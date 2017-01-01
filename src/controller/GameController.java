package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.Timer;

import model.Drawable;
import model.Missile;
import model.Model;
import model.Player;
import view.SwingView;
import view.View;

public class GameController implements Controller, PropertyChangeListener, ActionListener{
	
	public static final int PLAYER_MOVE_DELTA = 5;
	public static final int DELAY=10;

	public View view;
	public Player player, enemy;
	public Missile missile;
	public List<Drawable> models = new ArrayList<Drawable>();
	Iterator<Drawable> iter = models.iterator();
	public Timer timer;
	
	public GameController()
	{
		timer=new Timer(DELAY, this);
		timer.start();		
	}
	public void addView(View view)
	{
		this.view=view;
	}
	
	public void addModel(Drawable model)
	{
		models.add(model);
		if(models.size()==1)
			player = (Player)model;
		else if(models.size()==2)
			enemy = (Player)model;
		else if(models.size()>2)
			missile = (Missile)model;
		((Model)model).addPropertyChangeListener(this);
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
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			missile = new Missile(player);
			addModel(missile);
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
		for(Drawable it: models){
			if(it.getClass()==player.getClass())
				continue;
			missile = (Missile)it;
			if(missile != null)
				if(missile.IsToDelete()){
					models.remove(missile);
					missile = null;
					break;
				}
		}
			
		player.setMouseX(e.getX());
		player.setMouseY(e.getY());
		player.setIsRotating(1);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(models.size()>1)
		{
			for(Iterator<Drawable> it = models.subList(1, models.size()).iterator(); it.hasNext(); ){
				Model m = (Model)it.next();
				if(m.getClass()==Missile.class)	
				{
					if(enemy.collider.collides(((Missile)m).collider))	//kolizja enemy z missile
					{

						it.remove();
						missile=(Missile)m;
						//models.remove(missile);
						missile = null;
					}
				}
				else if(m.getClass()==player.getClass())
				{

					if(player.collider.collides(((Player)m).collider))	//kolizja playera z playerem
					{
						double a=m.getX()+m.getImage().getWidth(null)/2-player.getX()-player.getImage().getWidth(null)/2;
						double b=player.getY()+player.getImage().getWidth(null)/2-m.getY()-m.getImage().getWidth(null)/2;
						double rot=Math.atan2(a, b);
						//System.out.println(rot);
						player.setForbiddenRotation(rot);

					}
					else
						player.setForbiddenRotation(1000);				
				}
			}
		
		
		}
	}
}
