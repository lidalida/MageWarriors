package engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LocalGameScene {
	
	public List<Drawable> models = new ArrayList<Drawable>();
	private Model m;
	
	
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

}
