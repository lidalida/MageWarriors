package main;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import engine.Commons;
import engine.GameScene;
import view.StartView;
import view.SwingView;

public class Main implements Commons{
	
	public static GameScene gameScene;
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
		gameScene=new GameScene();
			javax.swing.SwingUtilities.invokeLater(new Runnable(){
				public void run(){
					createGUI();					
				}
			});		
				
	}
}
