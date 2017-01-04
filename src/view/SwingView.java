package view;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import engine.Bar;
import engine.BarElement;
import engine.Commons;
import engine.Drawable;
import engine.GameScene;
import engine.Item;
import engine.Player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.Toolkit;
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
	public static final int WIDTH=800, HEIGHT=600;
	private Bar bar;
	private Item item;
    private TexturePaint paint, paint_scoreboard;
    private BufferedImage bi, bi_sb;
    private ImageIcon ii;
    private Image image_p1, image_p2, image_bar_border;
	

	public SwingView(){
        addKeyListener(new CustomKeyListener());
        addMouseMotionListener(new CustomMouseListener());
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setFocusable(true);
		try {
			bi = ImageIO.read(new File("src/res/texture.png"));
			bi_sb = ImageIO.read(new File("src/res/texture_scoreboard.png"));
		} catch (IOException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 this.paint = new TexturePaint(bi, new Rectangle(0, 0, bi.getWidth(), bi.getHeight()));
		 this.paint_scoreboard = new TexturePaint(bi_sb, new Rectangle(0, 0, bi_sb.getWidth(), bi_sb.getHeight()));
		 ii = new ImageIcon("src/res/player1_text.png");
		 image_p1=ii.getImage();
		 ii = new ImageIcon("src/res/player2_text.png");
		 image_p2=ii.getImage();
		 ii = new ImageIcon("src/res/bar_border.png");
		 image_bar_border=ii.getImage();

	}
	
	public void setGameScene(GameScene gs){
		gameScene=gs;
		player = new Player();
		enemy = new Player();
		enemy.setPosition(100, SCOREBOARD_HEIGHT+100);
		gameScene.addModel(player);
		gameScene.addModel(enemy);
		gameScene.makeBars();
	}
		
	public void modelPropertyChange(final PropertyChangeEvent evt){
		//repaint();
	}
	
	@Override
    public void paintComponent(Graphics g1) {
        //super.paintComponent(g1);
       
        Graphics2D g = (Graphics2D) g1;
        g.setPaint(paint);
        g.fillRect(0, SCOREBOARD_HEIGHT, WINDOW_WIDTH, ARENA_HEIGHT);
        g.setPaint(paint_scoreboard);
		g.fillRect(0, 0, WINDOW_WIDTH, SCOREBOARD_HEIGHT);
		g.drawImage(image_p1, 60, 20, null);
		g.drawImage(image_p2, 460, 20, null);
		g.drawImage(image_bar_border, 200-2, 20-2, null);
		g.drawImage(image_bar_border, 200-2, 60-2, null);
		g.drawImage(image_bar_border, WINDOW_WIDTH-PLAYER_HEALTH-100-2, 20-2, null);
		g.drawImage(image_bar_border, WINDOW_WIDTH-PLAYER_MANA-100-2, 60-2, null);

		synchronized(gameScene.models){
		for(Iterator<Drawable> it = gameScene.models.iterator(); it.hasNext();)
		{
			Drawable d = it.next();
			d.draw(g1);
		}
		}
		gameScene.getBar(1,0).draw(g1);
		gameScene.getBar(1,1).draw(g1);
		gameScene.getBar(2,0).draw(g1);
		gameScene.getBar(2,1).draw(g1);
		
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
