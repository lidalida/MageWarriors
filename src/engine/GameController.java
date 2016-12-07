package engine;

import java.beans.PropertyChangeEvent;

public class GameController implements Controller{
	
	public View view;
	public Player player;
	
	public void addView(View view)
	{
		this.view=view;
	}
	
	public void addModel(Model model)
	{
		player=(Player)model;
	}
	
	public void propertyChange(PropertyChangeEvent e)
	{
		view.modelPropertyChange(e);
	}

}
