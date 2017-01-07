package net;

import java.io.Serializable;

public class Flag implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7770085020742859278L;
	public int id;
	public boolean value;
	
	public Flag(int i, boolean val){
		id = i;
		value = val;
	}
}
