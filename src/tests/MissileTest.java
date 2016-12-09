package tests;

import engine.Player;
import engine.Missile;

import static org.junit.Assert.assertEquals;

import javax.swing.ImageIcon;

import org.junit.Before;
import org.junit.Test;

public class MissileTest {
	Player player;
	Missile missile;
	
	@Before
	public void setUp(){
		player = new Player();
		player.setRotation(1.57);
		missile = new Missile(player);
	}
	
	@Test
	public void constructedMissileHaveGoodData(){
		assertEquals(50,missile.getX(),1);
		assertEquals(25,missile.getY(),1);
		assertEquals(1.57,missile.getRotation(),1e-15);
		assertEquals(new ImageIcon("src/res/missile.png").getImage(),missile.getImage());
	}
	
	@Test
	public void moveUpdatePositionCorrectly(){
		final int originalX = missile.getX();
		final int originalY = missile.getY();
		missile.move();
		int deltaX = missile.getX() - originalX;
		int deltaY = missile.getY() - originalY;
		assertEquals(deltaX,10,1);
	}
}
