package engine;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GameController implements Controller, PropertyChangeListener{
	
	public static final int PLAYER_MOVE_DELTA = 5;

	public View view;
	public Player player;
	
	public void addView(View view)
	{
		this.view=view;
	}
	
	public void addModel(Model model)
	{
		player=(Player)model;
		model.addPropertyChangeListener(this);
	}
	
	public void propertyChange(PropertyChangeEvent e)
	{
		view.modelPropertyChange(e);
	}
	
	public void keyPressed(KeyEvent e){
		
		if(e.getKeyCode() == KeyEvent.VK_W) {			
			player.setIsMoving(1);
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
		player.setMouseX(e.getX());
		player.setMouseY(e.getY());
		player.setIsRotating(1);
	}
}
