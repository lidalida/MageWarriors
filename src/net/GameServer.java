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

public class GameServer extends Thread implements Commons, Serializator{
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
		while(true){
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			
			try {
				UDPSocket.receive(packet);
				System.out.println("RECEIVED");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			Packet received = (Packet)Serializator.deserializeObject(packet.getData());
			System.out.println("DataType: "+received.data_type);
			Flag flag = (Flag)Serializator.deserializeObject(received.data);
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
	
	/*private void resolvePacket(Packet packet){
		if(packet.packet_type==LOGIN){
			if(playersIPs.size()<=MAX_PLAYERS)
				playersIPs.add()
		}
	}*/
	
	
}
