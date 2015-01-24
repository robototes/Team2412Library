package com.shsrobotics.library;

public class Wrapper<T> {
	public T value;
	public Wrapper(T v) {
		value = v;
	}
	
	public Wrapper() {
		value = null;
	}
}
