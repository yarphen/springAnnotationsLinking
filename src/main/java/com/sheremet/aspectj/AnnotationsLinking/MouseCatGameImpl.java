package com.sheremet.aspectj.AnnotationsLinking;

public class MouseCatGameImpl implements MouseCatGame{
	private int maxx, maxy;
	private int xCat=1, yCat=0, xMouse, yMouse;
	private int xCatShadow=-1, yCatShadow=-1, xMouseShadow=-1, yMouseShadow=-1;
	private int status=0;
	private int turns;
	public MouseCatGameImpl() {
		initGame(DEFAULT_SIZE, DEFAULT_SIZE);
	}
	public MouseCatGameImpl(int m, int n) {
		initGame(m, n);
	}
	public void initGame(int m, int n) {
		turns = 0;
		xCat=1;
		yCat=0;
		xCatShadow=-1;
		yCatShadow=-1;
		xMouseShadow=-1; 
		yMouseShadow=-1;
		maxx = m;
		maxy = n;
		if (maxx<3||maxy<3)throw new IllegalArgumentException();
		yMouse = maxy-1;
		xMouse = (maxx-2)/2*2+1;
		status=1;
	}
	
	public int getXForCat() {
		return xCat;
	}
	public int getYForCat() {
		return yCat;
	}

	public int getXForMouse() {
		return xMouse;
	}

	public int getYForMouse() {
		return yMouse;
	}

	public boolean turn(int x, int y) {
		if (allow(x, y)){
			if (turns%2==0){
				xCatShadow=xCat;
				yCatShadow=yCat;
				xCat=x;
				yCat=y;
			}	else  {
				xMouseShadow=xMouse;
				yMouseShadow=yMouse;
				xMouse=x;
				yMouse=y;
			}
			if (xCat==xMouse&&yCat==yMouse){
				status+=2;
			}else{
				status = (1-turns%2)+1;
			}
			turns++;
			return true;
		}else
			return false;
	}

	public int gameStatus() {
		return status;
	}

	public int getXForCatShadow() {
		return xCatShadow;
	}

	public int getYForCatShadow() {
		return yCatShadow;
	}

	public int getXForMouseShadow() {
		return xMouseShadow;
	}
	public int getYForMouseShadow() {
		return yMouseShadow;
	}
	public int getMaxX() {
		return maxx;
	}
	public int getMaxY() {
		return maxy;
	}

	public boolean allow(int x, int y) {
		int playerX, playerY, shadowX, shadowY;
		if (turns%2==0){
			playerX = xCat;
			playerY = yCat;
			shadowX = xCatShadow;
			shadowY = yCatShadow;
		}else{
			playerX = xMouse;
			playerY = yMouse;
			shadowX = xMouseShadow;
			shadowY = yMouseShadow;
		}
		int x1=x-playerX;
		int y1=y-playerY;
		if (Math.abs(x1)+Math.abs(y1)!=2)return false;
		if (x<0||x>=maxx)return false;
		if (y<0||y>=maxy)return false;
		if (x==shadowX&&y==shadowY)return false;
		return true;
	}
	public void reset() {
		initGame(maxx, maxy);
	}

}
