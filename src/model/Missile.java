package model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Missile implements Drawable, Model {
	
	public PropertyChangeSupport propertyChangeSupport;
	public static final int DELAY = 10;
	public static final int MOVE_DELTA = 10;
	
	private int x;
	private int y;
	private double dx;
	private double dy;
	private Image image;
	public Collider collider;
	private double rotation;
	int lifetime;
	
	public void draw(Graphics g1){
		Graphics2D g = (Graphics2D) g1.create();
		int cx = image.getWidth(null) / 2;
        int cy = image.getHeight(null) / 2;
        g.rotate(rotation, cx+x, cy+y);
        g.drawImage(image, x, y, null);
	}
	
	public Missile(Player player){
		x=(int) (player.getX()+player.getImage().getWidth(null)/2+(player.getImage().getWidth(null)+15)/2*Math.sin(player.getRotation()))-5;
		y=(int) (player.getY()+player.getImage().getHeight(null)/2-(player.getImage().getHeight(null)+15)/2*Math.cos(player.getRotation()))-5;
		dx=0;
		dy=0;
		rotation=player.getRotation();		
		ImageIcon ii = new ImageIcon("src/res/missile.png");
		image=ii.getImage();
		collider=new Collider(x,y,image.getWidth(null)/2);
		lifetime = 0;
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
	
	
	public void move(){
		x+=MOVE_DELTA*Math.sin(rotation);
		y-=MOVE_DELTA*Math.cos(rotation);
		collider.update(x,y);
	}
	
}
