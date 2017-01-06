package view;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import engine.Bar;
import engine.BarElement;
import engine.Commons;
import engine.Drawable;
import engine.GameScene;
import engine.Item;
import engine.Player;
import main.Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SwingView extends JPanel implements Commons{

	private static final long serialVersionUID = 1L;
	public GameScene gameScene;
	
	private Player player, enemy;
	public static final int WIDTH=WINDOW_WIDTH, HEIGHT=ARENA_HEIGHT;
	private TexturePaint paint;
    private BufferedImage bi;
    long t, t_start;
    private Timer timer;
	

	

	public SwingView(){
        addKeyListener(new CustomKeyListener());
        addMouseMotionListener(new CustomMouseListener());
		setSize(WINDOW_WIDTH, ARENA_HEIGHT);
		this.setPreferredSize(new Dimension(WINDOW_WIDTH, ARENA_HEIGHT));

		setFocusable(true);
		try {
			bi = ImageIO.read(new File("src/res/texture.png"));
		} catch (IOException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 this.paint = new TexturePaint(bi, new Rectangle(0, 0, bi.getWidth(), bi.getHeight()));
				 
		 t_start=System.currentTimeMillis();		 

	}
	
	public void setGameScene(GameScene gs){
		gameScene=gs;
		player = new Player();
		enemy = new Player();
		enemy.setPosition(100, 110);
		gameScene.addModel(player);
		gameScene.addModel(enemy);
		gameScene.makeBars();
	}
	
	public void startGame()
	{
		timer=new Timer(FRAMETIME, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				t=System.currentTimeMillis();
				if(t-t_start>1000)
				{
					gameScene.generateItems();	
					t_start=t;
				}
				repaint();
				gameScene.gameUpdate();
				if(gameScene.game_over!=0)
					gameOver();
			}
			
		});
		timer.start();
	}
	
	@Override
    public void paintComponent(Graphics g1) {
        super.paintComponent(g1);
       
        Graphics2D g = (Graphics2D) g1;
        g.setPaint(paint);
        g.fillRect(0, 0, WINDOW_WIDTH, ARENA_HEIGHT);       

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
			if(e.getKeyCode() == KeyEvent.VK_A){
				gameScene.setFlag(IS_MOVING_LEFT, true);
			}
			if(e.getKeyCode() == KeyEvent.VK_D){
				gameScene.setFlag(IS_MOVING_RIGHT, true);
			}
			if(e.getKeyCode() == KeyEvent.VK_E){
				if(!gameScene.getFlag(IS_SPELL_CRAFTED))
					gameScene.setFlag(IS_CASTING_SPELL_1, true);
			}
			if(e.getKeyCode() == KeyEvent.VK_R){
				if(!gameScene.getFlag(IS_SPELL_CRAFTED))
					gameScene.setFlag(IS_CASTING_SPELL_2, true);
			}
			if(e.getKeyCode() == KeyEvent.VK_T){
				if(!gameScene.getFlag(IS_SPELL_CRAFTED))
					gameScene.setFlag(IS_CASTING_SPELL_3, true);
			}
			if(e.getKeyCode() == KeyEvent.VK_SPACE){
				gameScene.setFlag(IS_CASTING_SUPER_SPELL, true);
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
			if(e.getKeyCode() == KeyEvent.VK_A){
				gameScene.setFlag(IS_MOVING_LEFT, false);
			}
			if(e.getKeyCode() == KeyEvent.VK_D){
				gameScene.setFlag(IS_MOVING_RIGHT, false);
			}
			if(e.getKeyCode() == KeyEvent.VK_E){
	            gameScene.setFlag(IS_CASTING_SPELL_1, false);
	            gameScene.setFlag(IS_SPELL_CRAFTED, false);
			}
			if(e.getKeyCode() == KeyEvent.VK_R){
	            gameScene.setFlag(IS_CASTING_SPELL_2, false);
	            gameScene.setFlag(IS_SPELL_CRAFTED, false);
			}
			if(e.getKeyCode() == KeyEvent.VK_T){
	            gameScene.setFlag(IS_CASTING_SPELL_3, false);
	            gameScene.setFlag(IS_SPELL_CRAFTED, false);
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
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
            gameScene.setFlag(IS_ROTATING, true);
            gameScene.setMousePos(e.getX(), e.getY());
		
		}
    	
    }
    
    private void gameOver()
    {
    	timer.stop();
    	Main.frame.getContentPane().removeAll();
    	WinView v = new WinView(gameScene.game_over);    	
    	Main.frame.getContentPane().add(v);
    	v.requestFocus();
    	v.repaint();
    	v.revalidate();
    }

}
