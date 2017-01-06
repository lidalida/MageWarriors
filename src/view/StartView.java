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
import main.Main;

public class StartView extends JPanel implements Commons {
	
	private static final long serialVersionUID = 6342838906053390199L;
	private JButton button_start, button_credits, button_quit;
	private BufferedImage bi;
	private TexturePaint paint;
	private ImageIcon ii;
	private Image img;
	
	public StartView()
	{
		try {
			bi = ImageIO.read(new File("src/res/texture.png"));
		} catch (IOException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 this.paint = new TexturePaint(bi, new Rectangle(0, 0, bi.getWidth(), bi.getHeight()));
		 
        ii=new ImageIcon("src/res/magewarriors.png");
        img=ii.getImage();
		 
		ImageIcon i1 = new ImageIcon("src/res/button_start.png");
		button_start = new JButton(i1);
		button_start.setContentAreaFilled(false);
		button_start.setFocusPainted(false);
		button_start.setBorderPainted(false);
		button_start.setMargin(new Insets(0,0,0,0));
		button_start.setRolloverIcon(new ImageIcon("src/res/button_start_hover.png"));
		button_start.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				createGame();
			}
		});
		System.out.println("start added");
		
		ImageIcon i2 = new ImageIcon("src/res/button_credits.png");
		button_credits = new JButton(i2);
		button_credits.setContentAreaFilled(false);
		button_credits.setFocusPainted(false);
		button_credits.setBorderPainted(false);
		button_credits.setMargin(new Insets(0,0,0,0));
		button_credits.setRolloverIcon(new ImageIcon("src/res/button_credits_hover.png"));
		button_credits.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
			}
			
		});
		
		ImageIcon i3 = new ImageIcon("src/res/button_quit.png");
		button_quit = new JButton(i3);
		button_quit.setContentAreaFilled(false);
		button_quit.setFocusPainted(false);
		button_quit.setBorderPainted(false);
		button_quit.setMargin(new Insets(0,0,0,0));
		button_quit.setRolloverIcon(new ImageIcon("src/res/button_quit_hover.png"));
		button_quit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
			
		});
		
		Box box = Box.createVerticalBox();
		box.add(Box.createVerticalStrut(350));
		box.add(button_start);
		box.add(Box.createVerticalStrut(20));
		box.add(button_credits);
		box.add(Box.createVerticalStrut(20));
		box.add(button_quit);
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
        g.drawImage(img, WINDOW_WIDTH/2-img.getWidth(null)/2, 20, null);
       
	    Toolkit.getDefaultToolkit().sync();

        
        
	}
	public void createGame()
	{
		SwingView v = new SwingView();
		v.setGameScene(Main.gameScene);
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


}
