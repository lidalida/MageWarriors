package engine;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;


public class GameScene extends Scene implements Commons{
		
	public List<Drawable> models = new ArrayList<Drawable>();

	public boolean[] player1_flags = new boolean[8];
	boolean[] player2_flags = new boolean[8];
	public int mouseX, mouseY;
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
	
	public boolean getFlag(int i)
	{
		return player1_flags[i];
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
					missile.move();
					if(++missile.lifetime>100){
						it.remove();
						models.remove(missile);
						missile = null;
					}
						
					else if(player1.collider.collides(((Missile)m).collider))	//kolizja playera z missile
					{							
						it.remove();
						models.remove(missile);
						missile = null;
					}
					
					else if(player2.collider.collides(((Missile)m).collider))	//kolizja playera z missile
					{
						player2.takeDamage(30);
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
	
			
	public void gameUpdate()
	{
			if(painted)
			{
				//checkCollisions();
				if(player1_flags[IS_MOVING])	
					player1.move();
				
				if(player1_flags[IS_MOVING_BACK])
					player1.moveBack();
								
				if(player1_flags[IS_ROTATING])
				{
					player1.setMouseX(mouseX);
					player1.setMouseY(mouseY);
					player1.rotate();
					player1_flags[IS_ROTATING]=false;
				}
				
				if(player2_flags[IS_MOVING])				
					player2.move();
				
				if(player2_flags[IS_MOVING_BACK])
					player2.moveBack();				
				
				if(player2_flags[IS_ROTATING])
				{
					player2.setMouseX(mouseX);
					player2.setMouseY(mouseY);
					player2.rotate();
					player2_flags[IS_ROTATING]=false;
					
				}
				painted=false;
			}
			
			//akcje, któe mog¹ zachodziæ wiele razy na klatkê
			
			if(player1_flags[IS_CASTING_SPELL_1])
			{
				if(player1.takeMana(10)){
					missile = new Missile(Missile.getXOnRadius(player1.getX(),player1.getRotation(),25),Missile.getYOnRadius(player1.getY(),player1.getRotation(),25),player1.getRotation());
					addModel(missile);
				}
				else
					System.out.println("Not enough mana points!!!");
				player1_flags[IS_CASTING_SPELL_1] = false;
				player1_flags[IS_SPELL_CRAFTED] = true;
			}
			
			if(player1_flags[IS_CASTING_SPELL_2])
			{
				if(player1.takeMana(30)){
					for(int i=0;i<4;i++){
						missile = new Missile(Missile.getXOnRadius(player1.getX(),player1.getRotation(),25+i*25),Missile.getYOnRadius(player1.getY(),player1.getRotation(),25+i*25),player1.getRotation());
						addModel(missile);
					}
					
				}
				else
					System.out.println("Not enough mana points!!!");
				player1_flags[IS_CASTING_SPELL_2] = false;
				player1_flags[IS_SPELL_CRAFTED] = true;
			}
			
			if(player1_flags[IS_CASTING_SPELL_3])
			{
				if(player1.takeMana(80)){
					for(int i=-6;i<7;i++){
						missile = new Missile(Missile.getXOnRadius(player1.getX(),player1.getRotation()+(double)i*5*DEG_TO_RAD,50),Missile.getYOnRadius(player1.getY(),player1.getRotation()+(double)i*5*DEG_TO_RAD,50),player1.getRotation()+(double)i*5*DEG_TO_RAD);
						addModel(missile);
					}
					
				}
				else
					System.out.println("Not enough mana points!!!");
				player1_flags[IS_CASTING_SPELL_3] = false;
				player1_flags[IS_SPELL_CRAFTED] = true;
			}
			
			if(player2_flags[IS_CASTING_SPELL_1])
			{
				missile = new Missile(player2.getX(),player2.getY(),player2.getRotation());
				addModel(missile);
			}
			
			if(player1_flags[TMP_MANA_CHARGER])
				player1.restoreMana();
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
