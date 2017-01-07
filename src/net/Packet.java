package net;

import java.io.Serializable;

import engine.Commons;

public class Packet implements Serializable, Commons {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6616602629077668129L;
	public int packet_type;
	public int data_type;
	public int owner;
	public byte[] data;
	
	public Packet(int p, int d, int o, byte[] array){
		packet_type = p;
		data_type = d;
		owner = o;
		data = array;
	}
}
