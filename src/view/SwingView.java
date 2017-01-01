package view;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.Player;
import model.Drawable;
import model.GameScene;
import model.Missile;

public class SwingView extends JPanel implements ActionListener, View{
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	public static final int HEIGHT = 600;
	public static final int WIDTH = 800;
    private final int DELAY = 1000/60;
	enum Flags_names{IS_MOVING, IS_MOVING_BACK, IS_SHOOTING, IS_ROTATING};



	public GameScene gameScene;
	
	private Player player, enemy;
	private Timer timer;
	

	public SwingView(){
        addKeyListener(new CustomKeyListener());
        addMouseMotionListener(new CustomMouseListener());
		setSize(WIDTH, HEIGHT);
		setFocusable(true);
		timer = new Timer(DELAY, this);
        timer.start();
	}
	
	public void setGameScene(GameScene gs){
		gameScene=gs;
		player = new Player();
		enemy = new Player();
		enemy.setPosition(100, 100);
		gameScene.addModel(player);
		gameScene.addModel(enemy);

	}
		
	public void modelPropertyChange(final PropertyChangeEvent evt){
		//repaint();
	}
	
	@Override
    public void paintComponent(Graphics g1) {
        super.paintComponent(g1);
        
       
        Graphics2D g = (Graphics2D) g1;
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		synchronized(gameScene.models){
		for(Iterator<Drawable> it = gameScene.models.iterator(); it.hasNext();)
		{
			Drawable d = it.next();
			d.draw(g1);
		}
		}
		
		gameScene.setPainted(true);
	    Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	repaint();
    }	
    
       
    private class CustomKeyListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_W) {			
	            gameScene.setFlag(Flags_names.IS_MOVING.ordinal(), true);
			}
			if(e.getKeyCode() == KeyEvent.VK_S) {
	            gameScene.setFlag(Flags_names.IS_MOVING_BACK.ordinal(), true);
			}
			if(e.getKeyCode() == KeyEvent.VK_SPACE){
	            gameScene.setFlag(Flags_names.IS_SHOOTING.ordinal(), true);

			}
            
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_W) {			
	            gameScene.setFlag(Flags_names.IS_MOVING.ordinal(), false);
			}
			if(e.getKeyCode() == KeyEvent.VK_S) {
	            gameScene.setFlag(Flags_names.IS_MOVING_BACK.ordinal(), false);
			}
			if(e.getKeyCode() == KeyEvent.VK_SPACE){
	            gameScene.setFlag(Flags_names.IS_SHOOTING.ordinal(), false);
			}
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
    	
    }
    
    private class CustomMouseListener implements MouseMotionListener{

		@Override
		public void mouseDragged(MouseEvent e) {
			//gameController.mouseDragged(e);
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
            gameScene.setFlag(Flags_names.IS_ROTATING.ordinal(), true);
            gameScene.setMousePos(e.getX(), e.getY());
		
		}
    	
    }

}
