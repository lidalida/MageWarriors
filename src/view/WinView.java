package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import engine.Commons;
import engine.GameScene;
import engine.LocalGameScene;
import main.Main;

public class WinView extends JPanel implements Commons{

	private static final long serialVersionUID = 7369417166220359256L;
	private JButton button_play, button_menu;
	private BufferedImage bi;
	private TexturePaint paint;
	private ImageIcon ii;
	private Image img;
	public WinView(int i)
	{
		try {
			bi = ImageIO.read(new File("src/res/texture.png"));
		} catch (IOException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 this.paint = new TexturePaint(bi, new Rectangle(0, 0, bi.getWidth(), bi.getHeight()));
		 
		if(i==1)
			ii=new ImageIcon("src/res/p2_won.png");
		else
			ii=new ImageIcon("src/res/p1_won.png");

        img=ii.getImage();
		 
		ImageIcon i1 = new ImageIcon("src/res/button_play.png");
		button_play = new JButton(i1);
		button_play.setContentAreaFilled(false);
		button_play.setFocusPainted(false);
		button_play.setBorderPainted(false);
		button_play.setMargin(new Insets(0,0,0,0));
		button_play.setRolloverIcon(new ImageIcon("src/res/button_play_hover.png"));
		button_play.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				createGame();
			}
		});
		
		ImageIcon i2 = new ImageIcon("src/res/button_menu.png");
		button_menu = new JButton(i2);
		button_menu.setContentAreaFilled(false);
		button_menu.setFocusPainted(false);
		button_menu.setBorderPainted(false);
		button_menu.setMargin(new Insets(0,0,0,0));
		button_menu.setRolloverIcon(new ImageIcon("src/res/button_menu_hover.png"));
		button_menu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				returnToMenu();
			}
		});
		
		Box box = Box.createVerticalBox();
		box.add(Box.createVerticalStrut(320));
		box.add(button_play);
		box.add(Box.createVerticalStrut(10));
		box.add(button_menu);
		
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		this.add(box);		
		setFocusable(true);
	}
	
	@Override
    public void paintComponent(Graphics g1) {
		super.paintComponent(g1);
		Graphics2D g = (Graphics2D) g1;
        g.setPaint(paint);
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);        
        g.drawImage(img, WINDOW_WIDTH/2-img.getWidth(null)/2, 10, null);
       
	    Toolkit.getDefaultToolkit().sync();       
        
	}
	
	private void createGame()
	{
		Main.gameScene=new GameScene();
		LocalGameScene tmp = new LocalGameScene();
		SwingView v = new SwingView();
		v.setGameScene(tmp,Main.gameScene);
		BarView bv = new BarView();
		bv.setGameScene(Main.gameScene);
		Main.frame.getContentPane().remove(this);
		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		container.add(bv);
		container.add(v);
		Main.frame.getContentPane().add(container);
		Main.frame.setVisible(true);
		v.requestFocus();
		v.repaint();
		bv.repaint();
		v.revalidate();
		bv.revalidate();
		v.startGame();
				
	}
	
	private void returnToMenu()
	{
		Main.frame.getContentPane().remove(this);
		Main.frame.getContentPane().add(Main.v);
		Main.v.repaint();
		Main.v.revalidate();
	}
	
	

}
