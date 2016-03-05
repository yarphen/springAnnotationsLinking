package com.sheremet.spring.AnnotationsLinking;

public class Utils {
	public static int distance(Position p1, Position p2) {
		int dx = Math.abs(p1.getX()-p2.getX());
		int dy = Math.abs(p1.getY()-p2.getY());
		int maxd = Math.max(dx, dy);
		int mind = Math.min(dx, dy);
		return mind+(maxd-mind)/2;
	}
	public static int diagonality(Position p1, Position p2) {
		int dx = Math.abs(p1.getX()-p2.getX());
		int dy = Math.abs(p1.getY()-p2.getY());
		return Math.abs(dy-dx);
	}

	public static int distance(MouseCatPosition p) {
		Position p1 = p.getCat().getPlayerPosition();
		Position p2 = p.getMouse().getPlayerPosition();
		return distance(p1,p2);
	}
	public static int diagonality(MouseCatPosition p) {
		Position p1 = p.getCat().getPlayerPosition();
		Position p2 = p.getMouse().getPlayerPosition();
		return diagonality(p1,p2);
	}
}
