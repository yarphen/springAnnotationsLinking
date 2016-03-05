package com.sheremet.spring.AnnotationsLinking;

public interface MouseCatGame {
	public static final int DEFAULT_SIZE = 9;
	public static final int  UNITIALIZED = 0;
	public static final int  CAT = 1;
	public static final int  MOUSE = 2;
	public static final int  WINNER_CAT = 3;
	public static final int  WINNER_MOUSE = 4;
	public static final int  TWOUSERS_MODE = 5;
	public static final int  CAT_MODE = 6;
	public static final int  MOUSE_MODE = 7;
	public static final int  AUTO_MODE = 8;
	void initGame(int n, int m, int mode);
	int getXForCat();
	int getYForCat();
	int getXForMouse();
	int getYForMouse();
	int getXForCatShadow();
	int getYForCatShadow();
	int getXForMouseShadow();
	int getYForMouseShadow();
	void turn();
	int gameStatus();
	void setMode(int m);
	boolean allow(Position p);
	int getMaxX();
	int getMaxY();
	void reset();
	boolean isAutoTurn();
	MouseCatPosition getPosition();
	boolean turn(Position even);
	int getMode();
}
