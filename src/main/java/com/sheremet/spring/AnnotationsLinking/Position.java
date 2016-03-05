package com.sheremet.spring.AnnotationsLinking;

public class Position {
	private int x,y;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	public Position(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	public Position add(int i, int j){
		return new Position(x+i, y+j);
	}
	public Position add(Position another){
		return new Position(x+another.x, y+another.y);
	}
	public Position sub(int i, int j){
		return new Position(x-i, y-j);
	}
	public Position sub(Position another){
		return new Position(x-another.x,y-another.y);
	}
	public Position abs(){
		return new Position(Math.abs(x),Math.abs(y));
	}
}
