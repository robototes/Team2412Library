package com.shsrobotics.library;

import java.util.function.Predicate;


public class PermissionAction {
	public VoidFunction action;
	public Predicate<Object[]> permFunc;
	
	public PermissionAction(VoidFunction func, Predicate<Object[]> perm) {
		action = func;
		permFunc = perm;
	}
	
	public void perform(Object...args) {
		try {
			if ( permFunc.test(args) ) action.f(args);
		}
		catch ( ClassCastException cce ) {
			System.err.println("Check parameter types more correctly!");
			cce.printStackTrace();
		}
	}
	
}
