package engine;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Bar implements Drawable, Commons {
	
	private int x;
	private int y;
	private int type;
	private List<BarElement> bar = new ArrayList<BarElement>();
	private Player player;
	
	public Bar(int pos_x, int pos_y, int img, int count, Player owner){
		x = pos_x;
		y = pos_y;
		type = img;
		for(int i=0;i<count;i++)
			bar.add(new BarElement(x+i,y,type));
		player = owner;
	}
	
	@Override
	public void draw(Graphics g1) {
		int i=0;
		
		int points;
		if(type==0)
			points = player.getHP();
		else
			points = player.getMP();
		
		for(BarElement it : bar){
			if(i>=points)
				break;
			it.draw(g1);
			i++;
		}
	}

}
