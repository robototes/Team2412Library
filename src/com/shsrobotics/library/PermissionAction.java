package com.shsrobotics.library;

import java.util.function.BooleanSupplier;


public class PermissionAction {
	public VoidFunction action;
	public java.util.function.BooleanSupplier permFunc;
	
	public PermissionAction(VoidFunction func, BooleanSupplier perm) {
		action = func;
		permFunc = perm;
	}
	
	public void perform() {
		if ( permFunc.getAsBoolean() ) action.f();
	}
	
}
