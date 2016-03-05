package com.sheremet.spring.AnnotationsLinking;

import java.awt.Color;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

@Component("drawer")
public class MouseCatDrawerImpl extends GraphicsProgram implements MouseCatDrawer  {
	private static final int MAXPOSS = 8;
	private static final double DEFAULT_CELL_SIZE = 50;
	public static final double APPLICATION_WIDTH = DEFAULT_CELL_SIZE*(MouseCatGame.DEFAULT_SIZE+1);
	public static final double APPLICATION_HEIGHT =DEFAULT_CELL_SIZE*(MouseCatGame.DEFAULT_SIZE+2);
	private GOval mouse;
	private GOval cat;
	private double cellSize = DEFAULT_CELL_SIZE;
	private int x, y;
	private GRect[]possibilities = new GRect[MAXPOSS];
	private MouseCatGame game;
	@Autowired
	public MouseCatDrawerImpl(MouseCatGame game) {
		this.game = game;
		this.x = game.getMaxX();
		this.y = game.getMaxY();
	}
	public void setCellSize(double size) {
		this.cellSize = size;
	}
	public void drawBoard() {
		GRect temp;
		for(int i=0; i<x; i++){
			for(int j=0; j<y; j++){
				temp = new GRect(cellSize*i, cellSize*j, cellSize, cellSize);
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
			mouse = new GOval(cellSize, cellSize);
			mouse.setFilled(true);
			mouse.setFillColor(Color.GREEN);
			add(mouse);
		}
	}

	public void drawCat() {
		if (cat==null){
			cat = new GOval(cellSize, cellSize);
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
			showAll(false);
			JOptionPane.showMessageDialog(this, "Cat ate the mouse!");
			reset();
			break;
		case MouseCatGame.WINNER_MOUSE:
			showAll(false);
			JOptionPane.showMessageDialog(this, "Mouse beat the cat!");
			reset();
			break;
		}
	}
	private void redraw() {
		mouse.setLocation(cellSize*game.getXForMouse(), cellSize*game.getYForMouse());
		cat.setLocation(cellSize*game.getXForCat(), cellSize*game.getYForCat());
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
						possibilities[c] = new GRect(cellSize, cellSize);
						possibilities[c].setFilled(true);
						possibilities[c].setFillColor(Color.GRAY);
						add(possibilities[c]);
					}
					possibilities[c].setLocation(cellSize*(i1+x), cellSize*(j1+y));
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
	private void reset(){
		game.reset();
		redraw();
		showAll(true);
		drawPossibilities(1, 0);
	}
	private void showAll(boolean b) {
		cat.setVisible(b);
		mouse.setVisible(b);
		for(GRect r : possibilities){
			if (r!=null){
				r.setVisible(b);
			}
		}
	}
	@Override
	public void run() {
		setSize((int)(APPLICATION_WIDTH), (int)(APPLICATION_HEIGHT));
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		turn((int)(arg0.getX()/cellSize), (int)(arg0.getY()/cellSize));
	}
}
