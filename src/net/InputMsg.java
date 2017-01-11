package net;

import java.io.Serializable;

public class InputMsg implements Serializable{

	private static final long serialVersionUID = -1086779311320330790L;
	int id; //playera
	int i; //rodzaj inputu
	boolean state; //pressed/released
	int x;
	int y; //do mouse position
	
	
}
