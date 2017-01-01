package view;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;

import java.util.Iterator;

import model.Player;
import model.Commons;
import model.Drawable;
import model.GameScene;

public class SwingView extends JPanel implements View, Commons{

	private static final long serialVersionUID = 1L;
	public GameScene gameScene;
	
	private Player player, enemy;
	public static final int WIDTH=800, HEIGHT=600;
	

	public SwingView(){
        addKeyListener(new CustomKeyListener());
        addMouseMotionListener(new CustomMouseListener());
		setSize(WIDTH, HEIGHT);
		setFocusable(true);

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

  
       
    private class CustomKeyListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_W) {			
	            gameScene.setFlag(IS_MOVING, true);
			}
			if(e.getKeyCode() == KeyEvent.VK_S) {
	            gameScene.setFlag(IS_MOVING_BACK, true);
			}
			if(e.getKeyCode() == KeyEvent.VK_SPACE){
	            gameScene.setFlag(IS_SHOOTING, true);

			}
            
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_W) {			
	            gameScene.setFlag(IS_MOVING, false);
			}
			if(e.getKeyCode() == KeyEvent.VK_S) {
	            gameScene.setFlag(IS_MOVING_BACK, false);
			}
			if(e.getKeyCode() == KeyEvent.VK_SPACE){
	            gameScene.setFlag(IS_SHOOTING, false);
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
            gameScene.setFlag(IS_ROTATING, true);
            gameScene.setMousePos(e.getX(), e.getY());
		
		}
    	
    }

}
