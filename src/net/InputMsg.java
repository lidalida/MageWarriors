package net;

import java.io.Serializable;

public class InputMsg implements Serializable{

	private static final long serialVersionUID = -1086779311320330790L;
	int id; //playera
	int i; //rodzaj inputu
	boolean state; //pressed/released
	int x;
	int y; //do mouse position
	
	public InputMsg(int id, int i, boolean s, int x, int y)
	{
		this.id=id;
		this.i=i;
		this.state=s;
		this.x=x;
		this.y=y;
	}
}
