package net;

import java.io.Serializable;

import engine.Commons;

public class InputMessage implements Serializable, Commons {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6616602629077668129L;
	public int flag;
	public int owner;
	public boolean value;
	
	public InputMessage(int fl, int ow, boolean val){
		flag = fl;
		owner = ow;
		value = val;
	}
}
