package engine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Item implements Drawable, Model, Commons{
	
	public int x, y;
	public int id;
	public int type;
	private double rotation;
	public double rnd;
	private Image image;
	public Collider collider;
	
	public Item(int item_x, int item_y)
	{
		x=item_x;
		y=item_y;
		rnd=Math.random();
		if(rnd<SPAWN_1)
		{
			type=HP;
			ImageIcon ii = new ImageIcon("src/res/hp.png");
			image=ii.getImage();
		}
		else if(rnd>=SPAWN_1 && rnd<SPAWN_2)
		{
			type=MANA;
			ImageIcon ii = new ImageIcon("src/res/mana.png");
			image=ii.getImage();
		}
		else if(rnd>=SPAWN_2 && rnd<SPAWN_3)
		{
			type=FREEZE;
			ImageIcon ii = new ImageIcon("src/res/freeze.png");
			image=ii.getImage();
		}
		else if(rnd>=SPAWN_3 && rnd<SPAWN_4)
		{
			type=SPEEDUP;
			ImageIcon ii = new ImageIcon("src/res/speedup.png");
			image=ii.getImage();
		}
		else if(rnd>=SPAWN_4)
		{
			type=TELEPORT;
			ImageIcon ii = new ImageIcon("src/res/teleport.png");
			image=ii.getImage();
		}
		
		collider=new Collider(x,y,image.getWidth(null)/2);
		
	}
	
	public Item(int item_x, int item_y, int t)
	{
		x=item_x;
		y=item_y;
		type=t;
		if(type==SPEEDUP)
		{
			ImageIcon ii = new ImageIcon("src/res/speedup.png");
			image=ii.getImage();
		}
		else if (type==TELEPORT)
		{
			ImageIcon ii = new ImageIcon("src/res/teleport.png");
			image=ii.getImage();
		}
		else if (type==FREEZE)
		{
			ImageIcon ii = new ImageIcon("src/res/freeze.png");
			image=ii.getImage();
		}
		else if (type==MANA)
		{
			ImageIcon ii = new ImageIcon("src/res/mana.png");
			image=ii.getImage();
		}
		else if (type==HP)
		{
			ImageIcon ii = new ImageIcon("src/res/hp.png");
			image=ii.getImage();
		}
	}

	@Override
	public Image getImage() {
		return image;
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
	
	public int getType()
	{
		return type;
	}
	
	public double getRotation(){
		return rotation;
	}
	
	public void setRotation(double rot){
		rotation = rot;
	}
	
	public void setID(int i)
	{
		id=i;
	}
	
	public int getID()
	{
		return id;
	}

	@Override
	public void draw(Graphics g1) {
		Graphics2D g = (Graphics2D) g1.create();
		int cx = image.getWidth(null) / 2;
        int cy = image.getHeight(null) / 2;
        g.rotate(rotation, cx+x, cy+y);
        g.drawImage(image, x, y, null);		
	}

}
