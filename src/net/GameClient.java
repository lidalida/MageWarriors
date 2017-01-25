package net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

import engine.Commons;
import engine.Item;
import engine.LocalGameScene;
import engine.Missile;
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
	public LocalGameScene game;
	public boolean play_again;
	public boolean gameOver;

	
	public GameClient(LocalGameScene lgs, String host){
		game = lgs;
		play_again=false;
		gameOver=false;
		
		try {
			UDPSocket = new DatagramSocket();
			System.out.println(UDPSocket.getLocalPort());
		} catch (SocketException e) {
			e.printStackTrace();
		}
		try {
			IPAddress = InetAddress.getByName(host);
			TCPSocket = new Socket(host,TCPPort);
			TCPSocket.setTcpNoDelay(true);
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
		byte[] data = new byte[76];
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
		
		//sendViaUDP("wut");
		
		while(true){
			EventMsg ev = (EventMsg) receiveViaTCP(false);
			if(ev!=null)
				resolveMessage(ev);
			
			
			if(!gameOver)
			{
				PositionMsg in = (PositionMsg) receiveViaUDP();
				Model tmp = (Model) game.findModelByID(in.id);
				
				if(tmp==null){
					continue;
				}
				
				tmp.setX(in.x);
				tmp.setY(in.y);
				tmp.setRotation(in.rot);

			}
			else
			{
				EventMsg evm = (EventMsg) receiveViaTCP(true);
				if(evm!=null)
					resolveMessage(evm);
				gameOver=false;
			}
			
		}
	}
	
	public void resolveMessage(Serializable msg){
		synchronized(game.models){
			
			if(msg.getClass()==EventMsg.class){
				EventMsg tmp = (EventMsg) msg;
				if(tmp.name==ADD_ITEM){
					game.addModel(new Item(-100,-100,tmp.value),tmp.id);
				} else if(tmp.name==ADD_MISSILE){
					game.addModel(new Missile(-100,-100,0),tmp.id);
				} else if(tmp.name==DELETE_OBJECT){
					game.removeModel(tmp.id);
				} else if(tmp.name==CHANGE_HP){
					((Player)game.findModelByID(tmp.id)).setHP(tmp.value);
				} else if(tmp.name==CHANGE_MP){
					((Player)game.findModelByID(tmp.id)).setMP(tmp.value);
				} else if(tmp.name==CHANGE_IMG){
					((Player)game.findModelByID(tmp.id)).setImage(tmp.value);
				} else if(tmp.name==GAME_OVER){
					game.gameOver = tmp.value;
					gameOver=true;
				} else if(tmp.name==PLAY_AGAIN){
					play_again=true;
				}
			}
			
		}
	}
	
}
