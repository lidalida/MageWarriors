package net;

import java.io.Serializable;

public class EventMsg implements Serializable{

	private static final long serialVersionUID = 7769300176771980978L;
	int id;
	int name;
	int value;
	
	public EventMsg(int i, int a, int v)
	{
		id=i;
		name=a;
		value=v;
	}
	

}
