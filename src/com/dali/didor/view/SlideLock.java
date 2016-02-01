package com.dali.didor.view;

public class SlideLock {
	public static final int NONE = 0;
	public static final int MENU = 1;
	public static final int LIST = 2;
	
	private int current;
	
	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

}
