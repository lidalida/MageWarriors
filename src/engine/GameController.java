package engine;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;

public class GameController implements Controller{
	
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
	}
	
	public void propertyChange(PropertyChangeEvent e)
	{
		view.modelPropertyChange(e);
	}
	
	public void keyPressed(KeyEvent e){
		
		if(e.getKeyCode() == KeyEvent.VK_W) {
			player.move();
		}
		if(e.getKeyCode() == KeyEvent.VK_S) {
			player.moveBack();
		}
		
	}	
	public void mouseMoved(MouseEvent e){
		player.setMouseX(e.getX());
		player.setMouseY(e.getY());
	}
}
