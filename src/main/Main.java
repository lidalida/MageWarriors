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
	static StartView v;
	static Random rnd;
	public static JFrame frame;
	

	private static void createGUI(){
		frame = new JFrame("MageWarriors");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Container contentPane = frame.getContentPane();
		//BoxLayout l = new BoxLayout(contentPane, BoxLayout.PAGE_AXIS);
		//frame.setLayout(l);
		
		v=new StartView();
		frame.getContentPane().add(v);
		frame.getContentPane().setPreferredSize(v.getSize());
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		v.requestFocus();
		
	}
	
	public static void gameLoop()
	{
		long t, t_start=System.currentTimeMillis();

		while(true)
		{	
			t=System.currentTimeMillis();
			if(t-t_start>1000)
			{
				gameScene.generateItems();				
				t_start=t;
			}
			v.repaint();
			gameScene.gameUpdate();
			try {
				Thread.sleep(FRAMETIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//v.repaint();
		}
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
