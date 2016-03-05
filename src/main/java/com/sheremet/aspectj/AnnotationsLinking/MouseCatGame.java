package com.sheremet.aspectj.AnnotationsLinking;

public interface MouseCatGame {
	public static final int  UNITIALIZED = 0;
	public static final int  CAT = 1;
	public static final int  MOUSE = 2;
	public static final int  WINNER_CAT = 3;
	public static final int  WINNER_MOUSE = 4;
	void initGame(int n, int m);
	int getXForCat();
	int getYForCat();
	int getXForMouse();
	int getYForMouse();
	boolean turn(int x, int y);
	int gameStatus();
	boolean allow(int x, int y);
	int getMaxX();
	int getMaxY();
	void reset();
}
