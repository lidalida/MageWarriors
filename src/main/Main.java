package main;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;

import engine.Commons;
import engine.GameScene;
import view.SwingView;

public class Main implements Commons{
	
	static GameScene gameScene;
	static SwingView v;

	private static void createGUI(){
		JFrame frame = new JFrame("MageWarriors");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		v = new SwingView();
		frame.getContentPane().add(v);
		frame.getContentPane().setPreferredSize(v.getSize());
		frame.pack();
		frame.setVisible(true);
		v.requestFocus();
		v.setGameScene(gameScene);
		
		
	}
	public static void main(String[] args){
		gameScene=new GameScene();
		long t, t_start=System.currentTimeMillis();
		try {
			javax.swing.SwingUtilities.invokeAndWait(new Runnable(){
				public void run(){
					createGUI();
					
				}
			});
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		while(true)
		{	
			t=System.currentTimeMillis();
			if(t-t_start>10000)
			{
				//v.repaint();
				//t_start=t;
				//System.gc();
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
		//gameScene.gameLoop();		
	}
}
