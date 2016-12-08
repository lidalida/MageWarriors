package engine;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class GameController implements Controller, PropertyChangeListener{
	
	public static final int PLAYER_MOVE_DELTA = 5;

	public View view;
	public Player player;
	public Missile missile;
	List<Model> models = new ArrayList<Model>();
	
	public void addView(View view)
	{
		this.view=view;
	}
	
	public void addModel(Model model)
	{
		models.add(model);
		if(models.size()==1)
			player = (Player)model;
		if(models.size()>2)
			missile = (Missile)model;
		model.addPropertyChangeListener(this);
	}
	
	public void propertyChange(PropertyChangeEvent e)
	{
		view.modelPropertyChange(e);
	}
	
	public void keyPressed(KeyEvent e){
		
		if(e.getKeyCode() == KeyEvent.VK_W) {			
			player.setIsMoving(1);
			missile = new Missile(player);
			addModel(missile);
			view.addModel(missile);
			System.out.println(models.size());
		}
		if(e.getKeyCode() == KeyEvent.VK_S) {
			player.setIsMoving(2);
		}
		
	}	
	
	public void keyReleased(KeyEvent e){
		
		if(e.getKeyCode() == KeyEvent.VK_W) {			
			player.setIsMoving(0);
		}
		if(e.getKeyCode() == KeyEvent.VK_S) {
			player.setIsMoving(0);
		}
	}
	
	public void mouseMoved(MouseEvent e){
		for(Model it: models){
			if(it.getClass()==player.getClass())
				continue;
			missile = (Missile)it;
			if(missile != null)
				if(missile.IsToDelete()){
					models.remove(missile);
					((SwingView)view).models.remove(missile);
					missile = null;
					break;
				}
		}
			
		player.setMouseX(e.getX());
		player.setMouseY(e.getY());
		player.setIsRotating(1);
	}
}
