package engine;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Player implements Model{

	public PropertyChangeSupport propertyChangeSupport;
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
		propertyChangeSupport = new PropertyChangeSupport(this);
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
	
	public Rectangle getBorders(){
		return new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
	}
	
	public Rectangle getWiderBorders(){
		return new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
	}
	
	public Rectangle getBordersAfterMove(){
		Double a=Math.ceil(x+dx);
		Double b=Math.ceil(y+dy);
		
		return new Rectangle(a.intValue(), b.intValue(), image.getWidth(null), image.getHeight(null));
	}
	
		
	public void setMouseX(int x)
	{
		double old_rotation=rotation;
		int old_mx=mousex;
		mousex=x;
		rotate();
		firePropertyChange("mousex", old_mx, mousex);
		firePropertyChange("rotation", old_rotation, rotation);


	}
	
	public void setMouseY(int y)
	{
		double old_rotation=rotation;
		int old_my=mousey;
		mousey=y;
		rotate();
		firePropertyChange("mousey", old_my, mousey);
		firePropertyChange("rotation", old_rotation, rotation);


	}
	
	public void setDx(double x)
	{
		dx=x;
	}
	
	public void setDy(double y)
	{
		dy=y;
	}
	
	public void move(){
		System.out.println("move");
		if(collider.containsPoint(mousex, mousey)){
			dx=0;
			dy=0;
		}
		else{
		dx=MOVE_DELTA*Math.sin(rotation);
		dy=-MOVE_DELTA*Math.cos(rotation);
		}
		
		checkBorders();
		x+=dx;
		y+=dy;
		//rotate();
		collider.update(x,y);
		System.out.println("move10");
		firePropertyChange("x", x-dx, x);
		firePropertyChange("y", y-dy, y);
	}
	
	public void moveBack()
	{
		dx=-MOVE_DELTA*Math.sin(rotation);
		dy=MOVE_DELTA*Math.cos(rotation);
		
		checkBorders();
		x+=dx;
		y+=dy;
		//rotate();
		collider.update(x,y);
		firePropertyChange("x", x-dx, x);
		firePropertyChange("y", y-dy, y);
	}
	
	public void rotate(){
		double old_rotation=rotation;
		double a=mousex-x;
		double b=y-mousey;
		if(b!=0)
			rotation=Math.atan2(a,b);
		System.out.println("rotate");
		firePropertyChange("rotation", old_rotation, rotation);
		System.out.println("rotate");
	}
	

	public void checkBorders(){
		
		Rectangle player = getBordersAfterMove();
		Rectangle gamescene = new Rectangle(0, 0, SwingView.WIDTH, SwingView.HEIGHT);
		

		if(!gamescene.contains(player))
		{
			dx=0;
			dy=0;
		}		
	}	
	
	@Override
	public void addPropertyChangeListener(PropertyChangeListener pcl)
	{
		propertyChangeSupport.addPropertyChangeListener(pcl);
	}

	@Override
	public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}

}
