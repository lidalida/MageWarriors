package model;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import view.SwingView;
import view.View;


public class GameScene extends Scene{
		
	public List<Drawable> models = new ArrayList<Drawable>();

	public boolean[] player1_flags = new boolean[4];
	boolean[] player2_flags = new boolean[4];
	public int mouseX, mouseY;
	enum Flags_names{IS_MOVING, IS_MOVING_BACK, IS_SHOOTING, IS_ROTATING};
	public boolean painted = false;
	Player player1, player2;
	Missile missile;
	double a, b, c, d, rot1, rot2;
	Model m;
	//public SwingView v;
	
	public GameScene()
	{
		player1=new Player();
		player2=new Player();
		
	}
	public void addModel(Drawable model)
	{
		synchronized(this.models){
		models.add(model);
		}
		if(models.size()==1)
			player1 = (Player)model;
		else if(models.size()==2)
			player2 = (Player)model;
		else if(models.size()>2)
			missile = (Missile)model;
	}
	
	public void setMousePos(int x, int y)
	{
		mouseX=x;
		mouseY=y;
	}
	
	public void setFlag(int i, boolean value)
	{
		player1_flags[i]=value;
	}
	
	public void setPainted(boolean value)
	{
		painted=value;
	}
	
	private void checkCollisions()
	{
		
		if(models.size()>1)
		{
			if(player1.collider.collides(player2.collider))
			{
				a=player2.getX()+player2.getImage().getWidth(null)/2-player1.getX()-player1.getImage().getWidth(null)/2;
				b=player1.getY()+player1.getImage().getWidth(null)/2-player2.getY()-player2.getImage().getWidth(null)/2;
				rot1=Math.atan2(a, b);
				
				c=-a;
				d=-b;
				rot2=Math.atan2(c, d);
				
				player1.setForbiddenRotation(rot1);
				player2.setForbiddenRotation(rot2);
			}
			else
			{
				player1.setForbiddenRotation(1000);	
				player2.setForbiddenRotation(1000);
			}				
			synchronized(this.models){
			for(Iterator<Drawable> it = models.subList(1, models.size()).iterator(); it.hasNext(); ){
				
				m = (Model)it.next();
				if(m.getClass()==Missile.class)	
				{
					missile=(Missile)m;
					if(missile.IsToDelete()){
						it.remove();
						models.remove(missile);
						missile = null;
					}
						
					else if(player1.collider.collides(((Missile)m).collider))	//kolizja playera z missile
					{														//tu musi byæ odejmowanie ¿ycia

						it.remove();
						models.remove(missile);
						missile = null;
					}
					
					else if(player2.collider.collides(((Missile)m).collider))	//kolizja playera z missile
					{

						it.remove();
						models.remove(missile);
						missile = null;
					}
				}
				
				//tu musz¹ byæ jeszcze kolizje playerów z itemami itd.
				
			}
			}
		}
			
	}
	
			
	public void gameLoop()
	{
		//long t, t_start=System.currentTimeMillis();
		//while(true)
		//{	
			//System.out.println("loop");
			//akcje zachodz¹ce raz na klatkê
			/*t=System.currentTimeMillis();
			if(t-t_start>18)
			{
				checkCollisions();
				t_start=t;
			}*/
			
			
			if(painted)
			{
				//checkCollisions();
				if(player1_flags[Flags_names.IS_MOVING.ordinal()])				
					player1.move();
				
				if(player1_flags[Flags_names.IS_MOVING_BACK.ordinal()])
					player1.moveBack();
								
				if(player1_flags[Flags_names.IS_ROTATING.ordinal()])
				{
					player1.setMouseX(mouseX);
					player1.setMouseY(mouseY);
					player1.rotate();
					player1_flags[Flags_names.IS_ROTATING.ordinal()]=false;
				}
				
				if(player2_flags[Flags_names.IS_MOVING.ordinal()])				
					player2.move();
				
				if(player2_flags[Flags_names.IS_MOVING_BACK.ordinal()])
					player2.moveBack();				
				
				if(player2_flags[Flags_names.IS_ROTATING.ordinal()])
				{
					player2.setMouseX(mouseX);
					player2.setMouseY(mouseY);
					player2.rotate();
					player2_flags[Flags_names.IS_ROTATING.ordinal()]=false;
					
				}
				painted=false;
			}
			
			//akcje, któe mog¹ zachodziæ wiele razy na klatkê
			
			if(player1_flags[Flags_names.IS_SHOOTING.ordinal()])
			{
				missile = new Missile(player1);
				addModel(missile);
			}
			
			if(player2_flags[Flags_names.IS_SHOOTING.ordinal()])
			{
				missile = new Missile(player2);
				addModel(missile);
			}
			
			//wykrywanie kolizji		
			
			
			checkCollisions();
			//usuwanie pocisków
			/*synchronized(this.models){
			for(Drawable it: models){
				if(it.getClass()==new Missile(player1).getClass())
				{
					missile = (Missile)it;
					if(missile != null)
						if(missile.IsToDelete()){
							models.remove(missile);
							missile = null;
							break;
					}
				}
			}
			
			}*/
	}

}
