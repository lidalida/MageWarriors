package main;

import java.util.Random;

import javax.swing.JFrame;

import engine.Commons;
import engine.GameScene;
import net.GameClient;
import net.GameServer;
import view.StartView;

public class Main implements Commons{
	
	public static GameScene gameScene;
	public static GameServer gameServer;
	public static GameClient gameClient;
	public static StartView v;
	static Random rnd;
	public static JFrame frame;
	

	private static void createGUI(){
		frame = new JFrame("MageWarriors");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		v=new StartView();
		frame.getContentPane().add(v);
		frame.getContentPane().setPreferredSize(v.getSize());
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		v.requestFocus();
		
	}
	
	
	public static void main(String[] args){
			javax.swing.SwingUtilities.invokeLater(new Runnable(){
				public void run(){
					createGUI();					
				}
			});
			
		
				
	}
}
