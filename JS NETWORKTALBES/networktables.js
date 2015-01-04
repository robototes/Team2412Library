/*
 * TEAM 2412
 * JAVASCRIPT NETWORK TABLE IMPLEMENTATION
 * ALL RIGHTS RESERVED
 * 
 * OCTOBER 2014
 */

/**
 * Create or connect to a Network Table.
 * @param {String} name
 * @param {String} IP
 * @param {Object} altLibrary an alternate library to use for network communication.  
 * 
 * Alternate Library detail:
 * create(IP, PORT) creates socket
 * send(BUFFER) sends buffer
 * onReceive(CALLBACK) sets callback for receiving messages.  Must pass callback function with data parameter containing data buffer.
 * isConnected() returns true if connection established
 */
function NetworkTable(name, IP, altLibrary) {
    var NAME = name + "/";
    var table = {};
    var IP = IP;
    var PORT = 1735;
    var NULLF = function() { };
    var callback = NULLF;
    var connected = false;

    var chromeLibrary = (function() {
	var lib = chrome.sockets.tcp;
	var sID;
	return {
	    create: function(IP, PORT) {
		lib.create({}, function(info) {
		    sID = info.socketId;
		    lib.connect(sID, IP, PORT, NULLF);
		});
	    },
	    send: function(BUFFER) {
		try {
		    lib.send(sID, BUFFER, NULLF);
		} catch(e) { }
	    },
	    onReceive: function(CALLBACK) {
		lib.onReceive.addListener(CALLBACK);
	    },
	    isConnected: function() {
		return !!sID;
	    }
	};
    })();

    var comm = altLibrary || chromeLibrary;

    comm.create(IP, PORT);

    var Entry = function(name, value, type, sequence) {
	this.name = name;
	this.value = value;
	this.type = type;
	this.sequence = sequence || 0;
    };

    var createValue = function(name, value) {
	checkConnection();
	var data = {
	    length: 3,
	    packets: [
		{type: "b", data: 16} // type of request (entry assignment)
	    ]
	};

	var l = name.length;
	data.length += l + 1; // l+1 b/c name + 1 slash
	data.packets.push({type: "i16", data: l + 1}); // name string length
	data.packets.push({type: "b", data: 47}); // slash

	// entry name
	var a = toCharCodes(name);
	for (var i = 0; i < l; i++) {
	    data.packets.push({type: "b", data: a[i]});
	}

	// entry data type 
	switch (typeof value) {
	    case "boolean":
		data.packets.push({type: "b", data: 0});
		break;
	    case "number":
		data.packets.push({type: "b", data: 1});
		break;
	    case "string":
		data.packets.push({type: "b", data: 2});
		break;
	}

	data.length += 5;
	// entry ID
	data.packets.push({type: "i16", data: 65535});
	// entry sequence number
	data.packets.push({type: "i16", data: 0});

	switch (typeof value) {
	    case "boolean":
		data.packets.push({type: "b", data: value ? 1 : 0});
		data.length += 1;
		break;
	    case "number":
		data.packets.push({type: "d", data: value});
		data.length += 8;
		break;
	    case "string":
		var l = value.length;
		data.length += l; 
		data.packets.push({type: "i16", data: l}); // name string length
		var a = toCharCodes(value);
		for (var i = 0; i < l; i++) {
		    data.packets.push({type: "b", data: a[i]});
		    data.length += 1;
		}
		break;
	}

	send(data);
    };    
    
    var updateValue = function(name, value) {
	checkConnection();
	var id = getEntryID(name);

	var data = {
	    length: 1,
	    packets: [
		{type: "b", data: 17} // update request
	    ]
	};

	data.packets.push({type: "i16", data: id}); // entry ID
	data.packets.push({type: "i16", data: ++table[id].sequence}); // entry sequence ID
	data.length += 4;

	switch (typeof value) {
	    case "boolean":
		data.packets.push({type: "b", data: value ? 1 : 0});
		data.length += 1;
		break;
	    case "number":
		data.packets.push({type: "d", data: value});
		data.length += 8;
		break;
	    case "string":
		var l = value.length;
		data.length += l; 
		data.packets.push({type: "i16", data: l}); // name string length
		var a = toCharCodes(value);
		for (var i = 0; i < l; i++) {
		    data.packets.push({type: "b", data: a[i]});
		    data.length += 1;
		}
		break;
	}

	send(data);
    };

    var send = function(data) {
	var b = prepareBuffer(data);
	comm.send(b);
    };

    var connect = function() {
	checkConnection();
	var data = {
	    length: 3,
	    packets: [
		{type: "b", data: 1}, // hello
		{type: "i16", data: 512} // protocol revision
	    ]
	};
	send(data);
    };

    var receive = function(info) {
	connected = true;
	var last = 0;
	var d = info.data;
	do { // for each packet received
	    var d = d.slice(last);
	    var ar = new Uint8Array(d);
	    switch (ar[0]) {
		case 3: // SERVER HELLO
		    connected = true;
		    return;
		    break;
		case 16: // REGISTER NEW
		    var labelData = decodeUTF8(d, 4);
		    var name = labelData.string;
		    var end = labelData.end;
		    var dataStart = end + 5;
		    var type = ar[end];
		    var buffer = convertData(convertData(d.slice(end + 1, end + 5), 0, 2), 2, 2);
		    var chars = new Uint16Array(buffer);
		    var id = chars[0];
		    var sequence = chars[1];
		    var value = decodeData(d, dataStart, type);
		    // don't record if data for another table
		    if (name.indexOf(NAME) === 0) {
			table[id] = new Entry(name, value.value, type, sequence);
		    }
		    last = value.end;
		    break;
		case 17: // UPDATE
		    var buffer = convertData(convertData(d.slice(1, 5), 0, 2), 2, 2);
		    var chars = new Uint16Array(buffer);
		    var id = chars[0];
		    var dataStart = 5;
		    // don't record if data for another table or doesn't exist at all
		    if (table[id]) {
			var value = decodeData(d, dataStart, table[id].type);
			table[id].value = value.value;
			table[id].sequence = chars[1];
		    }
		    last = value.end;
		    break;
	    }
	} while (ar[last] !== undefined);
	callback();
    };

    // convert from string
    var decodeUTF8 = function(buffer, start) {
	var ar = new Uint8Array(buffer);
	var l = ar[start - 2] - 1;
	var end = start + l; // ar[start - 1] is length
	return {
	    string: fromCharCodes(new Uint8Array(buffer.slice(start, end))),
	    length: l,
	    end: end
	};
    };

    // decodes buffer data to types (boolean, double, string, etc)
    var decodeData = function(buffer, start, type) {
	var ar = new Uint8Array(buffer);
	var end;
	var value;
	switch (type) {
	    case 0: // boolean
		value = (ar[start] === 1);
		end = start + 1;
		break;
	    case 1: // double
		buffer = convertData(buffer, start, 8);
		value = new Float64Array(buffer.slice(start, start + 8))[0];
		end = start + 8;
		break;
	    case 2: // string
		var str = decodeUTF8(buffer, start);
		value = str.string;
		end = str.end;
		break;
	}
	return {
	    value: value,
	    end: end
	};
    };

    // makes buffer from packet structure
    var prepareBuffer = function(data) {
	var l = data.length;
	var b = new ArrayBuffer(l);
	var v = new DataView(b);
	var pos = 0;
	for (var i in data.packets) {
	    var d = data.packets[i];
	    switch (d.type) {
		case "b":
		    v.setInt8(pos, d.data);
		    pos += 1;
		    break;
		case "i16":
		    v.setInt16(pos, d.data);
		    pos += 2;
		    break;
		case "i32":
		    v.setInt32(pos, d.data);
		    pos += 4;
		    break;
		case "d":
		    v.setFloat64(pos, d.data);
		    pos += 8;
		    break;
	    }
	}
	return b;
    };

    // reverses data for proper sending
    var convertData = function(buffer, start, byteLength) {
	var a = new Array(byteLength);
	var v = new Uint8Array(buffer);
	for (var i = 0; i < byteLength; i++) {
	    a[byteLength - 1 - i] = v[i + start];
	}
	v.set(a, start);
	return v.buffer;
    };

    // string conversion method
    var toCharCodes = function(string) {
	var a = [];
	for (var i = 0; i < string.length; i++) {
	    a.push(string.charCodeAt(i));
	}
	return a;
    };
    var fromCharCodes = function(array) {
	var s = "";
	for (var i = 0; i < array.length; i++) {
	    s += String.fromCharCode(array[i]);
	}
	return s;
    };

    // check connection status
    var checkConnection = function() {
	if (!comm.isConnected()) {
	    console.log("Not connected.");
	}
    };

    // get Entry ID from name of object
    var getEntryID = function(name) {
	for (var id in table) {
	    if (table[id].name === name) {
		return id;
	    }
	}
	return -1;
    };

    //  receiving listener
    comm.onReceive(function(a) {
	try {
	    receive(a);
	} catch (e) {
	    console.log(e);
	}
    });

    return {
	/**
	 * Get Network Table value
	 * @param {String} name
	 * @param {type} defaultValue optional default value to avoid errors
	 * @returns {NetworkTable.Entry.value} value stored in table
	 */
	get: function(name, defaultValue) {
	    var id = getEntryID(NAME + name);
	    if (id >= 0) {
		return table[id].value;
	    } else {
		return defaultValue;
	    }
	},
	/**
	 * Set Network Table value
	 * @param {type} name
	 * @param {type} value the value
	 */
	set: function(name, value) {
	    if (getEntryID(NAME + name) >= 0) { // already exists
		updateValue(NAME + name, value);
	    } else {
		createValue(NAME + name, value);
	    }
	},
	/**
	 * Set a function to call when new data arrives.
	 * @param {type} f the callback
	 */
	setCallback: function(f) {
	    callback = f;
	},
	/**
	 * Connect to robot.
	 */
	connect: connect,
	/**
	 * Determine connection status.
	 * @returns {boolean} true if connected
	 */
	isConnected: function() {
	    return connected;
	},
	/**
	 * Get the datatype and table ID of a given object.
	 * @param {string} name the object name
	 * @returns {object} {type: string, id: number}
	 */
	getTypeAndID: function(name) {
	    var id = getEntryID(NAME + name);
	    return {
		type: table[id].type,
		id: id
	    };
	}
    };
}