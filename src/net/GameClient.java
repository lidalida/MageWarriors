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

public class GameClient extends Thread implements Commons, Serializer{
	public final static int UDPPort = 1337;
	public final static int TCPPort = 7331;
	
	DatagramSocket UDPSocket;
	DatagramSocket UDPSocket2;
	Socket TCPSocket;
	DataOutputStream outputStream;
	DataInputStream inputStream;
	InetAddress IPAddress;
	
	public GameClient(){
		
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
	
	public void run(){
		EventMsg msg = new EventMsg(0,LOGIN,UDPSocket.getLocalPort());
		sendViaTCP(msg);
		
		//InputMessage msg = new InputMessage(UDPSocket.getLocalPort(),2,true);
		//sendViaUDP(msg);
		InputMessage out = (InputMessage) receiveViaUDP();
		System.out.println("From Server: "+out.flag+" "+out.owner+" "+out.value);
		//while(true){
		/*sendViaUDP(msg);
		InputMessage out = (InputMessage) receiveViaUDP();
		System.out.println("From Server: "+out.flag+" "+out.owner+" "+out.value);//}*/
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
	
	public Serializable receiveViaTCP(){
		byte[] data = null;
		try {
			inputStream = new DataInputStream(TCPSocket.getInputStream());
			int len = inputStream.readInt();
			data = new byte[len];
			if(len > 0)
				inputStream.readFully(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Serializer.deserializeObject(data);
	}
}
