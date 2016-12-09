package tests;

import model.Missile;
import model.Player;
import view.SwingView;

import static org.junit.Assert.assertEquals;

import javax.swing.ImageIcon;

import org.junit.Before;
import org.junit.Test;

public class SwintViewTest {
	SwingView view;
	
	@Before
	public void setUp(){
		view = new SwingView();
	}
	
	@Test
	public void isWindowsSizeGoodAfterSettingUp(){
		assertEquals(800,view.getBounds().width);
		assertEquals(600,view.getBounds().height);
	}
}
