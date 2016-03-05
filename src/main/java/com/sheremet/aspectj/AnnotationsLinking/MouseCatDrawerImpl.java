package com.sheremet.aspectj.AnnotationsLinking;

import java.awt.Color;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

public class MouseCatDrawerImpl extends GraphicsProgram implements MouseCatDrawer  {
	private static final int MAXPOSS = 8;
	private GOval mouse;
	private GOval cat;
	private double size;
	private int x, y;
	private GRect[]possibilities = new GRect[8];
	private MouseCatGame game;
	public MouseCatDrawerImpl(MouseCatGame game, double size) {
		this.game = game;
		this.size = size;
		this.x = game.getMaxX();
		this.y = game.getMaxY();
	}
	public void drawBoard() {
		GRect temp;
		for(int i=0; i<x; i++){
			for(int j=0; j<y; j++){
				temp = new GRect(size*i, size*j, size, size);
				if ((i+j)%2==1){
					temp.setFilled(true);
					temp.setFillColor(Color.LIGHT_GRAY);
				}
				add(temp);
			}
		}
		return;
	}

	public void drawMouse() {
		if (mouse==null){
			mouse = new GOval(size, size);
			mouse.setFilled(true);
			mouse.setFillColor(Color.GREEN);
			add(mouse);
		}
	}

	public void drawCat() {
		if (cat==null){
			cat = new GOval(size, size);
			cat.setFilled(true);
			cat.setFillColor(Color.RED);
			add(cat);
		}
	}

	public void turn(int x, int y) {
		if (game.turn(x, y)){
			switch (game.gameStatus()) {
			case MouseCatGame.CAT:

				drawPossibilities(game.getXForCat(),game.getYForCat());
				break;
			case MouseCatGame.MOUSE:

				drawPossibilities(game.getXForMouse(),game.getYForMouse());
				break;
			default:
				break;
			}
			redraw();
		}
		switch (game.gameStatus()) {
		case MouseCatGame.WINNER_CAT:
			removeAll();
			JOptionPane.showMessageDialog(this, "Cat ate the mouse!");
			game.reset();
			init();
			redraw();
			break;
		case MouseCatGame.WINNER_MOUSE:
			removeAll();
			JOptionPane.showMessageDialog(this, "Mouse beat the cat!");
			game.reset();
			init();
			redraw();
			break;
		}
	}
	private void redraw() {
		mouse.setLocation(size*game.getXForMouse(), size*game.getYForMouse());
		cat.setLocation(size*game.getXForCat(), size*game.getYForCat());
		mouse.sendToFront();
		cat.sendToFront();
	}
	private void drawPossibilities(int x, int y){
		int c = 0;
		for(int i=-1; i<=1; i++){
			for(int j=-1; j<=1; j++){
				int i1=i, j1=j;
				if (i1==0&&j1==0)
					continue;
				if (i1*j1==0){
					i1*=2;
					j1*=2;
				}
				if (game.allow(i1+x, j1+y)){
					if (possibilities[c]==null){
						possibilities[c] = new GRect(size, size);
						possibilities[c].setFilled(true);
						possibilities[c].setFillColor(Color.GRAY);
						add(possibilities[c]);
					}
					possibilities[c].setLocation(size*(i1+x), size*(j1+y));
					possibilities[c].setVisible(true);
					c++;
				}
			}
		}
		for(;c<MAXPOSS; c++){
			if (possibilities[c]!=null)
				possibilities[c].setVisible(false);
		}
	}
	@Override
	public void init() {
		drawBoard();
		drawCat();
		drawMouse();
		redraw();
		drawPossibilities(1, 0);
		addMouseListeners();
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		turn((int)(arg0.getX()/size), (int)(arg0.getY()/size));
	}
}
