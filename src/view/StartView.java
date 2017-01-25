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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import engine.Commons;
import engine.GameScene;
import engine.LocalGameScene;
import engine.Model;
import engine.Player;
import main.Main;
import net.GameClient;
import net.GameServer;

public class StartView extends JPanel implements Commons {
	
	private static final long serialVersionUID = 6342838906053390199L;
	private JButton button_host, button_join, button_credits, button_quit;
	private BufferedImage bi;
	private TexturePaint paint;
	private ImageIcon ii;
	private Image img;
	
	public StartView()
	{
		try {
			bi = ImageIO.read(new File("src/res/texture.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		 this.paint = new TexturePaint(bi, new Rectangle(0, 0, bi.getWidth(), bi.getHeight()));
		 
        ii=new ImageIcon("src/res/magewarriors.png");
        img=ii.getImage();
		 
		ImageIcon i1 = new ImageIcon("src/res/button_host.png");
		button_host = new JButton(i1);
		button_host.setContentAreaFilled(false);
		button_host.setFocusPainted(false);
		button_host.setBorderPainted(false);
		button_host.setMargin(new Insets(0,0,0,0));
		button_host.setRolloverIcon(new ImageIcon("src/res/button_host_hover.png"));
		button_host.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				createGame();
			}
		});
		
		ImageIcon i4 = new ImageIcon("src/res/button_join.png");
		button_join = new JButton(i4);
		button_join.setContentAreaFilled(false);
		button_join.setFocusPainted(false);
		button_join.setBorderPainted(false);
		button_join.setMargin(new Insets(0,0,0,0));
		button_join.setRolloverIcon(new ImageIcon("src/res/button_join_hover.png"));
		button_join.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				join();
			}
		});
		
		ImageIcon i2 = new ImageIcon("src/res/button_credits.png");
		button_credits = new JButton(i2);
		button_credits.setContentAreaFilled(false);
		button_credits.setFocusPainted(false);
		button_credits.setBorderPainted(false);
		button_credits.setMargin(new Insets(0,0,0,0));
		button_credits.setRolloverIcon(new ImageIcon("src/res/button_credits_hover.png"));
		button_credits.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				credits();
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
		box.add(Box.createVerticalStrut(320));
		box.add(button_join);
		box.add(Box.createVerticalStrut(10));
		box.add(button_host);
		box.add(Box.createVerticalStrut(10));
		box.add(button_credits);
		box.add(Box.createVerticalStrut(10));
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
        g.drawImage(img, WINDOW_WIDTH/2-img.getWidth(null)/2, 10, null);
       
	    Toolkit.getDefaultToolkit().sync();

        
        
	}
	private void createGame()
	{

		Main.gameScene=new GameScene();
		//Main.gameScene.gameServer=new GameServer(Main.gameScene);
		Main.gameServer=new GameServer(Main.gameScene);
		Main.gameScene.gameServer=Main.gameServer;
		Main.gameScene.gameServer.start();

		Main.gameScene.addModel(new Player());
		Main.gameScene.addModel(new Player());
		Main.gameScene.makeBars();
		((Player)Main.gameScene.models.get(0)).setPosition(WINDOW_WIDTH*1/4, ARENA_HEIGHT/2);
		((Player)Main.gameScene.models.get(1)).setPosition(WINDOW_WIDTH*3/4, ARENA_HEIGHT/2);

		
		LocalGameScene tmp = new LocalGameScene();
		SwingView v = new SwingView();
		v.setGameScene(tmp);
		//v.gameClient=new GameClient(v.localGameScene,"localhost");
		Main.gameClient=new GameClient(v.localGameScene, "localhost");
		v.gameClient=Main.gameClient;
		Main.gameClient.start();
		v.localGameScene.makeBars();

		
		BarView bv = new BarView();
		bv.setLocalGameScene(v.localGameScene);
		
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
		
		
		//Main.gameScene.startGame();
		v.startGame();
		
	}
	
	private void credits()
	{
		CreditsView v = new CreditsView();
		Main.frame.getContentPane().remove(this);
		Main.frame.getContentPane().add(v);
		v.requestFocus();
		v.repaint();
		v.revalidate();
	}
	
	private void join()
	{
		String ip = JOptionPane.showInputDialog(Main.frame, "Enter the host's IP", "", JOptionPane.PLAIN_MESSAGE);
		
		Main.gameServer=null;
		
		LocalGameScene tmp = new LocalGameScene();
		SwingView v = new SwingView();
		v.setGameScene(tmp);
		//v.gameClient=new GameClient(v.localGameScene,ip);
		Main.gameClient=new GameClient(v.localGameScene, ip);
		v.gameClient=Main.gameClient;
		Main.gameClient.start();
		v.localGameScene.makeBars();

		
		BarView bv = new BarView();
		bv.setLocalGameScene(v.localGameScene);
		
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
