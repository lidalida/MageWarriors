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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import engine.Commons;
import main.Main;

public class CreditsView extends JPanel implements Commons{
	

	private static final long serialVersionUID = -316527935793363629L;
	private JButton button_back;
	private BufferedImage bi;
	private TexturePaint paint;
	private ImageIcon ii;
	private Image img;
	
	public CreditsView()
	{
		try {
			bi = ImageIO.read(new File("src/res/texture.png"));
		} catch (IOException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.paint = new TexturePaint(bi, new Rectangle(0, 0, bi.getWidth(), bi.getHeight()));
		ii=new ImageIcon("src/res/credits_view.png");
        img=ii.getImage();
		 
		ImageIcon i1 = new ImageIcon("src/res/button_back.png");
		button_back = new JButton(i1);
		button_back.setContentAreaFilled(false);
		button_back.setFocusPainted(false);
		button_back.setBorderPainted(false);
		button_back.setMargin(new Insets(0,0,0,0));
		button_back.setRolloverIcon(new ImageIcon("src/res/button_back_hover.png"));
		button_back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				returnToMenu();
			}
		});
		
		Box box = Box.createVerticalBox();
		box.add(Box.createVerticalStrut(500));
		box.add(button_back);	
		
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
	
	private void returnToMenu()
	{
		Main.frame.getContentPane().remove(this);
		Main.frame.getContentPane().add(Main.v);
		Main.v.repaint();
		Main.v.revalidate();
	}

}
