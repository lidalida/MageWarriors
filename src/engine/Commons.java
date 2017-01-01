package engine;

public interface Commons {
	public static final double DEG_TO_RAD = 0.01745;
	
	public static final int MOVE_DELTA = 5;
	public static final int MISSILE_MOVE_DELTA=8;
	public static final int DELAY = 10;
	public static final int WINDOW_HEIGHT = 600;
	public static final int WINDOW_WIDTH = 800;
	public static final int FRAMETIME = 18;
	public static final int MISSILE_LIFETIME = 100;
	public static final int PLAYER_HEALTH = 100;
	public static final int PLAYER_MANA = 100;
	
	//player flags
	public static final int IS_MOVING = 0;
	public static final int IS_MOVING_BACK = 1;
	public static final int IS_ROTATING = 2;
	public static final int IS_CASTING_SPELL_1 = 3;
	public static final int IS_CASTING_SPELL_2 = 4;
	public static final int IS_CASTING_SPELL_3 = 5;
	public static final int IS_SPELL_CRAFTED = 6;
	public static final int TMP_MANA_CHARGER = 7;

	//player attacks
	public static final int SPELL1_COST = 10;
	public static final int SPELL2_COST = 30;
	public static final int SPELL3_COST = 80;
	
}
