package net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import engine.Commons;

public class GameClient extends Thread implements Commons, Serializer{
	public final static int UDPPort = 1337;
	public final static int TCPPort = 7331;
	
	public static final int LOGIN = 0;
	public static final int DATA = 1;
	
	DatagramSocket UDPSocket;
	Socket TCPSocket;
	InetAddress IPAddress;
	
	public GameClient(){
		
		
		try {
			UDPSocket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		try {
			IPAddress = InetAddress.getByName("localhost");
			TCPSocket = new Socket(IPAddress,TCPPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		/*String sentence = "Bede gral w gre!!!";
		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
		sendData = sentence.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, UDPPort);
		
		try {
			UDPSocket.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		DatagramPacket receive = new DatagramPacket(receiveData, receiveData.length);
		
		try {
			UDPSocket.receive(receive);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(new String(receive.getData()));*/
	}
	
	public void sendPacket(Packet pack){
		byte[] packet = Serializer.serializeObject(pack);
		DatagramPacket sendPacket = new DatagramPacket(packet, packet.length, IPAddress, UDPPort);
		try {
			UDPSocket.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
