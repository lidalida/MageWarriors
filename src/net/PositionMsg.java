package net;

import java.io.Serializable;

public class PositionMsg implements Serializable{

	private static final long serialVersionUID = 7380373230942879527L;
	int id;
	int x;
	int y;
	double rot;
	
	public PositionMsg(int id, int x, int y, double r)
	{
		this.id=id;
		this.x=x;
		this.y=y;
		this.rot=r;
		
	}

}
