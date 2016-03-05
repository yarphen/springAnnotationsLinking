package com.sheremet.spring.AnnotationsLinking;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
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
	public final double APPLICATION_WIDTH;
	public final double APPLICATION_HEIGHT;
	private GOval mouse;
	private GOval cat;
	private double cellSize = DEFAULT_CELL_SIZE;
	private int x, y;
	private GRect[]possibilities = new GRect[MAXPOSS];
	private MouseCatGame game;
	private boolean paused=false;
	private boolean autoTurn=true;
	private boolean waiting=false;
	private boolean interrupting=false;
	@Autowired
	public MouseCatDrawerImpl(MouseCatGame game) {
		this.game = game;
		this.x = game.getMaxX();
		this.y = game.getMaxY();
		APPLICATION_WIDTH = DEFAULT_CELL_SIZE*(x+1);
		APPLICATION_HEIGHT = DEFAULT_CELL_SIZE*(y+2);
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
	public void turn(){
		new Thread(new Runnable() {

			public void run() {
				waiting=true;
				do{
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (interrupting){
						waiting=false;
						interrupting=false;
						return;
					}
				}while(paused);
				waiting=false;
				game.turn();
				redraw();
				checkWinner();
				if (autoTurn&&game.isAutoTurn())
					turn();
			}
		}).start();
	}
	public void turn(int x, int y) {
		if (game.turn(new Position(x, y))){
			redraw();
		}
		checkWinner();
		if (game.isAutoTurn())
			turn();
	}
	private void checkWinner() {
		switch (game.gameStatus()) {
		case MouseCatGame.WINNER_CAT:
			interrupting=true;
			showAll(false);
			JOptionPane.showMessageDialog(this, "Cat ate the mouse!");
			reset();
			break;
		case MouseCatGame.WINNER_MOUSE:
			interrupting=true;
			showAll(false);
			JOptionPane.showMessageDialog(this, "Mouse beat the cat!");
			reset();
			break;
		}
	}
	private void redraw() {
		switch (game.gameStatus()) {
		case MouseCatGame.CAT:

			drawPossibilities(game.getPosition().getCat().getPlayerPosition());
			break;
		case MouseCatGame.MOUSE:

			drawPossibilities(game.getPosition().getMouse().getPlayerPosition());
			break;
		default:
			break;
		}
		mouse.setLocation(cellSize*game.getXForMouse(), cellSize*game.getYForMouse());
		cat.setLocation(cellSize*game.getXForCat(), cellSize*game.getYForCat());
		mouse.sendToFront();
		cat.sendToFront();
	}
	private void drawPossibilities(Position p){
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
				if (game.allow(p.add(i1,j1))){
					if (possibilities[c]==null){
						possibilities[c] = new GRect(cellSize, cellSize);
						possibilities[c].setFilled(true);
						possibilities[c].setFillColor(Color.GRAY);
						add(possibilities[c]);
					}
					possibilities[c].setLocation(cellSize*(i1+p.getX()), cellSize*(j1+p.getY()));
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
	public void run() {
		resize((int)(APPLICATION_WIDTH), (int)(APPLICATION_HEIGHT));
		drawBoard();
		drawCat();
		drawMouse();
		redraw();
		drawPossibilities(game.getPosition().getCat().getPlayerPosition());
		addMouseListeners();
		addKeyListeners();
	}
	private void reset(){
		game.reset();
		redraw();
		showAll(true);
		drawPossibilities(game.getPosition().getCat().getPlayerPosition());
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
	public void mouseClicked(MouseEvent arg0) {
		if (!waiting)
			if (game.isAutoTurn())
				turn();
			else
				turn((int)(arg0.getX()/cellSize), (int)(arg0.getY()/cellSize));
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.isShiftDown()){
			if (game.getMode()<MouseCatGame.AUTO_MODE)
				game.setMode(game.getMode()+1);
			else
				game.setMode(MouseCatGame.TWOUSERS_MODE);
			System.out.println("Mode set to " + game.getMode());
		}
		else 
			if (arg0.isControlDown()){
				autoTurn=!autoTurn;
				System.out.println("Autoturn set to " + autoTurn);
			}else{
				paused=!paused;
				System.out.println(paused?"Paused":"Resumed");
			}

	}
}
