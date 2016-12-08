package engine;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.Timer;

public class Missile implements Model, ActionListener {
	
	public PropertyChangeSupport propertyChangeSupport;
	public static final int DELAY = 10;
	
	private int x;
	private int y;
	private double dx;
	private double dy;
	private Image image;
	private Collider collider;
	private double rotation;
	private int lifetime;
	private boolean toDelete;
	private Timer timer;
	
	
	public Missile(){
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
	}
	
}
