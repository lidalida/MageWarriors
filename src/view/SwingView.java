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

public class SwingView extends JPanel implements View, Commons{

	private static final long serialVersionUID = 1L;
	public GameScene gameScene;
	
	private Player player, enemy;
	public static final int WIDTH=WINDOW_WIDTH, HEIGHT=ARENA_HEIGHT;
	private Bar bar;
	private Item item;
    private TexturePaint paint, paint_scoreboard;
    private BufferedImage bi, bi_sb;
    private ImageIcon ii;
    private Image image_p1, image_p2, image_bar_border;
	long t, t_start;
	

	

	public SwingView(){
        addKeyListener(new CustomKeyListener());
        addMouseMotionListener(new CustomMouseListener());
		setSize(WINDOW_WIDTH, ARENA_HEIGHT);
		this.setPreferredSize(new Dimension(WINDOW_WIDTH, ARENA_HEIGHT));
		//this.setMaximumSize(new Dimension(WINDOW_WIDTH, ARENA_HEIGHT));

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
		Timer timer=new Timer(FRAMETIME, new ActionListener(){

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
			}
			
		});
		timer.start();
	}
		
	public void modelPropertyChange(final PropertyChangeEvent evt){
		//repaint();
	}
	
	@Override
    public void paintComponent(Graphics g1) {
        //super.paintComponent(g1);
       
        Graphics2D g = (Graphics2D) g1;
        g.setPaint(paint);
        g.fillRect(0, 0, WINDOW_WIDTH, ARENA_HEIGHT);
       /* g.setPaint(paint_scoreboard);
		g.fillRect(0, 0, WINDOW_WIDTH, SCOREBOARD_HEIGHT);
		g.drawImage(image_p1, 60, 20, null);
		g.drawImage(image_p2, 460, 20, null);
		g.drawImage(image_bar_border, 200-2, 20-2, null);
		g.drawImage(image_bar_border, 200-2, 60-2, null);
		g.drawImage(image_bar_border, WINDOW_WIDTH-PLAYER_HEALTH-100-2, 20-2, null);
		g.drawImage(image_bar_border, WINDOW_WIDTH-PLAYER_MANA-100-2, 60-2, null);*/

		synchronized(gameScene.models){
		for(Iterator<Drawable> it = gameScene.models.iterator(); it.hasNext();)
		{
			Drawable d = it.next();
			d.draw(g1);
		}
		}
		/*gameScene.getBar(1,0).draw(g1);
		gameScene.getBar(1,1).draw(g1);
		gameScene.getBar(2,0).draw(g1);
		gameScene.getBar(2,1).draw(g1);*/
		
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
			if(e.getKeyCode() == KeyEvent.VK_M){
				gameScene.setFlag(TMP_MANA_CHARGER, true);
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
			if(e.getKeyCode() == KeyEvent.VK_M){
				gameScene.setFlag(TMP_MANA_CHARGER, false);
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
