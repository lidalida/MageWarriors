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

import engine.Commons;
import engine.GameScene;

public class GameServer extends Thread implements Commons{
	public final static int UDPPort = 1337;
	public final static int TCPPort = 7331;
	
	DatagramSocket UDPSocket;
	ServerSocket TCPSocket;
	GameScene game;
	
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
		while(true){
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			
			try {
				UDPSocket.receive(packet);
				System.out.println("RECEIVED");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			Packet received = (Packet)DeserializePacket(packet.getData());
			System.out.println("DataType: "+received.data_type);
			Flag flag = (Flag)DeserializePacket(received.data);
			System.out.println("Flag id: "+flag.id+"  Value: "+flag.value);
			game.setFlag(flag.id, flag.value);
            /*String sentence = new String( packet.getData());
            System.out.println("RECEIVED: " + sentence);
            InetAddress IPAddress = packet.getAddress();
            int port = packet.getPort();
            data = "You're logged in".getBytes();
            DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, port);
            
            try {
				UDPSocket.send(sendPacket);
			} catch (IOException e) {
				e.printStackTrace();
			}*/
            
		}
	}
	
	public Serializable DeserializePacket(byte[] array){
		ByteArrayInputStream bis = new ByteArrayInputStream(array);
		ObjectInput in = null;
		try {
			in = new ObjectInputStream(bis);
			Serializable packet = (Serializable) in.readObject();
			return packet;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
