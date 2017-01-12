package net;

import java.io.Serializable;

public class InputMsg implements Serializable{

	private static final long serialVersionUID = -1086779311320330790L;
	int flag;
	boolean state;
	int x;
	int y; //for mouse position
	
	public InputMsg(int i, boolean s, int x, int y)
	{
		flag=i;
		state=s;
		this.x=x;
		this.y=y;
	}
}
