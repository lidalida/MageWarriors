package model;

public abstract class Scene {
	private boolean[] flags = new boolean[4];
	public void setFlag(int i, boolean value)
	{
		flags[i]=value;
	}

}
