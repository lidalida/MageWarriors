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
		System.out.println(rotation);
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
		System.out.println(rotation);
		ImageIcon ii = new ImageIcon("src/res/missile.png");
		image=ii.getImage();
		/*if(rotation>=(-Math.PI/2) && rotation<0)
		{
			x-=image.getWidth(null);
			y-=image.getHeight(null);
		}
		else if(rotation>=(-Math.PI) && rotation<(-Math.PI/2))
		{
			x-=image.getWidth(null);
		}
		else if(rotation>=0 && rotation<=Math.PI/2)
		{
			y-=image.getWidth(null);
		}*/
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
		x+=MISSILE_MOVE_DELTA*Math.sin(rotation);
		y-=MISSILE_MOVE_DELTA*Math.cos(rotation);
		collider.update(x,y);
	}
	
}
