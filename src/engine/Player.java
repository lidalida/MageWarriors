package engine;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Player {

	public static final int MOVE_DELTA = 5;

	private int x;
	private int y;
	private double dx;
	private double dy;
	private int mousex;
	private int mousey;
	private Image image;
	private Collider collider;
	private double rotation;

	
	public Player(){
		x=0;
		y=0;
		dx=0;
		dy=0;
		rotation=0;
		ImageIcon ii = new ImageIcon("src/res/player.png");
		image=ii.getImage();
		collider=new Collider(x,y,image.getWidth(null)/2);
		
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public double getRotation(){
		return rotation;
	}
	
	public Image getImage(){
		return image;
	}
	
	private Rectangle getBorders(){
		return new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
	}
	
	private Rectangle getWiderBorders(){
		return new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
	}
	
	private Rectangle getBordersAfterMove(){
		Double a=Math.ceil(x+dx);
		Double b=Math.ceil(y+dy);
		
		return new Rectangle(a.intValue(), b.intValue(), image.getWidth(null), image.getHeight(null));
	}
	
	
	public void move(){
		checkBorders();
		x+=dx;
		y+=dy;
		rotate();
		collider.update(x,y);
	}
	
	private void rotate(){
		double a=mousex-x;
		double b=y-mousey;
		if(b!=0)
			rotation=Math.atan2(a,b);
				
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
		if(e.getKeyCode() == KeyEvent.VK_W) {
			if(collider.checkMouseIn(mousex, mousey)){
				dx=0;
				dy=0;
			}
			else{
			dx=MOVE_DELTA*Math.sin(rotation);
			dy=-MOVE_DELTA*Math.cos(rotation);
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_S) {
			dx=-MOVE_DELTA*Math.sin(rotation);
			dy=MOVE_DELTA*Math.cos(rotation);
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
		if(e.getKeyCode() == KeyEvent.VK_W) {
			dx=0;
			dy=0;
		}
		if(e.getKeyCode() == KeyEvent.VK_S) {
			dx=0;
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
	
	public void mouseDragged(MouseEvent e){
		
	}
	
	public void mouseMoved(MouseEvent e){
		mousex=e.getX();
		mousey=e.getY();
			
		
	}

}
