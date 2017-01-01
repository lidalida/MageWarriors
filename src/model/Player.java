package model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.Timer;

import view.SwingView;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Player implements Drawable, Model, ActionListener{

	public PropertyChangeSupport propertyChangeSupport;
	public static final int MOVE_DELTA = 5;
	public static final int DELAY = 10;

	private int x;
	private int y;
	private double dx;
	private double dy;
	private int mousex;
	private int mousey;
	private Image image;
	public Collider collider;
	private double rotation;
	private int is_moving; //0-nie, 1-do przodu, 2-do tylu
	private int is_rotating; 
	private Timer timer;
	private double forbidden_rotation=1000;
	
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
		timer=new Timer(DELAY, this);
		timer.start();
	}
	
	public void draw(Graphics g1){
		Graphics2D g = (Graphics2D) g1.create();
		int cx = image.getWidth(null) / 2;
        int cy = image.getHeight(null) / 2;
        g.rotate(rotation, cx+x, cy+y);
        g.drawImage(image, x, y, null);
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
	
	public void setRotation(double rot){
		rotation = rot;
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
		int old_mx=mousex;
		mousex=x;
		firePropertyChange("mousex", old_mx, mousex);
		


	}
	
	public void setMouseY(int y)
	{
		int old_my=mousey;
		mousey=y;
		firePropertyChange("mousey", old_my, mousey);


	}
	
	public void setPosition(int xx, int yy)
	{
		x=xx;
		y=yy;
		collider.update(x, y);
	}
	
	public void setDx(double x)
	{
		dx=x;
	}
	
	public void setDy(double y)
	{
		dy=y;
	}
	
	public void setIsMoving(int i)
	{
		is_moving=i;
	}
	public void setIsRotating(int i)
	{
		is_rotating=i;
	}
	
	public void setForbiddenRotation(double r)
	{
		forbidden_rotation=r;
	}
	
	public void move(){
		if(checkRotation())
		{
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
			collider.update(x,y);
			firePropertyChange("x", x-dx, x);
			firePropertyChange("y", y-dy, y);
		}
	}
	
	public void moveBack()
	{
		dx=-MOVE_DELTA*Math.sin(rotation);
		dy=MOVE_DELTA*Math.cos(rotation);
		
		checkBorders();
		x+=dx;
		y+=dy;
		collider.update(x,y);
		firePropertyChange("x", x-dx, x);
		firePropertyChange("y", y-dy, y);
	}
	
	public void rotate(){
		double old_rotation=rotation;
		double a=mousex-x-image.getWidth(null)/2;
		double b=y+image.getWidth(null)/2-mousey;
		if(b!=0)
			rotation=Math.atan2(a,b);
		firePropertyChange("rotation", old_rotation, rotation);
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
	
	public boolean checkRotation()
	{
		if(forbidden_rotation!=1000)
		{
			forbidden_rotation=Math.toDegrees(forbidden_rotation);
			if(forbidden_rotation<0)
				forbidden_rotation=360+forbidden_rotation;
		
			double r=(forbidden_rotation+90);
			double l=(forbidden_rotation-90);
			if(r<0)
				r+=360;
			if(l<0)
				l+=360;
			r=r%360;
			l=l%360;
			double rot=Math.toDegrees(rotation);
			if(rot<0)
				rot+=360;

			if(forbidden_rotation>270 || forbidden_rotation<90)
			{
				if(rot>l || rot<r)
					return false;			
			}
			else
			{
				if(rot>l && rot<r)
					return false;
			}
			return true;
		}
		return true;
		
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

	@Override
	public void actionPerformed(ActionEvent e) {
	/*	if(is_moving==1)
		{
			move();
		}
		else if(is_moving==2)
		{
			moveBack();
		}
		
		if(is_rotating==1)
		{
			double old_rotation=rotation;
			rotate();
			is_rotating=0;
			firePropertyChange("rotation", old_rotation, rotation);
		}*/
	}

}
