package main;

import javax.swing.JFrame;

import view.SwingView;

public class Main {

	private static void createGUI(){
		JFrame frame = new JFrame("MageWarriors");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		SwingView v = new SwingView();
		frame.getContentPane().add(v);
		frame.getContentPane().setPreferredSize(v.getSize());
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
