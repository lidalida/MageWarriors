package net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import engine.Commons;
import engine.GameScene;


public class GameServer extends Thread implements Commons, Serializer{
	public final static int UDPPort = 1337;
	public final static int TCPPort = 7331;
	
	DatagramSocket UDPSocket;
	ServerSocket TCPWelcomeSocket;
	Socket TCPSocket;
	int connectedPlayersCount = 0;
	InetAddress[] IPs = new InetAddress[2];
	public int[] port = new int[2];
	DataInputStream[] inputStream = new DataInputStream[2];
	DataOutputStream[] outputStream = new DataOutputStream[2];
	public GameScene game;
	int lastPlayer;
	
	public List<InetAddress> playersIPs = new ArrayList<InetAddress>();
	
	public GameServer(GameScene g){
		game = g;
		
		try {
			UDPSocket = new DatagramSocket(null);
			UDPSocket.setReuseAddress(true);
			UDPSocket.bind(new InetSocketAddress("localhost",UDPPort));
			IPs[0] = InetAddress.getByName("localhost");
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		try {
			TCPWelcomeSocket = new ServerSocket(TCPPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void dispose()
	{
		try {
			
			TCPSocket.close();
			TCPWelcomeSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void sendViaUDP(Serializable msg, InetAddress ip,int p){
		byte[] data = Serializer.serializeObject(msg);
		DatagramPacket packet = new DatagramPacket(data,data.length,ip,p);
		try {
			UDPSocket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Serializable receiveViaUDP(){
		byte[] data = new byte[128];
		DatagramPacket packet = new DatagramPacket(data, data.length);
		try {
			UDPSocket.receive(packet);
			if(port[0]==0)
				port[0] = packet.getPort();
			else if(port[1]==0)
				port[1] = packet.getPort();
			else if(port[0]==packet.getPort())
				lastPlayer = 1;
			else
				lastPlayer = 2;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Serializer.deserializeObject(packet.getData());
	}
	
	public void TCPInit(){
		if(connectedPlayersCount>2)
			return;
		
		try {
			TCPSocket= TCPWelcomeSocket.accept();
			((Socket)TCPSocket).setTcpNoDelay(true);
			inputStream[connectedPlayersCount] = new DataInputStream(TCPSocket.getInputStream());
			outputStream[connectedPlayersCount] = new DataOutputStream(TCPSocket.getOutputStream());
			if(connectedPlayersCount==1)
				IPs[1] = TCPSocket.getInetAddress();
		} catch (IOException e) {
			e.printStackTrace();
		}
		connectedPlayersCount++;
	}
	
	public void sendViaTCP(Serializable msg, int playerID){
		try {
			byte[] data = Serializer.serializeObject(msg);
			outputStream[playerID].writeInt(data.length);
			if(data.length>0)
				outputStream[playerID].write(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Serializable receiveViaTCP(int playerID, boolean blockingMode){
		byte[] data = null;
		try {
			if(!blockingMode)
				if(inputStream[playerID].available()==0)
					return null;
			int len = inputStream[playerID].readInt();
			data = new byte[len];
			if(len > 0)
				inputStream[playerID].readFully(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Serializer.deserializeObject(data);
	}
	
	public void sendPositionMsg(int id, int x, int y, double rot){
		sendViaUDP(new PositionMsg(id,x,y,rot),IPs[0],port[0]);
		sendViaUDP(new PositionMsg(id,x,y,rot),IPs[1],port[1]);

	}
	
	public void sendEventMsg(int id, int name, int value){
		sendViaTCP(new EventMsg(id,name,value),0);
		sendViaTCP(new EventMsg(id,name,value),1);
	}
	
	public void run(){
		Serializable in;
		
		TCPInit();
		EventMsg out = (EventMsg) receiveViaTCP(0,true);
		port[0] = out.value;
		
		TCPInit();
		out = (EventMsg) receiveViaTCP(1,true);
		port[1] = out.value;
		
		game.startGame();
		
		while(true){
			in = receiveViaUDP();
			if(in!=null)
				resolveMessage(in, lastPlayer);
		}
		
	}
	
	public void resolveMessage(Serializable msg, int owner){
		if(msg.getClass()==InputMsg.class){
			InputMsg tmp = (InputMsg) msg;
			if(tmp.flag==IS_MOVING){
				game.setFlag(IS_MOVING,tmp.state,owner);
			} else if(tmp.flag==IS_MOVING_BACK){
				game.setFlag(IS_MOVING_BACK,tmp.state,owner);
			} else if(tmp.flag==IS_MOVING_LEFT){
				game.setFlag(IS_MOVING_LEFT,tmp.state,owner);
			} else if(tmp.flag==IS_MOVING_RIGHT){
				game.setFlag(IS_MOVING_RIGHT,tmp.state,owner);
			} else if(tmp.flag==IS_MOVING_RIGHT){
				game.setFlag(IS_MOVING_RIGHT,tmp.state,owner);
			} else if(tmp.flag==IS_CASTING_SPELL_1){
				game.setFlag(IS_CASTING_SPELL_1,tmp.state,owner);
			} else if(tmp.flag==IS_CASTING_SPELL_2){
				game.setFlag(IS_CASTING_SPELL_2,tmp.state,owner);
			} else if(tmp.flag==IS_CASTING_SPELL_3){
				game.setFlag(IS_CASTING_SPELL_3,tmp.state,owner);
			} else if(tmp.flag==IS_CASTING_SUPER_SPELL){
				game.setFlag(IS_CASTING_SUPER_SPELL,tmp.state,owner);
			} else if(tmp.flag==IS_ROTATING){
				game.setFlag(IS_ROTATING,tmp.state,owner);
				game.setMousePos(tmp.x, tmp.y, owner);
			} else if(tmp.flag==PLAY_AGAIN){
				sendEventMsg(0, PLAY_AGAIN, 0);
			}
		}
	}
	
}
