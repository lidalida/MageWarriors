package model;

public interface Commons {
	public static final int MOVE_DELTA = 5;
	public static final int MISSILE_MOVE_DELTA=8;
	public static final int DELAY = 10;
	public static final int WINDOW_HEIGHT = 600;
	public static final int WINDOW_WIDTH = 800;
	public static final int FRAMETIME = 18;
	public static final int MISSILE_LIFETIME = 100;
	
	//player flags
	public static final int IS_MOVING = 0;
	public static final int IS_MOVING_BACK = 1;
	public static final int IS_SHOOTING = 2;
	public static final int IS_ROTATING = 3;

	

}
