package engine;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Missile implements Model, ActionListener {
	
	public PropertyChangeSupport propertyChangeSupport;
	public static final int DELAY = 10;
	
	private int x;
	private int y;
	private double dx;
	private double dy;
	private Image image;
	public Collider collider;
	private double rotation;
	private int lifetime;
	private boolean toDelete;
	private Timer timer;
	
	
	public Missile(Player player){
		x=(int) (player.getX()+player.getImage().getWidth(null)/2+player.getImage().getWidth(null)/2*Math.sin(player.getRotation()));
		y=(int) (player.getY()+player.getImage().getHeight(null)/2-player.getImage().getHeight(null)/2*Math.cos(player.getRotation()));
		dx=0;
		dy=0;
		rotation=player.getRotation();
		ImageIcon ii = new ImageIcon("src/res/missile.png");
		image=ii.getImage();
		collider=new Collider(x,y,image.getWidth(null)/2);
		propertyChangeSupport = new PropertyChangeSupport(this);
		timer=new Timer(DELAY, this);
		timer.start();
		lifetime = 0;
		toDelete = false;
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
	
	public boolean IsToDelete(){
		return toDelete;
	}
	
	
	public void move(){
		x+=10*Math.sin(rotation);
		y-=10*Math.cos(rotation);
		collider.update(x,y);
		firePropertyChange("x", x-dx, x);
		firePropertyChange("y", y-dy, y);
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
	
	
	public void actionPerformed(ActionEvent e) {
		lifetime++;
		if(lifetime>=100)
			toDelete = true;
		move();
	}
	
}
