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
import java.beans.PropertyChangeEvent;

import java.util.ArrayList;
import java.util.List;

import engine.Player;

public class SwingView extends JPanel implements ActionListener, View{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int HEIGHT = 600;
	public static final int WIDTH = 800;
    private final int DELAY = 10;

	private GameController gameController;
	List<Model> models = new ArrayList<Model>();
	
	private Player player;
	private Timer timer;

	public SwingView(){
        addKeyListener(new CustomKeyListener());
        addMouseMotionListener(new CustomMouseListener());
		setSize(WIDTH, HEIGHT);
		setFocusable(true);
		setController(new GameController());
		player = new Player();
		gameController.addModel(player);
		gameController.addView(this);
		timer = new Timer(DELAY, this);
        timer.start();     
	}
	
	public void paintView(){
		repaint();
	}
	
	public void setController(Controller controller){
		gameController = (GameController)controller;
	}
	
	public void modelPropertyChange(final PropertyChangeEvent evt){
		System.out.println("yup");
		repaint();
	}
	
	public void addModel(Model model){
		models.add(model);
	}
	
	@Override
    public void paintComponent(Graphics g1) {
        super.paintComponent(g1);

        Graphics2D g = (Graphics2D) g1;
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		/*for (Model it: models){
			int cx = player.getImage().getWidth(null) / 2;
	        int cy = player.getImage().getHeight(null) / 2;
	        g.rotate(player.getRotation(), cx+player.getX(), cy+player.getY());
	        g.drawImage(player.getImage(), player.getX(), player.getY(), null);
		}*/
		
		int cx = player.getImage().getWidth(null) / 2;
        int cy = player.getImage().getHeight(null) / 2;
        g.rotate(player.getRotation(), cx+player.getX(), cy+player.getY());
        g.drawImage(player.getImage(), player.getX(), player.getY(), null);
        
	    Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        //player.move();
        repaint();  
    }	
    
       
    private class CustomKeyListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
            gameController.keyPressed(e);
            
		}

		@Override
		public void keyReleased(KeyEvent e) {
			//gameController.keyReleased(e);
			
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
			gameController.mouseMoved(e);
			
		}
    	
    }

}
