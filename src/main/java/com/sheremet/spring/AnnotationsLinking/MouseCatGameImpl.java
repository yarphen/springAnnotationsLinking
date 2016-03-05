package com.sheremet.spring.AnnotationsLinking;

import org.springframework.beans.factory.annotation.Autowired;

public class MouseCatGameImpl implements MouseCatGame{
	private int maxx, maxy;
	private MouseCatPosition position;
	private int status=0;
	private int turns;
	private int mode;
	@Autowired
	private MouseCatSolver solver;
	public MouseCatGameImpl() {
		this(DEFAULT_SIZE, DEFAULT_SIZE, TWOUSERS_MODE);
	}
	public MouseCatGameImpl(int m, int n, int mode) {
		initGame(m, n, mode);
	}
	public void initGame(int m, int n, int mode) {
		this.mode = mode;
		turns = 0;
		maxx = m;
		maxy = n;
		if (maxx<3||maxy<3)throw new IllegalArgumentException();
		position=new MouseCatPosition(new Player(new Position((maxx-2)/2*2+(maxy%2), maxy-1),  new Position(-1, -1)), new Player(new Position(1, 0), new Position(-1, -1)));
		status=CAT;
	}

	public int getXForCat() {
		return position.getCat().getPlayerPosition().getX();
	}
	public int getYForCat() {
		return position.getCat().getPlayerPosition().getY();
	}

	public int getXForMouse() {
		return position.getMouse().getPlayerPosition().getX();
	}

	public int getYForMouse() {
		return position.getMouse().getPlayerPosition().getY();
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
	public boolean turn(Position p) {
		if (allow(p)){
			boolean isCat = turns%2==0;
			System.out.println("Turn: "+(isCat?"Cat":"Mouse")+" "+p.getX()+", "+p.getY());
			if (isCat){
				position.getCat().setShadowPosition(position.getCat().getPlayerPosition());
				position.getCat().setPlayerPosition(p);
			}	else  {
				position.getMouse().setShadowPosition(position.getMouse().getPlayerPosition());
				position.getMouse().setPlayerPosition(p);
			}
			if (position.getCat().getPlayerPosition().equals(position.getMouse().getPlayerPosition())){
				status+=2;
			}else{
				status = (1-turns%2)+1;
			}
			turns++;
			return true;
		}else
			return false;
	}
	public void turn() {
		solver.solveStep(this);
	}
	public int gameStatus() {
		return status;
	}
	public int getXForCatShadow() {
		return position.getCat().getShadowPosition().getX();
	}
	public int getYForCatShadow() {
		return position.getCat().getShadowPosition().getY();
	}
	public int getXForMouseShadow() {
		return position.getMouse().getShadowPosition().getX();
	}
	public int getYForMouseShadow() {
		return position.getMouse().getShadowPosition().getY();
	}
	public int getMaxX() {
		return maxx;
	}
	public int getMaxY() {
		return maxy;
	}
	public boolean allow(Position pos) {
		Player p = null;
		if (turns%2==0){
			p = position.getCat();
		}else{
			p = position.getMouse();
		}
		Position r = pos.sub(p.getPlayerPosition());
		if (Math.abs(r.getX())+Math.abs(r.getY())!=2)return false;
		if (pos.getX()<0||pos.getX()>=maxx)return false;
		if (pos.getY()<0||pos.getY()>=maxy)return false;
		if (pos.equals(p.getShadowPosition()))return false;
		return true;
	}
	public void reset() {
		initGame(maxx, maxy, mode);
	}
	public boolean isAutoTurn(){
		return (status==CAT||status==MOUSE)&&(mode==AUTO_MODE||mode==(turns%2+6));
	}
	public MouseCatPosition getPosition() {
		return position;
	}
	public int getMode() {
		return mode;
	}
}
