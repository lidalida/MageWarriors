package engine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import view.SwingView;

import java.beans.PropertyChangeSupport;

public class Player implements Drawable, Model, Commons {

	public PropertyChangeSupport propertyChangeSupport;

	private int x;
	private int y;
	private double dx;
	private double dy;
	private int mousex;
	private int mousey;
	private Image image;
	public Collider collider;
	private double rotation, tmp_rotation;
	private double forbidden_rotation=1000;
	private int super_spell;
	
	private int hp;
	private int mp;
	
	public Player(){
		x=0;
		y=SCOREBOARD_HEIGHT;
		dx=0;
		dy=0;
		rotation=0;
		tmp_rotation=0;
		super_spell=0;
		hp=PLAYER_HEALTH;
		mp=PLAYER_MANA;
		ImageIcon ii = new ImageIcon("src/res/player.png");
		image=ii.getImage();
		collider=new Collider(x,y,image.getWidth(null)/2);
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
		mousex=x;
	}
	
	public void setMouseY(int y)
	{
		mousey=y;
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
	
	public void setForbiddenRotation(double r)
	{
		forbidden_rotation=r;
	}
	
	public void setSuperSpell(int spell)
	{
		super_spell=spell;
	}
	public int getSuperSpell()
	{
		return super_spell;
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
		}
	}
	
	
	public void moveBack()
	{
		if(checkRotation())
		{
			dx=-MOVE_DELTA*Math.sin(rotation);
			dy=MOVE_DELTA*Math.cos(rotation);
			
			checkBorders();
			x+=dx;
			y+=dy;
			collider.update(x,y);
		}
	}
	
	public void moveLeft()
	{
		if(checkRotation())
		{
			tmp_rotation=Math.toDegrees(rotation)-90;
			if(tmp_rotation<0)
				tmp_rotation+=360;
			dx=-MOVE_DELTA*Math.sin(Math.toRadians(tmp_rotation));
			dy=MOVE_DELTA*Math.cos(Math.toRadians(tmp_rotation));
			checkBorders();
			x+=dx;
			y+=dy;
			collider.update(x,y);
			rotate();
		}
	}
	
	public void moveRight()
	{
		if(checkRotation())
		{
			tmp_rotation=Math.toDegrees(rotation)+90;
			if(tmp_rotation<0)
				tmp_rotation+=360;
			dx=-MOVE_DELTA*Math.sin(Math.toRadians(tmp_rotation));
			dy=MOVE_DELTA*Math.cos(Math.toRadians(tmp_rotation));
			checkBorders();
			x+=dx;
			y+=dy;
			collider.update(x,y);
			rotate();
		}
	}
	
	public void rotate(){
		double a=mousex-x-image.getWidth(null)/2;
		double b=y+image.getWidth(null)/2-mousey;
		if(b!=0)
			rotation=Math.atan2(a,b);
	}
	
	public int getHP(){
		return hp;
	}
	
	public int getMP(){
		return mp;
	}
	
	public void setHP(int health){
		hp=health;
	}
	
	public void addHP()
	{
		hp+=20;
		if(hp>100)
			hp=100;
	}

	public void takeDamage(int dmg){
		hp -= dmg;
		if(hp<=0)
			System.out.println("I'm dead "+hp);
	}

	public boolean takeMana(int mana){
		if(mp >= mana)
			mp -= mana;
		else
			return false;
		return true;
	}
	
	public void restoreMana(){
		mp = 100;
	}
	
	public void checkBorders(){
		
		Rectangle player = getBordersAfterMove();
		Rectangle gamescene = new Rectangle(0, SCOREBOARD_HEIGHT, WINDOW_WIDTH, ARENA_HEIGHT);

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
}
