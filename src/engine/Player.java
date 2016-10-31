package engine;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import java.awt.event.KeyEvent;

public class Player {

	public static final int MOVE_DELTA = 5;

	private int x;
	private int y;
	private int dx;
	private int dy;
	private Image image;

	
	public Player(){
		x=0;
		y=0;
		dx=0;
		dy=0;
		ImageIcon ii = new ImageIcon("src/res/player.png");
		image=ii.getImage();
		
	}
	
	public int getX(){
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public Image getImage(){
		return image;
	}
	
	private Rectangle getBordersAfterMove(){
		return new Rectangle(x+dx, y+dy, image.getWidth(null), image.getHeight(null));
	}
	
	
	public void move(){
		checkBorders();
		x+=dx;
		y+=dy;
	}
	
	public void keyPressed(KeyEvent e){
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {					
		    dx=-MOVE_DELTA;   
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			dx=MOVE_DELTA;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
		    dy=-MOVE_DELTA;    
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
		    dy=MOVE_DELTA;    
		}
		
	}
	
	public void keyReleased(KeyEvent e){
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {					
		    dx=0;   
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			dx=0;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
		    dy=0;    
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
		    dy=0;    
		}		
	}
	
	private void checkBorders(){
		
		Rectangle player = getBordersAfterMove();
		Rectangle gamescene = new Rectangle(0, 0, SwingView.WIDTH, SwingView.HEIGHT);
		

		if(!gamescene.contains(player))
		{
			dx=0;
			dy=0;
		}
		
	}
	

}
