package engine;

public class Collider {
	
	private int x, y, r;
	private double dist, a, b;

	public Collider(int xx, int yy, int rr)
	{
		x=xx;
		y=yy;
		r=rr;
	}
	
	public boolean collides(Collider otherCollider)
	{
		dist = Math.sqrt(Math.pow((x+r)-(otherCollider.x+otherCollider.r), 2)+Math.pow((y+r)-(otherCollider.y+otherCollider.r), 2));
		if (dist<= Math.abs(r+otherCollider.r))// || dist >=Math.abs(r-otherCollider.r))
			return true;
		return false;
	}
	
	public boolean containsPoint(int xx, int yy)
	{
		a=Math.pow((xx-(x+r)), 2.0);
		b=Math.pow((yy-(y+r)), 2.0);
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



