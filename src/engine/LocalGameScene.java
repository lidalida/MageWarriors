package engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LocalGameScene implements Commons {
	
	public List<Drawable> models = new ArrayList<Drawable>();
	private Model m;
	Bar p1_hp, p1_mp, p2_hp, p2_mp;
	public int gameOver=0;
	
	
	public Drawable findModelByID(int i)
	{
		for(Iterator<Drawable> it = models.iterator(); it.hasNext(); )
		{
			m=(Model)it.next();
			if(m.getID()==i)
				return (Drawable)m;
		}
		return null;
	}
	
	public void addModel(Drawable model, int id)
	{
		models.add(model);
		((Model)model).setID(id);
	}
	
	public void removeModel(int i)
	{
		for(Iterator<Drawable> it = models.iterator(); it.hasNext(); )
		{
			m=(Model)it.next();
			if(m.getID()==i)
			{
				it.remove();
				models.remove(m);
				m=null;
			}
		}
	}
	
	public void makeBars(){
		p1_hp = new Bar(200,20,HEALTH_BAR,PLAYER_HEALTH,(Player)models.get(0));
		p1_mp = new Bar(200,60,MANA_BAR,PLAYER_MANA,(Player)models.get(0));
		p2_hp = new Bar(WINDOW_WIDTH-PLAYER_HEALTH-100,20,HEALTH_BAR,PLAYER_HEALTH,(Player)models.get(1));
		p2_mp = new Bar(WINDOW_WIDTH-PLAYER_MANA-100,60,MANA_BAR,PLAYER_MANA,(Player)models.get(1));
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

}
