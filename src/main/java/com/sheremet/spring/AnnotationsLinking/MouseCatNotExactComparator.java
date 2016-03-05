package com.sheremet.spring.AnnotationsLinking;

import java.util.Comparator;
import java.util.Random;


public class MouseCatNotExactComparator implements MouseCatComparator {
	private final MouseCatPositionComparator c;
	public static final int MAXLEVEL = 10;
	private final Random r = new Random();
	private final int level;
	public MouseCatNotExactComparator(int level) {
		c = new MouseCatPositionComparator();
		this.level=level;
	}
	public int compare(MouseCatPosition o1, MouseCatPosition o2) {
		if (c.getMode()==0)throw new RuntimeException("Mode does not initialized!");
		if (r.nextDouble()>(double)level/MAXLEVEL){
			return r.nextInt(2)-1;
		}else{
			return c.compare(o1, o2);
		}
	}
	public void setMode(int mode) {
		c.setMode(mode);
	}
	public int getMode() {
		return c.getMode();
	}
}
