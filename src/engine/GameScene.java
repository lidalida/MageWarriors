package engine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.Timer;

import com.sun.org.apache.xpath.internal.operations.Mod;

import net.GameServer;

public class GameScene implements Commons, GameCommons, ActionListener{
		
	public List<Drawable> models = new ArrayList<Drawable>();
	public GameServer gameServer;

	public boolean[] player1_flags = new boolean[FLAG_COUNT];
	boolean[] player2_flags = new boolean[FLAG_COUNT];
	private int player1_modificator, player2_modificator;
	public int targetX_p1, targetY_p1, targetX_p2, targetY_p2;
	
	Player player1, player2;
	Bar p1_hp, p1_mp, p2_hp, p2_mp;
	Missile missile;
	Model m;
	Item item;
	Random rnd;
	ImageIcon ii;
	private int item_x=0, item_y=0;
	double super_spell_time_p1, super_spell_time_p2, super_spell_start_time_p1, super_spell_start_time_p2, items_generating_time, items_generating_time_prev;
	public Timer timer;
	
	private int models_count;	
	public boolean gameOver=false;
	public int winnerID;
	
	public GameScene()
	{
		init();
	}
	
	public void init()
	{
		models.clear();
		models_count=0;
		gameOver=false;
		winnerID=0;
		player1=new Player();
		player2=new Player();
		rnd=new Random();
		items_generating_time_prev=System.currentTimeMillis();
		timer = new Timer(FRAMETIME, this);

	}
	
	public void startGame()
	{
		timer.start();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		gameUpdate();
		if(gameServer==null)
			return;		
		
		for(int i=0;i<models.size();i++){
			Model tmp = (Model) models.get(i);
			if(tmp.getClass()==Item.class)
				continue;
			gameServer.sendPositionMsg(tmp.getID(),tmp.getX(),tmp.getY(),tmp.getRotation());
		}			
	}
	
	public void gameUpdate()
	{		
			checkMoves(player1, player1_flags, player1_modificator);
			checkMoves(player2, player2_flags, player2_modificator);
			checkRegularSpells(player1, player1_flags, player1_modificator);	
			checkRegularSpells(player2, player2_flags, player2_modificator);
			checkSuperSpells(player1, player1_flags, player1_modificator);
			checkSuperSpells(player2, player2_flags, player2_modificator);
			checkForbiddenRotation();
			checkCollisions();
			checkSuperSpellTime();	
			items_generating_time=System.currentTimeMillis();
			if(items_generating_time-items_generating_time_prev>ITEM_SPAWN_TIME)
			{
				generateItems();
				items_generating_time_prev=items_generating_time;
			}
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
		((Model)model).setID(++models_count);
		models.add(model);
		if(models.size()==1)
			player1 = (Player)model;

		else if(models.size()==2)
			player2 = (Player)model;
	}
	
	public void makeBars(){
		p1_hp = new Bar(200,20,HEALTH_BAR,PLAYER_HEALTH,player1);
		p1_mp = new Bar(200,60,MANA_BAR,PLAYER_MANA,player1);
		p2_hp = new Bar(WINDOW_WIDTH-PLAYER_HEALTH-100,20,HEALTH_BAR,PLAYER_HEALTH,player2);
		p2_mp = new Bar(WINDOW_WIDTH-PLAYER_MANA-100,60,MANA_BAR,PLAYER_MANA,player2);
	}
	
	public void setMousePos(int x, int y, int player_id)
	{
		if(player_id==1)
		{
			targetX_p1=x;
			targetY_p1=y;
		}
		
		else if(player_id==2)
		{
			targetX_p2=x;
			targetY_p2=y;
		}
	}
	
	public void setFlag(int i, boolean value, int player_id)
	{
		if(player_id==1)
			player1_flags[i]=value;
		else if(player_id==2)
			player2_flags[i]=value;
	}
	
