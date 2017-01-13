package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import engine.Commons;
import engine.LocalGameScene;
import engine.Player;

public class BarView extends JPanel implements Commons, PropertyChangeListener {

	private static final long serialVersionUID = -2659427042057456193L;
	public static final int WIDTH=800, HEIGHT=100;

	private TexturePaint paint_scoreboard;
    private BufferedImage bi_sb;
    private ImageIcon ii;
    private Image image_p1, image_p2, image_bar_border;
    private LocalGameScene localGameScene;

	public BarView()
	{
		setSize(WINDOW_WIDTH, SCOREBOARD_HEIGHT);
		this.setPreferredSize(new Dimension(WINDOW_WIDTH, SCOREBOARD_HEIGHT));

		setFocusable(true);
		try {
			bi_sb = ImageIO.read(new File("src/res/texture_scoreboard.png"));
		} catch (IOException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.paint_scoreboard = new TexturePaint(bi_sb, new Rectangle(0, 0, bi_sb.getWidth(), bi_sb.getHeight()));
		 ii = new ImageIcon("src/res/player1_text.png");
		 image_p1=ii.getImage();
		 ii = new ImageIcon("src/res/player2_text.png");
		 image_p2=ii.getImage();
		 ii = new ImageIcon("src/res/bar_border.png");
		 image_bar_border=ii.getImage();
		 
	 
		
	}
	
	public void setLocalGameScene(LocalGameScene gs)
	{
		localGameScene=gs;
		 ((Player)localGameScene.models.get(0)).addPropertyChangeListener(this);
		 ((Player)localGameScene.models.get(1)).addPropertyChangeListener(this);
		 
	}
	
	public void paintComponent(Graphics g1) {
        super.paintComponent(g1);
       

		Graphics2D g = (Graphics2D) g1;
        g.setPaint(paint_scoreboard);
		g.fillRect(0, 0, WINDOW_WIDTH, SCOREBOARD_HEIGHT);
		g.drawImage(image_p1, 60, 20, null);
		g.drawImage(image_p2, 460, 20, null);
		g.drawImage(image_bar_border, 200-3, 20-3, null);
		g.drawImage(image_bar_border, 200-3, 60-3, null);
		g.drawImage(image_bar_border, WINDOW_WIDTH-PLAYER_HEALTH-100-3, 20-3, null);
		g.drawImage(image_bar_border, WINDOW_WIDTH-PLAYER_MANA-100-3, 60-3, null);


		
		localGameScene.getBar(1,0).draw(g1);
		localGameScene.getBar(1,1).draw(g1);
		localGameScene.getBar(2,0).draw(g1);
		localGameScene.getBar(2,1).draw(g1);
		
	    Toolkit.getDefaultToolkit().sync();
    }


	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		repaint();
		
	}



}
