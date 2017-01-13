package view;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import engine.Bar;
import engine.BarElement;
import engine.Commons;
import engine.Drawable;
import engine.GameScene;
import engine.Item;
import engine.LocalGameScene;
import engine.Player;
import main.Main;
import net.Flag;
import net.GameClient;
import net.GameServer;
import net.InputMsg;
import net.PositionMsg;

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
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SwingView extends JPanel implements Commons{

	private static final long serialVersionUID = 1L;
	public GameScene gameScene;
	public LocalGameScene localGameScene;
	
	private Player player1, player2;
	public static final int WIDTH=WINDOW_WIDTH, HEIGHT=ARENA_HEIGHT;
	private TexturePaint paint;
    private BufferedImage bi;
    long t, t_start;
    private Timer timer;
    private boolean isSpellCrafted=false;
    
    GameClient gameClient;
	

	public SwingView(){
       
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
	
	public void setGameScene(LocalGameScene lgs){
		localGameScene = lgs;
		player1 = new Player();
		player2 = new Player();
		
		player1.setID(1);
		player2.setID(2);
		//gameScene.addModel(player1);
		localGameScene.addModel(player1,1);		
		//gameScene.addModel(player2);
		localGameScene.addModel(player2,2);
		//gameScene.makeBars();
	}
	
	public void startGame()
	{		
		addKeyListener(new CustomKeyListener());
	    addMouseMotionListener(new CustomMouseListener());
		timer=new Timer(FRAMETIME, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				t=System.currentTimeMillis();				
				repaint();
				//gameScene.gameUpdate();
				if(gameScene!=null)
				if(gameScene.getGameOver()!=0)
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
        
        synchronized(localGameScene.models){
		for(Iterator<Drawable> it = localGameScene.models.iterator(); it.hasNext();)
		{
			Drawable d = it.next();
			d.draw(g1);
		}
		}
		if(gameScene!=null)
			gameScene.setPainted(true);
	    Toolkit.getDefaultToolkit().sync();
    }

  
       
    private class CustomKeyListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
			//if(gameScene==null)
				//return;
			
			if(e.getKeyCode() == KeyEvent.VK_W) {
	            gameClient.sendViaTCP(new InputMsg(IS_MOVING,true,0,0));
			}
			if(e.getKeyCode() == KeyEvent.VK_S) {
	            gameClient.sendViaTCP(new InputMsg(IS_MOVING_BACK,true,0,0));
			}
			if(e.getKeyCode() == KeyEvent.VK_A){
				gameClient.sendViaTCP(new InputMsg(IS_MOVING_LEFT,true,0,0));
			}
			if(e.getKeyCode() == KeyEvent.VK_D){
				gameClient.sendViaTCP(new InputMsg(IS_MOVING_RIGHT,true,0,0));
			}
			if(e.getKeyCode() == KeyEvent.VK_E){
				if(!isSpellCrafted)
					gameClient.sendViaTCP(new InputMsg(IS_CASTING_SPELL_1,true,0,0));
				isSpellCrafted = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_R){
				if(!isSpellCrafted)
					gameClient.sendViaTCP(new InputMsg(IS_CASTING_SPELL_2,true,0,0));
				isSpellCrafted = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_T){
				if(!isSpellCrafted)
					gameClient.sendViaTCP(new InputMsg(IS_CASTING_SPELL_3,true,0,0));
				isSpellCrafted = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_SPACE){
				gameClient.sendViaTCP(new InputMsg(IS_CASTING_SUPER_SPELL,true,0,0));
			}
            
		}

		@Override
		public void keyReleased(KeyEvent e) {
			//if(gameScene==null)
				//return;
			
			if(e.getKeyCode() == KeyEvent.VK_W) {			
				gameClient.sendViaTCP(new InputMsg(IS_MOVING,false,0,0));
			}
			if(e.getKeyCode() == KeyEvent.VK_S) {
	            gameClient.sendViaTCP(new InputMsg(IS_MOVING_BACK,false,0,0));
			}
			if(e.getKeyCode() == KeyEvent.VK_A){
				gameClient.sendViaTCP(new InputMsg(IS_MOVING_LEFT,false,0,0));
			}
			if(e.getKeyCode() == KeyEvent.VK_D){
				gameClient.sendViaTCP(new InputMsg(IS_MOVING_RIGHT,false,0,0));
			}
			if(e.getKeyCode() == KeyEvent.VK_E){
				gameClient.sendViaTCP(new InputMsg(IS_CASTING_SPELL_1,false,0,0));
				isSpellCrafted = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_R){
				gameClient.sendViaTCP(new InputMsg(IS_CASTING_SPELL_2,false,0,0));
	            isSpellCrafted = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_T){
				gameClient.sendViaTCP(new InputMsg(IS_CASTING_SPELL_3,false,0,0));
	            isSpellCrafted = false;
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
			//if(gameScene==null)
				//return;
            gameClient.sendViaTCP(new InputMsg(IS_ROTATING,true,e.getX(),e.getY()));
		
		}
    	
    }
    
    private void gameOver()
    {
    	timer.stop();
    	Main.frame.getContentPane().removeAll();
    	WinView v = new WinView(gameScene.getGameOver());    	
    	Main.frame.getContentPane().add(v);
    	v.requestFocus();
    	v.repaint();
    	v.revalidate();
    }

}
