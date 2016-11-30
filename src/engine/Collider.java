package engine;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class Collider {
	
	private int x, y, r;
	private Ellipse2D ellipse;

	public Collider(int xx, int yy, int rr)
	{
		x=xx;
		y=yy;
		r=rr;
		//ellipse=new Ellipse2D.Double(x, y, r, r);
	}
	public boolean checkCollision(Collider otherCollider)
	{
		double dist = Math.sqrt(Math.pow(x-otherCollider.x, 2)+Math.pow(y-otherCollider.y, 2));
		if (dist<= Math.abs(r+otherCollider.r) || dist >=Math.abs(r-otherCollider.r))
			return true;
		return false;
	}
	
	public boolean checkMouseIn(int xx, int yy)
	{
		double a=Math.pow((xx-x), 2.0);
		double b=Math.pow((yy-y), 2.0);
		if(a+b<=Math.pow(r, 2.0))
			return true;
		return false;
			
	}
	
	public void update(int xx, int yy)
	{
		x=xx;
		y=yy;
	}
}



