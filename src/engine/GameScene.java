package engine;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class GameScene extends Scene implements Commons{
		
	public List<Drawable> models = new ArrayList<Drawable>();

	public boolean[] player1_flags = new boolean[FLAG_COUNT];
	boolean[] player2_flags = new boolean[FLAG_COUNT];
	public int mouseX, mouseY;
	public boolean painted = false;
	private int player1_modificator, player2_modificator;
	private int player1_modificator_counter, player2_modificator_counter;
	Player player1, player2;
	Bar p1_hp, p1_mp, p2_hp, p2_mp;
	Missile missile;
	double a, b, c, d, rot1, rot2;
	Model m;
	Item item;
	Random rnd;
	private int item_x=0, item_y=0;
	
	public GameScene()
	{
		player1=new Player();
		player2=new Player();
		rnd=new Random();

	}
	
	public Bar getBar(int player_id, int type){
		if(player_id==1)
			if(type==0)
				return p1_hp;
			else
				return p1_mp;
		else
			if(type==0)
				return p2_hp;
			else
				return p2_mp;
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
		//else if(models.size()>2)
			//missile = (Missile)model;
	}
	
	public void makeBars(){
		p1_hp = new Bar(200,20,HEALTH_BAR,PLAYER_HEALTH,player1);
		p1_mp = new Bar(200,60,MANA_BAR,PLAYER_MANA,player1);
		p2_hp = new Bar(WINDOW_WIDTH-PLAYER_HEALTH-100,20,HEALTH_BAR,PLAYER_HEALTH,player2);
		p2_mp = new Bar(WINDOW_WIDTH-PLAYER_MANA-100,60,MANA_BAR,PLAYER_MANA,player2);
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
	
	public void generateItems()
	{
		int r=rnd.nextInt(2);
		if(r==0)
		{			
			do
			{
				item_x=rnd.nextInt(WINDOW_WIDTH-40);
				item_y=rnd.nextInt(ARENA_HEIGHT-40);
			} while((item_x>player1.getX() && item_x<player1.getX()+50) || 
					(item_x>player2.getX() && item_x<player2.getX()+50) ||
					(item_y>player1.getY() && item_y<player1.getY()+50) ||
					(item_y>player2.getY() && item_y<player2.getY()+50));		
			Item item=new Item(item_x, item_y);
			addModel(item);
		}
	}
	
	private void checkCollisions()
	{
		
		if(models.size()>1)
		{
			if(player1.collider.collides(player2.collider))
			{
				a=player2.getX()+player2.getImage().getWidth(null)/2-player1.getX()-player1.getImage().getWidth(null)/2;
				b=player1.getY()+player1.getImage().getWidth(null)/2-player2.getY()-player2.getImage().getWidth(null)/2;
				rot1=Math.toDegrees(Math.atan2(a, b));

				c=-a;
				d=-b;
				rot2=Math.toDegrees(Math.atan2(c, d));
				
				if(player1_flags[IS_MOVING_BACK])
				{
					rot1-=180;
					if(rot1<0)
						rot1+=360;
				}
				if(player1_flags[IS_MOVING_LEFT])
				{
					rot1+=90;
					if(rot1<0)
						rot1+=360;
				}
				if(player1_flags[IS_MOVING_RIGHT])
				{
					rot1-=90;
					if(rot1<0)
						rot1+=360;
				}
				
				if(player2_flags[IS_MOVING_BACK])
				{
					rot2-=180;
					if(rot2<0)
						rot2+=360;
				}
				if(player2_flags[IS_MOVING_LEFT])
				{
					rot2+=90;
					if(rot2<0)
						rot2+=360;
				}
				if(player2_flags[IS_MOVING_RIGHT])
				{
					rot2-=90;
					if(rot2<0)
						rot2+=360;
				}
				
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
				else if(m.getClass()==Item.class)		//kolizja playera z itemem
				{
					if(player1.collider.collides(((Item)m).collider))	
					{			
						System.out.println("hp: " + player1.getHP() + " mana: " + player1.getMP() + "super spell: " + player1.getSuperSpell());
						item=(Item)m;
						if(item.type==MANA)
							player1.restoreMana();
						else if(item.type==HP)
							player1.addHP();
						else if(item.type==SPEEDUP)
							player1.setSuperSpell(SPEEDUP);
						else if(item.type==TELEPORT)
							player1.setSuperSpell(TELEPORT);
						it.remove();
						models.remove(item);
						item = null;
						System.out.println("hp: " + player1.getHP() + " mana: " + player1.getMP()+ "super spell: " + player1.getSuperSpell());

					}
					else if(player2.collider.collides(((Item)m).collider))	
					{			
						item=(Item)m;
						if(item.type==MANA)
							player2.restoreMana();
						else if(item.type==HP)
							player2.addHP();
						else if(item.type==SPEEDUP)
							player2.setSuperSpell(SPEEDUP);
						else if(item.type==TELEPORT)
							player1.setSuperSpell(TELEPORT);
						it.remove();
						models.remove(item);
						item = null;
					}
					
				}
				
				
			}
			}
		}
			
	}
	
			
	public void gameUpdate()
	{
			if(painted)
			{
				if(player1_flags[IS_MOVING])
				{
					player1.move();
					if(player1_modificator==SPEEDUP)
							player1.move();
				}
				
				if(player1_flags[IS_MOVING_BACK])
				{
					player1.moveBack();
					if(player1_modificator==SPEEDUP)
							player1.moveBack();
				}
				
				if(player1_flags[IS_MOVING_LEFT])
				{
					player1.moveLeft();
					if(player1_modificator==SPEEDUP)
							player1.moveLeft();
				}
				
				if(player1_flags[IS_MOVING_RIGHT])
				{
					player1.moveRight();
					if(player1_modificator==SPEEDUP)
							player1.moveRight();
				}					
								
				if(player1_flags[IS_ROTATING])
				{
					player1.setMouseX(mouseX);
					player1.setMouseY(mouseY);
					player1.rotate();
					player1_flags[IS_ROTATING]=false;
				}
				
				if(player2_flags[IS_MOVING])				
				{
					player2.move();
					if(player2_modificator==SPEEDUP)
						player2.move();
				}
				
				if(player2_flags[IS_MOVING_BACK])				
				{
					player2.moveBack();
					if(player2_modificator==SPEEDUP)
						player2.moveBack();
				}
				
				if(player2_flags[IS_MOVING_LEFT])
				{
					player2.moveLeft();
					if(player2_modificator==SPEEDUP)
						player2.moveLeft();
				}
				
				if(player2_flags[IS_MOVING_RIGHT])
				{
					player2.moveRight();
					if(player2_modificator==SPEEDUP)
						player2.moveRight();
				}		
				
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
			
			if(player1_flags[IS_CASTING_SUPER_SPELL])
			{
				if(player1.getSuperSpell()==SPEEDUP)
				{
					player1_modificator=SPEEDUP;
					player1_modificator_counter++;
					if(player1_modificator_counter==FRAMETIME*10)
					{
						player1_modificator_counter=0;
						player1.setSuperSpell(0);
						player1_modificator=0;
						player1_flags[IS_CASTING_SUPER_SPELL]=false;
					}
				}
				else if(player1.getSuperSpell()==TELEPORT)
				{
					player1_modificator=0;
					player1_modificator_counter=0;
					if(mouseY>0 && mouseX>0 && mouseY<ARENA_HEIGHT-50 && mouseX<WINDOW_WIDTH-50 && ((mouseX > player2.getX()+50 || mouseX < player2.getX()-50) || (mouseY > player2.getY()+50 || mouseY < player2.getY()-50)))
					{
						player1.setPosition(mouseX, mouseY);
						player1.collider.update(player1.getX(), player1.getY());
						player1.setSuperSpell(0);
					}
					player1_flags[IS_CASTING_SUPER_SPELL]=false;
				}
				else
					player1_flags[IS_CASTING_SUPER_SPELL]=false;
					
			}
			
			if(player2_flags[IS_CASTING_SUPER_SPELL])
			{
				if(player2.getSuperSpell()==SPEEDUP)
				{
					player2_modificator=SPEEDUP;
					player2_modificator_counter++;
					if(player2_modificator_counter==FRAMETIME*10)
					{
						player2_modificator_counter=0;
						player2.setSuperSpell(0);
						player2_modificator=0;
						player2_flags[IS_CASTING_SUPER_SPELL]=false;
					}
				}	
				else if(player2.getSuperSpell()==TELEPORT)
				{
					player2_modificator=0;
					player2_modificator_counter=0;
					if(mouseY>0 && mouseX>0 && mouseY<ARENA_HEIGHT-50 && mouseX<WINDOW_WIDTH-50 && (mouseX > player1.getX()+50 || mouseX < player1.getX()-50) || (mouseY > player1.getY()+50 || mouseY < player1.getY()-50))
					{
						player2.setPosition(mouseX, mouseY);
						player2.collider.update(player2.getX(), player2.getY());
						player2.setSuperSpell(0);
					}
					player2_flags[IS_CASTING_SUPER_SPELL]=false;
				}
				else
					player2_flags[IS_CASTING_SUPER_SPELL]=false;
					
			}
			//wykrywanie kolizji		
			
			
			checkCollisions();
			
	}

}
