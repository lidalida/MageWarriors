package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import engine.Commons;
import main.Main;

public class StartView extends JPanel implements Commons {
	
	private static final long serialVersionUID = 6342838906053390199L;
	private JButton button;
	
	public StartView()
	{
		button = new JButton("Start");

		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				createGame();
			}
		});
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		this.add(button);
		setFocusable(true);
	}
	
	public void createGame()
	{
		SwingView v = new SwingView();
		BarView bv = new BarView();
		v.setGameScene(Main.gameScene);
		bv.setGameScene(Main.gameScene);
		Main.frame.getContentPane().remove(this);
		Main.frame.getContentPane().add(bv);
		Main.frame.getContentPane().add(v);
		Main.frame.setVisible(true);
		v.requestFocus();
		v.repaint();
		bv.repaint();
		v.startGame();
				
	}

}
