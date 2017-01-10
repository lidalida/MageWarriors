package net;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import engine.Commons;
import engine.Drawable;
import engine.GameScene;

public class GameServer extends Thread implements Commons, Serializer{
	public final static int UDPPort = 1337;
	public final static int TCPPort = 7331;
	
	DatagramSocket UDPSocket;
	ServerSocket TCPSocket;
	GameScene game;
	
	public List<InetAddress> playersIPs = new ArrayList<InetAddress>();
	
	public GameServer(GameScene g){
		game = g;
		
		try {
			UDPSocket = new DatagramSocket(UDPPort);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		try {
			TCPSocket = new ServerSocket(TCPPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void run(){
		
	}
	
	
	
}
