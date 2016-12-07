package engine;

public class Collider {
	
	private int x, y, r;

	public Collider(int xx, int yy, int rr)
	{
		x=xx;
		y=yy;
		r=rr;
	}
	
	public boolean collides(Collider otherCollider)
	{
		double dist = Math.sqrt(Math.pow(x-otherCollider.x, 2)+Math.pow(y-otherCollider.y, 2));
		if (dist<= Math.abs(r+otherCollider.r) || dist >=Math.abs(r-otherCollider.r))
			return true;
		return false;
	}
	
	public boolean containsPoint(int xx, int yy)
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