	public boolean getFlag(int i, int player_id)
	{
		if(player_id==1)
			return player1_flags[i];
		else
			return player2_flags[i];

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
			gameServer.sendEventMsg(item.getID(), ADD_ITEM, item.getType());
			gameServer.sendPositionMsg(item.getID(), item.getX(), item.getY(), 0);
			gameServer.sendPositionMsg(item.getID(), item.getX(), item.getY(), 0);
			System.out.println(models.size());
		}
	}
	
	private void checkForbiddenRotation()
	{
		if(player1.collider.collides(player2.collider))
		{
			double a=player2.getX()+player2.getImage().getWidth(null)/2-player1.getX()-player1.getImage().getWidth(null)/2;
			double b=player1.getY()+player1.getImage().getWidth(null)/2-player2.getY()-player2.getImage().getWidth(null)/2;
			double rot1=Math.toDegrees(Math.atan2(a, b));

			double c=-a;
			double d=-b;
			double rot2=Math.toDegrees(Math.atan2(c, d));
			
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
		
	}
	
	private void collisionsWithMissiles(Missile miss, Iterator<Drawable> it)
	{
		miss.move();
		if(++miss.lifetime>100){
			it.remove();
			models.remove(miss);
			gameServer.sendEventMsg(miss.getID(), DELETE_OBJECT, 0);
			miss = null;
		}
		else if(player1.collider.collides(miss.collider))
			collisionPlayerMissile(miss, player1, it);
		else if(player2.collider.collides(miss.collider))
			collisionPlayerMissile(miss, player2, it);
		
	}
	
	private void collisionPlayerMissile(Missile miss, Player player, Iterator<Drawable> it)
	{
		player.takeDamage(DAMAGE);
		gameServer.sendEventMsg(player.getID(), CHANGE_HP, player.getHP());
		it.remove();
		models.remove(miss);
		gameServer.sendEventMsg(miss.getID(), DELETE_OBJECT, 0);
		miss = null;
		if(player.getHP()<=0){
			gameOver = true;
			if(player.getID()==1)
				winnerID = 2;
			else 
				winnerID=1;
			gameServer.sendEventMsg(0, GAME_OVER, winnerID);
		}
	}
	
	private void collisionsWithItems(Item item, Iterator<Drawable> it)
	{
		if(player1.collider.collides(item.collider))	
			collisionPlayerItem(player1, item, it);
		if(player2.collider.collides(item.collider))	
			collisionPlayerItem(player2, item, it);
	}
	
	private void collisionPlayerItem(Player player, Item item, Iterator<Drawable> it)
	{
		if(item.type==MANA)
		{
			player.restoreMana();
			gameServer.sendEventMsg(player.getID(), CHANGE_MP, player.getMP());
		}
		else if(item.type==HP)
		{
			player.addHP();
			gameServer.sendEventMsg(player.getID(), CHANGE_HP, player.getHP());
		}
		else if(item.type==SPEEDUP)
			player.setSuperSpell(SPEEDUP);
		else if(item.type==TELEPORT)
			player.setSuperSpell(TELEPORT);
		else if(item.type==FREEZE)
			player.setSuperSpell(FREEZE);
		it.remove();
		models.remove(item);
		gameServer.sendEventMsg(item.getID(), DELETE_OBJECT, 0);
		item = null;
	}
	
	private void checkCollisions()
	{		
		if(models.size()>1)
		{			
			for(Iterator<Drawable> it = models.subList(1, models.size()).iterator(); it.hasNext(); ){
				
				m = (Model)it.next();
				if(m.getClass()==Missile.class)
					collisionsWithMissiles((Missile)m, it);
				else if(m.getClass()==Item.class)	
					collisionsWithItems((Item)m, it);					
			}			
		}			
	}	
	
	private void checkSuperSpellTime()
	{
		if(player1_modificator==SPEEDUP){
			super_spell_time_p1=System.currentTimeMillis();
			if(super_spell_time_p1-super_spell_start_time_p1>SPEEDUP_TIME)
				player1_modificator=0;
		}		
		if(player2_modificator==SPEEDUP){
			super_spell_time_p2=System.currentTimeMillis();
			if(super_spell_time_p2-super_spell_start_time_p2>SPEEDUP_TIME)
				player2_modificator=0;
		}		
		if(player1_modificator==FREEZE){
			super_spell_time_p1=System.currentTimeMillis();
			if(super_spell_time_p1-super_spell_start_time_p1>FREEZE_TIME){
				player1_modificator=0;
				player1.setImage(REGULAR_IMG);
				gameServer.sendEventMsg(player1.getID(), CHANGE_IMG, REGULAR_IMG);
			}
		}		
		if(player2_modificator==FREEZE){
			super_spell_time_p2=System.currentTimeMillis();
			if(super_spell_time_p2-super_spell_start_time_p2>FREEZE_TIME){
				player2_modificator=0;
				player1.setImage(REGULAR_IMG);
				gameServer.sendEventMsg(player2.getID(), CHANGE_IMG, REGULAR_IMG);
			}
		}		
	}
	
	private void checkMoves(Player player, boolean[] flags, int modificator)
	{
			if(flags[IS_MOVING]){
				if(modificator!=FREEZE){
					player.move();
					if(modificator==SPEEDUP)
							player.move();
				}
			}			
			if(flags[IS_MOVING_BACK]){
				if(modificator!=FREEZE){
					player.moveBack();
					if(modificator==SPEEDUP)
							player.moveBack();
				}
			}			
			if(flags[IS_MOVING_LEFT]){
				if(modificator!=FREEZE){
					player.moveLeft();
					if(modificator==SPEEDUP)
							player.moveLeft();
				}
			}			
			if(flags[IS_MOVING_RIGHT]){
				if(modificator!=FREEZE){
					player.moveRight();
					if(modificator==SPEEDUP)
							player.moveRight();
				}
			}							
			if(flags[IS_ROTATING]){
				if(modificator!=FREEZE){
					if(player.getID()==1){
						player.setMouseX(targetX_p1);
						player.setMouseY(targetY_p1);
						player.rotate();
						player1_flags[IS_ROTATING]=false;
					}
					else{
						player.setMouseX(targetX_p2);
						player.setMouseY(targetY_p2);
						player.rotate();
						player2_flags[IS_ROTATING]=false;
					}					
				}
			}			
	}

	private void checkRegularSpells(Player player, boolean[] flags, int modificator)
	{
		if(modificator!=FREEZE)
		{
			if(flags[IS_CASTING_SPELL_1])
				castSpell1(player);
			if(flags[IS_CASTING_SPELL_2])
				castSpell2(player);
			if(flags[IS_CASTING_SPELL_3])
				castSpell3(player);
		}
	}
	
	private void castSpell1(Player player)
	{
		if(player.takeMana(SPELL1_COST)){
			gameServer.sendEventMsg(player.getID(), CHANGE_MP, player.getMP());
			missile = new Missile(Missile.getXOnRadius(player.getX(),player.getRotation(),25),Missile.getYOnRadius(player.getY(),player.getRotation(),25),player.getRotation());
			addModel(missile);
			gameServer.sendEventMsg(missile.getID(),ADD_MISSILE,0);
			gameServer.sendPositionMsg(missile.getID(), missile.getX(), missile.getY(), 0);
		}
		if(player.getID()==1)
			player1_flags[IS_CASTING_SPELL_1] = false;
		else
			player2_flags[IS_CASTING_SPELL_1] = false;

	}
	
	private void castSpell2(Player player)
	{
		if(player.takeMana(SPELL2_COST)){
			gameServer.sendEventMsg(player.getID(), CHANGE_MP, player.getMP());
			for(int i=0;i<4;i++){
				missile = new Missile(Missile.getXOnRadius(player.getX(),player.getRotation(),25+i*25),Missile.getYOnRadius(player.getY(),player.getRotation(),25+i*25),player.getRotation());
				addModel(missile);
				gameServer.sendEventMsg(missile.getID(),ADD_MISSILE,0);
				gameServer.sendPositionMsg(missile.getID(), missile.getX(), missile.getY(), 0);
			}			
		}
		if(player.getID()==1)
			player1_flags[IS_CASTING_SPELL_2] = false;
		else
			player2_flags[IS_CASTING_SPELL_2] = false;	
	}
	
	private void castSpell3(Player player)
	{
		if(player.takeMana(SPELL3_COST)){
			gameServer.sendEventMsg(player.getID(), CHANGE_MP, player.getMP());
			for(int i=-6;i<7;i++){
				missile = new Missile(Missile.getXOnRadius(player.getX(),player.getRotation()+(double)i*5*DEG_TO_RAD,50),Missile.getYOnRadius(player.getY(),player.getRotation()+(double)i*5*DEG_TO_RAD,50),player.getRotation()+(double)i*5*DEG_TO_RAD);
				addModel(missile);
				gameServer.sendEventMsg(missile.getID(),ADD_MISSILE,0);
				gameServer.sendPositionMsg(missile.getID(), missile.getX(), missile.getY(), 0);
			}			
		}
		if(player.getID()==1)
			player1_flags[IS_CASTING_SPELL_3] = false;
		else
			player2_flags[IS_CASTING_SPELL_3] = false;
	}
	
	private void checkSuperSpells(Player player, boolean[] flags, int modificator)
	{
		if(modificator!=FREEZE)
		{
			if(flags[IS_CASTING_SUPER_SPELL])
			{
				if(player.getSuperSpell()==SPEEDUP)
					castSpeedup(player);
				else if(player.getSuperSpell()==TELEPORT)
					castTeleport(player);
				else if(player.getSuperSpell()==FREEZE)
					castFreeze(player);
				else
				{
					if(player.getID()==1)
						player1_flags[IS_CASTING_SUPER_SPELL]=false;
					else
						player2_flags[IS_CASTING_SUPER_SPELL]=false;
				}
			}
		}
	}
	
	private void castSpeedup(Player player)
	{
		if(player.getID()==1)
		{
			player1_modificator=SPEEDUP;
			super_spell_start_time_p1 = System.currentTimeMillis();
			player1.setSuperSpell(0);
			player1_flags[IS_CASTING_SUPER_SPELL]=false;				
		}
		else
		{
			player2_modificator=SPEEDUP;
			super_spell_start_time_p2 = System.currentTimeMillis();
			player2.setSuperSpell(0);
			player2_flags[IS_CASTING_SUPER_SPELL]=false;				
		}
	}
	
	private void castTeleport(Player player)
	{
		if(player.getID()==1)
		{
			player1_modificator=0;
			if(targetY_p1>player1.getImage().getHeight(null)/2 && targetX_p1>player1.getImage().getWidth(null)/2 && targetY_p1<ARENA_HEIGHT-player1.getImage().getHeight(null)/2 && targetX_p1<WINDOW_WIDTH-player1.getImage().getWidth(null)/2 && ((targetX_p1 > player2.getX()+player2.getImage().getWidth(null)+player1.getImage().getWidth(null)/2 || targetX_p1 < player2.getX()-player1.getImage().getWidth(null)/2) || (targetY_p1 > player2.getY()+player2.getImage().getWidth(null)+player1.getImage().getHeight(null)/2 || targetY_p1 < player2.getY()-player1.getImage().getHeight(null)/2)))
			{
				player.setPosition(targetX_p1-player.getImage().getWidth(null)/2, targetY_p1-player.getImage().getHeight(null)/2);
				player.collider.update(player.getX(), player.getY());
				player.setSuperSpell(0);
			}
			player1_flags[IS_CASTING_SUPER_SPELL]=false;
		}
		else if(player.getID()==2)
		{
			player2_modificator=0;
			if(targetY_p2>player2.getImage().getHeight(null)/2 && targetX_p2>player2.getImage().getWidth(null)/2 && targetY_p2<ARENA_HEIGHT-player2.getImage().getHeight(null)/2 && targetX_p2<WINDOW_WIDTH-player2.getImage().getWidth(null)/2 && ((targetX_p2 > player1.getX()+player1.getImage().getWidth(null)+player2.getImage().getWidth(null)/2 || targetX_p2 < player1.getX()-player2.getImage().getWidth(null)/2) || (targetY_p2 > player1.getY()+player1.getImage().getWidth(null)+player2.getImage().getHeight(null)/2 || targetY_p2 < player1.getY()-player2.getImage().getHeight(null)/2)))
			{
				player.setPosition(targetX_p2-player.getImage().getWidth(null)/2, targetY_p2-player.getImage().getHeight(null)/2);
				player.collider.update(player.getX(), player.getY());
				player.setSuperSpell(0);
			}
			player2_flags[IS_CASTING_SUPER_SPELL]=false;
		}
	}
	
	private void castFreeze(Player player)
	{
		if(player.getID()==1)
		{
			player2_modificator=FREEZE;
			player1.setSuperSpell(0);
			super_spell_start_time_p2=System.currentTimeMillis();
			player2.setImage(FROZEN_IMG);
			gameServer.sendEventMsg(player2.getID(), CHANGE_IMG, FROZEN_IMG);
			player1_flags[IS_CASTING_SUPER_SPELL]=false;
		}
		else
		{
			player1_modificator=FREEZE;
			player2.setSuperSpell(0);
			super_spell_start_time_p1=System.currentTimeMillis();
			player1.setImage(REGULAR_IMG);
			gameServer.sendEventMsg(player1.getID(), CHANGE_IMG, FROZEN_IMG);
			player2_flags[IS_CASTING_SUPER_SPELL]=false;
		}
	}	

}
