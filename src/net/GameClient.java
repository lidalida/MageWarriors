package net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import engine.Commons;
import engine.Item;
import engine.LocalGameScene;
import engine.Model;
import engine.Player;

public class GameClient extends Thread implements Commons, Serializer{
	public final static int UDPPort = 1337;
	public final static int TCPPort = 7331;
	
	DatagramSocket UDPSocket;
	DatagramSocket UDPSocket2;
	Socket TCPSocket;
	DataOutputStream outputStream;
	DataInputStream inputStream;
	InetAddress IPAddress;
	LocalGameScene game;
	
	public GameClient(LocalGameScene lgs){
		game = lgs;
		
		try {
			UDPSocket = new DatagramSocket();
			System.out.println(UDPSocket.getLocalPort());
		} catch (SocketException e) {
			e.printStackTrace();
		}
		try {
			IPAddress = InetAddress.getByName("localhost");
			TCPSocket = new Socket("localhost",TCPPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendViaUDP(Serializable msg){
		byte[] data = Serializer.serializeObject(msg);
		DatagramPacket packetacket = new DatagramPacket(data, data.length, IPAddress, UDPPort);
		try {
			UDPSocket.send(packetacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Serializable receiveViaUDP(){
		byte[] data = new byte[1024];
		DatagramPacket packet = new DatagramPacket(data, data.length);
		try {
			UDPSocket.receive(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Serializer.deserializeObject(packet.getData());
	}
	
	public void sendViaTCP(Serializable msg){
		try {
			outputStream = new DataOutputStream(TCPSocket.getOutputStream());
			byte[] data = Serializer.serializeObject(msg);
			outputStream.writeInt(data.length);
			if(data.length>0)
				outputStream.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Serializable receiveViaTCP(boolean blockingMode){
		byte[] data = null;
		try {
			inputStream = new DataInputStream(TCPSocket.getInputStream());
			if(!blockingMode)
				if(inputStream.available()==0)
					return null;
			int len = inputStream.readInt();
			data = new byte[len];
			if(len > 0)
				inputStream.readFully(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Serializer.deserializeObject(data);
	}
	
	public void run(){
		EventMsg msg = new EventMsg(0,LOGIN,UDPSocket.getLocalPort());
		sendViaTCP(msg);
		
		while(true){
			EventMsg ev = (EventMsg) receiveViaTCP(false);
			if(ev!=null)
				resolveMessage(ev);
			PositionMsg in = (PositionMsg) receiveViaUDP();
			Model tmp = (Model) game.findModelByID(in.id);
			
			if(tmp==null)
				continue;
			//System.out.println("13");
			
			tmp.setX(in.x);
			tmp.setY(in.y);
			tmp.setRotation(in.rot);

		}
	}
	
	public void resolveMessage(Serializable msg){
		//System.out.println(msg.getClass());
		synchronized(game.models){
		if(msg.getClass()==EventMsg.class){
			EventMsg tmp = (EventMsg) msg;
			if(tmp.name==ADD_ITEM)
				game.addModel(new Item(0,0),tmp.id);
				
		}
	}
	}
	
}
