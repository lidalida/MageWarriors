package engine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class BarElement implements Drawable, Commons {
	
	private int x;
	private int y;
	private Image image;
	
	public BarElement(int pos_x, int pos_y, int img){
		x = pos_x;
		y = pos_y;
		ImageIcon ii;
		if(img == HEALTH_BAR)
			ii = new ImageIcon("src/res/healthbar.png");
		else
			ii = new ImageIcon("src/res/manabar.png");
		image=ii.getImage();
	}
	
	@Override
	public void draw(Graphics g1) {
		Graphics2D g = (Graphics2D) g1.create();
		g.drawImage(image, x, y, null);
	}

}
