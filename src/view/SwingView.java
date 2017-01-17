package view;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import engine.Commons;
import engine.Drawable;
import engine.GameScene;
import engine.LocalGameScene;
import engine.Player;
import main.Main;
import net.GameClient;
import net.InputMsg;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class SwingView extends JPanel implements Commons{

	private static final long serialVersionUID = 1L;
	GameScene gameScene;
	LocalGameScene localGameScene;
	
	private Player player1, player2;
	public static final int WIDTH=WINDOW_WIDTH, HEIGHT=ARENA_HEIGHT;
	private TexturePaint paint;
    private BufferedImage bi;
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

	}
	
	public void setGameScene(LocalGameScene lgs){
		localGameScene = lgs;
		player1 = new Player();
		player2 = new Player();
		
		player1.setID(1);
		player2.setID(2);
		player2.setImage(0);
		localGameScene.addModel(player1,1);		
		localGameScene.addModel(player2,2);
	}
	
	public void startGame()
	{		
		addKeyListener(new CustomKeyListener());
	    addMouseMotionListener(new CustomMouseListener());
		timer=new Timer(FRAMETIME, new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
				if(localGameScene.gameOver!=0)
					gameOver(localGameScene.gameOver);
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

	    Toolkit.getDefaultToolkit().sync();
    }

  
       
    private class CustomKeyListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
			
			if(e.getKeyCode() == KeyEvent.VK_W) {
				gameClient.sendViaUDP(new InputMsg(IS_MOVING,true,0,0));
			}
			if(e.getKeyCode() == KeyEvent.VK_S) {
				gameClient.sendViaUDP(new InputMsg(IS_MOVING_BACK,true,0,0));
			}
			if(e.getKeyCode() == KeyEvent.VK_A){
				gameClient.sendViaUDP(new InputMsg(IS_MOVING_LEFT,true,0,0));
			}
			if(e.getKeyCode() == KeyEvent.VK_D){
				gameClient.sendViaUDP(new InputMsg(IS_MOVING_RIGHT,true,0,0));
			}
			if(e.getKeyCode() == KeyEvent.VK_E){
				if(!isSpellCrafted)
					gameClient.sendViaUDP(new InputMsg(IS_CASTING_SPELL_1,true,0,0));
				isSpellCrafted = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_R){
				if(!isSpellCrafted)
					gameClient.sendViaUDP(new InputMsg(IS_CASTING_SPELL_2,true,0,0));
				isSpellCrafted = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_T){
				if(!isSpellCrafted)
					gameClient.sendViaUDP(new InputMsg(IS_CASTING_SPELL_3,true,0,0));
				isSpellCrafted = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_SPACE){
				gameClient.sendViaUDP(new InputMsg(IS_CASTING_SUPER_SPELL,true,0,0));
			}
            
		}

		@Override
		public void keyReleased(KeyEvent e) {
			
			if(e.getKeyCode() == KeyEvent.VK_W) {			
				gameClient.sendViaUDP(new InputMsg(IS_MOVING,false,0,0));
			}
			if(e.getKeyCode() == KeyEvent.VK_S) {
				gameClient.sendViaUDP(new InputMsg(IS_MOVING_BACK,false,0,0));
			}
			if(e.getKeyCode() == KeyEvent.VK_A){
				gameClient.sendViaUDP(new InputMsg(IS_MOVING_LEFT,false,0,0));
			}
			if(e.getKeyCode() == KeyEvent.VK_D){
				gameClient.sendViaUDP(new InputMsg(IS_MOVING_RIGHT,false,0,0));
			}
			if(e.getKeyCode() == KeyEvent.VK_E){
				gameClient.sendViaUDP(new InputMsg(IS_CASTING_SPELL_1,false,0,0));
				isSpellCrafted = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_R){
				gameClient.sendViaUDP(new InputMsg(IS_CASTING_SPELL_2,false,0,0));
	            isSpellCrafted = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_T){
				gameClient.sendViaUDP(new InputMsg(IS_CASTING_SPELL_3,false,0,0));
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

            gameClient.sendViaUDP(new InputMsg(IS_ROTATING,true,e.getX(),e.getY()));
		
		}
    	
    }
    
    private void gameOver(int id)
    {
    	timer.stop();
    	Main.frame.getContentPane().removeAll();
    	WinView v = new WinView(id);  
    	Main.frame.getContentPane().add(v);
    	v.requestFocus();
    	v.repaint();
    	v.revalidate();
    }

}
