package engine;

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

import engine.Player;

public class SwingView extends JPanel implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int HEIGHT = 600;
	public static final int WIDTH = 800;
    private final int DELAY = 10;

	
	private Player player;
	private Timer timer;

	public SwingView(){
        addKeyListener(new CustomKeyListener());
        addMouseMotionListener(new CustomMouseListener());
		setSize(WIDTH, HEIGHT);
		setFocusable(true);
		player = new Player();
		timer = new Timer(DELAY, this);
        timer.start();     
	}
	
	@Override
    public void paintComponent(Graphics g1) {
        super.paintComponent(g1);

        Graphics2D g = (Graphics2D) g1;
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		
		int cx = player.getImage().getWidth(null) / 2;
        int cy = player.getImage().getHeight(null) / 2;
        //AffineTransform oldAT = g.getTransform();
        g.rotate(player.getRotation(), cx+player.getX(), cy+player.getY());
        g.drawImage(player.getImage(), player.getX(), player.getY(), null);
        //g.setTransform(oldAT);
        
	    Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        player.move();
        repaint();  
    }	
    
       
    private class CustomKeyListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
            player.keyPressed(e);
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
    	
    }
    
    private class CustomMouseListener implements MouseMotionListener{

		@Override
		public void mouseDragged(MouseEvent e) {
			player.mouseDragged(e);
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			player.mouseMoved(e);
			
		}
    	
    }

}
