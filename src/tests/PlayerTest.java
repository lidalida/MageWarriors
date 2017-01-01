package tests;

import static org.junit.Assert.*;

import javax.swing.ImageIcon;

import org.junit.Before;
import org.junit.Test;

import engine.Player;

public class PlayerTest {
	Player player;
	
	@Before
	public void setUp(){
		player = new Player();
	}
	
	@Test
	public void constructedPlayerHaveGoodData(){
		assertEquals(0,player.getX());
		assertEquals(0,player.getY());
		assertEquals(0,player.getRotation(),1e-15);
		assertEquals(new ImageIcon("src/res/player.png").getImage(),player.getImage());
	}
	
	@Test
	public void moveUpdatePositionCorrectly(){
		player.setRotation(2);
		final int originalX = player.getX();
		final int originalY = player.getY();
		player.move();
		int deltaX = player.getX() - originalX;
		int deltaY = player.getY() - originalY;
		assertEquals(Player.MOVE_DELTA,Math.sqrt(Math.pow(deltaX,2)+Math.pow(deltaY,2)),1);
		player.moveBack();
		assertEquals(0,player.getX());
		assertEquals(0,player.getY());
	}
	
	@Test
	public void playerDoeseNotMoveToBorders(){
		player.move();
		assertEquals(0,player.getY());
		player.setPosition(775, 0);
		player.setRotation(1.57);
		assertEquals(775,player.getX());
		player.setPosition(775, 575);
		player.setRotation(3.14);
		assertEquals(575,player.getY());
		player.setPosition(0, 575);
		player.setRotation(-1.57);
		assertEquals(0,player.getX());
	}
}
