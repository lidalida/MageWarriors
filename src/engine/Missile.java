package engine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.beans.PropertyChangeSupport;

import javax.swing.ImageIcon;

public class Missile implements Drawable, Model, Commons {
	
	public PropertyChangeSupport propertyChangeSupport;
	
	private int x;
	private int y;
	private int id;
	private Image image;
	Collider collider;
	private double rotation;
	int lifetime;
	
	public void draw(Graphics g1){
		Graphics2D g = (Graphics2D) g1.create();
		int cx = image.getWidth(null) / 2;
        int cy = image.getHeight(null) / 2;
        g.rotate(rotation, cx+x, cy+y);
        g.drawImage(image, x, y, null);
	}
	
	public static int getXOnRadius(int rx, double rot, double rad){
		return (int) (rx+25 + rad*Math.sin(rot)-8);
	}
	
	public static int getYOnRadius(int ry, double rot, double rad){
		return (int) (ry+25-rad*Math.cos(rot)-8);
	}
	
	public Missile(Player player){
		x=(int) (player.getX()+player.getImage().getWidth(null)/2+(player.getImage().getWidth(null))/2*Math.sin(player.getRotation()));
		y=(int) (player.getY()+player.getImage().getHeight(null)/2-(player.getImage().getHeight(null))/2*Math.cos(player.getRotation()));
		rotation=player.getRotation();	
		ImageIcon ii = new ImageIcon("src/res/missile.png");
		image=ii.getImage();
		if(rotation>=(-Math.PI/2) && rotation<0)
		{
			x-=image.getWidth(null);
			y-=image.getHeight(null);
		}
		
		collider=new Collider(x,y,image.getWidth(null)/2);
		lifetime = 0;
	}
	
	public Missile(int spawn_x, int spawn_y, double spawn_rotation){
		x=spawn_x;
		y=spawn_y;
		rotation=spawn_rotation;	
		ImageIcon ii = new ImageIcon("src/res/missile.png");
		image=ii.getImage();		
		collider=new Collider(x,y,image.getWidth(null)/2);
		lifetime = 0;
	}
	
	public int getX(){
		return x;
	}
	
	public void setX(int x){
		this.x=x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setY(int y){
		this.y=y;
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
	
	public void setID(int i)
	{
		id=i;
	}
	
	public int getID()
	{
		return id;
	}
	
	public void move(){
		x+=MISSILE_MOVE_DELTA*Math.sin(rotation);
		y-=MISSILE_MOVE_DELTA*Math.cos(rotation);
		collider.update(x,y);
	}
	
}
