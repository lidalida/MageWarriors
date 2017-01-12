package net;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
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
	int[] port = new int[2];
	DataInputStream[] inputStream = new DataInputStream[2];
	DataOutputStream[] outputStream = new DataOutputStream[2];
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
			TCPWelcomeSocket = new ServerSocket(TCPPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void run(){
		TCPInit();
		EventMsg out = (EventMsg) receiveViaTCP(0);
		port[0] = out.value;
		System.out.println(port[0]);
		TCPInit();
		out = (EventMsg) receiveViaTCP(1);
		port[1] = out.value;
		System.out.println(port[1]);
		while(true){
			/*InputMessage in = (InputMessage) receiveViaUDP();
			System.out.println("From User: "+in.flag+" "+in.owner+" "+in.value);
			
			InputMessage msg = new InputMessage(10,24,false);
			InetAddress local = null;
			try {
				local = InetAddress.getByName("localhost");
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			sendViaUDP(msg,local);*/
			//System.out.println("From User: "+out.flag+" "+out.owner+" "+out.value);
			InputMessage msg = new InputMessage(10,24,false);
			try {
				sendViaUDP(msg,InetAddress.getByName("localhost"),port[0]);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*sendViaTCP(msg,0);
			if(connectedPlayersCount>1){
				sendViaTCP(msg,1);
			}*/
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
		byte[] data = new byte[1024];
		DatagramPacket packet = new DatagramPacket(data, data.length);
		try {
			UDPSocket.receive(packet);
			if(port[0]==0)
				port[0] = packet.getPort();
			else
				port[1] = packet.getPort();
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
			inputStream[connectedPlayersCount] = new DataInputStream(TCPSocket.getInputStream());
			outputStream[connectedPlayersCount] = new DataOutputStream(TCPSocket.getOutputStream());
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
	
	public Serializable receiveViaTCP(int playerID){
		byte[] data = null;
		try {
			int len = inputStream[playerID].readInt();
			data = new byte[len];
			if(len > 0)
				inputStream[playerID].readFully(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Serializer.deserializeObject(data);
	}
	
}
