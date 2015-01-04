/**
 * Console
 * 
 * @param N {NetworkTable} A network table
 * @param ID {String} An HTML ID for the console element.
 */
window.Console = (function() {
	var currentString = "";
	var lastString = "";
	var current;
	var EL;
	var promptString = "<b>CONDOR></b> ";
	var logging = false;
	var cleared = false;
	var X = {
		location: "Hlocation",
		port: "Hport",
		valueB: "HvalueB",
		valueN: "HvalueN",
		go: "Hgo",
		get: 1,
		set: 2
	};
	var N;
	var ID;
	
	var print = function(text, norep) {
		EL.innerHTML += norep ? text : text.replace(/ /g, "&nbsp;");
	};
	var println = function(str, norep) {
		str = str || "";
		print(str + "<br>", norep);
		EL.scrollByLines(1);
	};
	var error = function(str) {
		println("    ERROR: " + str);
	};
	var backspace = function() {
		currentString = currentString.slice(0, -1);
		current.innerHTML = currentString;
	};
	var up = function() {
		currentString = lastString;
		current.innerHTML = currentString;
	};
	
	var prompt = function() {
		if (logging) {
			current.remove();
			setTimeout(prompt, 500);
			return;
		};
		if (cleared) {
			cleared = false;
			return;
		};
		print(promptString);
		current = document.createElement("span");
		current.className = "console";
		EL.appendChild(current);
		EL.scrollByLines(1);
	};
	
	var submit = function(str) {
		var input = str.replace(/&nbsp;/g, " ").split(" ");
		var command = input[0].toUpperCase();
		var args = input.slice(1);
		switch (command) {
			case "CLEAR":
				clear();
				cleared = true;
				break;
			case "TIME":
				var d = new Date();
				var dSTR = (d.getHours()%12).toString() + ":" + ("00"+d.getMinutes()).slice(-2) + ":" + ("00"+d.getSeconds()).slice(-2) + " " + (d.getHours()>=12?"PM":"AM");
				println("  " + dSTR);
				break;
			case "HELP":
				if (!args[0]) {
					println("  Commands:");
					println("    TIME: prints local time.");
					println("    NETWORK: get and set NetworkTable values.");
					println("    HARDWARE: get and set hardware values on robot.");
					println("    LOG: log hardware values from robot.");
					println("    CLEAR: clear screen.");
					println("    HELP: get help on a command. Use as HELP [command name].");
				} else {
					switch (args[0].toUpperCase()) {
						case "NETWORK":
							println("  Format:");
							println("      NETWORK [GET or SET] [name] ([value])");
							println();
							println("    GET or SET: whether the values should be read or set.");
							println("    name: a valid string (no quotes or spaces) that represents the name of the network object to get or set.");
							println("    value: a valid boolean (TRUE or FALSE), string, or decimal number to set the value.");
							break;
						case "HARDWARE":
							println("  Format:");
							println("      HARDWARE [GET or SET] [location] [port] ([value])");
							println();
							println("    GET or SET: whether the values should be read or set.");
							println("    location: PWM, RELAY, DIGITAL, or ANALOG");
							println("    port: an integer representing the corresponding port to read.");
							println("    value: a valid boolean (TRUE or FALSE), string, or decimal number to set the value.");
							break;
						case "LOG":
							println("  Format:");
							println("      LOG [location] [port]");
							println();
							println("    location: PWM, RELAY, DIGITAL, or ANALOG");
							println("    port: an integer representing the corresponding port to read.");
							println();
							println("  Press 'q' to quit while logging.");
							break;
					}
				}
				break;
			case "NETWORK":
				var type = args[0].toUpperCase();
				var name = args[1];
				if (type === "GET") {
					var value = N.get(name);
					println("  " + name + " = " + value);
				} else if (type === "SET") {
					var value = args[2];
					if (value === "TRUE" || value === "FALSE") value = (value === "TRUE");
					if (parseFloat(value) == value) value = parseFloat(value);
					N.set(name, value);
					println("  SET " + name + " TO " + value.toString());
				} else {
					error("Expected GET or SET.");
				}
				break;
			case "HARDWARE":
				var type = args[0].toUpperCase();
				var location = args[1];
				var port = args[2];
					if (!"PWM RELAY DIGITAL ANALOG SOLENOID".contains(location)) {
						error("Location was not a valid type. Expected PWM, RELAY, DIGITAL, SOLENOID, or ANALOG.");
						return;
					}
					if (parseInt(port) != port) {
						error("Port number was not a valid integer.");
						return;
					}
				if (type === "GET") {
					hGET(location, port, function(d) {
						println("  " + location + " " + port + " = " + d);
					});
				} else if (type === "SET") {
					var value = args[3];
					hSET(location, port, parseFloat(value));
					println("  SET " + location + " " + port + " TO " + value + ".");
				} else {
					error("Expected GET or SET.");
				}
				break;
			case "LOG":
				var location = args[0];
				var port = args[1];
					if (!"PWM RELAY DIGITAL ANALOG SOLENOID".contains(location)) {
						error("Location was not a valid type. Expected PWM, RELAY, DIGITAL, SOLENOID, or ANALOG.");
						return;
					}
					if (parseInt(port) != port) {
						error("Port number was not a valid integer.");
						return;
					}
				println("LOGGING " + location + " " + port);
				logging = true;
				var int = setInterval(function() {
					if (!logging) {
						clearInterval(int);
						return;
					}
					hGET(location, port, println);
				}, 500);
				break;
			default:
				error("'" + command + "' was not recognized. Try 'help' for more information.")
		}
	};
	var hGET = function(location, port, callback) {
		var locID = ["PWM", "ANALOG", "RELAY", "DIGITAL", "SOLENOID"].indexOf(location);
		var continuous = (locID < 2);
		N.set(X.location, locID);
		N.set(X.port, parseInt(port));
		N.set(X.go, X.get);
		var oneTime = !logging;
		logging = true;
		setTimeout(function() {
			if (continuous) {
				callback(N.get(X.valueN));
			} else {
				var value = N.get(X.valueB) ? "TRUE" : "FALSE";
				callback(value);
			}
			if (oneTime) logging = false;
		}, 500);
	};
	var hSET = function(location, port, value) {
		var locID = ["PWM", "ANALOG", "RELAY", "DIGITAL", "SOLENOID"].indexOf(location);
		var continuous = (locID < 2);
		var key = continuous ? X.valueN : X.valueB;
		N.set(X.location, locID);
		N.set(X.port, parseInt(port));
		N.set(key, value);
		N.set(X.go, X.set);
	};
	
	
	var keypress = function(e) {
		var c = getChar(e.charCode);
		if (logging && c === "q") { logging = false; }
		if (c === "<ENTER>") {
			current.className = "";
			println();
			if (currentString.length > 0)
				submit(currentString);
			lastString = currentString;
			currentString = "";
			if (current) prompt();
		} else {
			currentString += c;
			current.innerHTML = currentString;
		}
	};
	var keydown = function(e) {
		if (e.keyCode === 8) backspace();
		if (e.keyCode === 38) { up(); e.preventDefault(); }
	};
	var getChar = function(code) {
		if (code === 13) return "<ENTER>";
		if (code === 32) return " ";
		return String.fromCharCode(code);
	};
	var clear = function() {
		EL = document.querySelector("div#" + ID);

		EL.innerHTML = null;

		println("<span style='color:red'>CONDOR Robot Human Textual Interface Console</span>", true);
		println("<span style='color:red'>FRC Team 2412.  2014.  [Version 1.0.0.0]</span>", true);
		println();
		prompt();

		document.onkeydown = keydown;
		document.onkeypress = keypress;
	};
	
	
	return {
		init: function(p_N, p_ID) { 
		    N = p_N; 
		    ID = p_ID;
		    document.head.appendChild(document.createElement("style"));
		    document.styleSheets[document.styleSheets.length-1].insertRule("span.console::after{ content:' |'; -webkit-animation: blinker 1s step-start infinite;", 0);
		    clear();
		}
	};
})();