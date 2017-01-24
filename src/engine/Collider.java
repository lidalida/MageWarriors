package engine;

public class Collider {
	
	private int x, y, radius;

	public Collider(int xx, int yy, int rr)
	{
		x=xx;
		y=yy;
		radius=rr;
	}
	
	public boolean collides(Collider otherCollider)
	{
		double dist = Math.sqrt(Math.pow((x+radius)-(otherCollider.x+otherCollider.radius), 2)+Math.pow((y+radius)-(otherCollider.y+otherCollider.radius), 2));
		if (dist<= Math.abs(radius+otherCollider.radius))
			return true;
		return false;
	}
	
	public boolean containsPoint(int xx, int yy)
	{
		double a=Math.pow((xx-(x+radius)), 2.0);
		double b=Math.pow((yy-(y+radius)), 2.0);
		if(a+b<=Math.pow(radius, 2.0))
			return true;
		return false;
			
	}
	
	public void update(int xx, int yy)
	{
		x=xx;
		y=yy;
	}
}



