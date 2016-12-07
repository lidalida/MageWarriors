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
			System.out.println("yup2");
			player.move();
			System.out.println("yup3");
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
