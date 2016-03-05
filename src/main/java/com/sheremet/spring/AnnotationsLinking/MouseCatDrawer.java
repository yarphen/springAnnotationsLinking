package com.sheremet.spring.AnnotationsLinking;

public interface MouseCatDrawer {
	void start();
	void drawBoard();
	void drawMouse();
	void drawCat();
	void turn(int x,int y);
	void setCellSize(double size);
}
