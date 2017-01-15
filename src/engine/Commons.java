package engine;

import java.awt.Color;

public interface Commons {
	public static final double DEG_TO_RAD = 0.01745;
	
	public static final int MOVE_DELTA = 5;
	public static final int MISSILE_MOVE_DELTA=9;
	public static final int DELAY = 10;
	public static final int WINDOW_HEIGHT = 600;
	public static final int WINDOW_WIDTH = 800;
	public static final int SCOREBOARD_HEIGHT = 100;
	public static final int ARENA_HEIGHT = 500;
	public static final int FRAMETIME = 18;
	public static final int MISSILE_LIFETIME = 100;
	public static final int PLAYER_HEALTH = 100;
	public static final int PLAYER_MANA = 100;
	
	// Count of flags IMPORTANT
	public static final int FLAG_COUNT = 10;
	//player flags
	public static final int IS_MOVING = 0;
	public static final int IS_MOVING_BACK = 1;
	public static final int IS_ROTATING = 2;
	public static final int IS_CASTING_SPELL_1 = 3;
	public static final int IS_CASTING_SPELL_2 = 4;
	public static final int IS_CASTING_SPELL_3 = 5;
	public static final int IS_SPELL_CRAFTED = 6;
	public static final int IS_CASTING_SUPER_SPELL = 7;
	public static final int IS_MOVING_RIGHT = 8;
	public static final int IS_MOVING_LEFT = 9;


	//player attacks
	public static final int SPELL1_COST = 10;
	public static final int SPELL2_COST = 30;
	public static final int SPELL3_COST = 80;
	public static final int DAMAGE = 3;

	
	//types of items & super spells
	public static final int MANA = 0;
	public static final int HP = 1;	
	public static final int SPEEDUP = 2;
	public static final int TELEPORT = 3;
	public static final int FREEZE = 4;

	
	// GUI
	public static final int HEALTH_BAR = 0;
	public static final int MANA_BAR = 1;
	
	// NETWORK
	// EventMsg
	public static final int LOGIN = 0;
	public static final int ADD_ITEM = 1;
	public static final int ADD_MISSILE = 2;
	public static final int DELETE_OBJECT = 3;
	public static final int CHANGE_HP = 4;
	public static final int CHANGE_MP = 5;
	public static final int CHANGE_IMG = 6;
	public static final int GAME_OVER = 7;
	
	public static final int REGULAR_IMG = 0;
	public static final int FROZEN_IMG = 1;

	
	public static final int MAX_PLAYERS = 2;
}
