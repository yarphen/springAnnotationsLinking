package com.sheremet.spring.AnnotationsLinking;

public class MouseCatPositionComparator implements MouseCatComparator{
	private int mode;
	public MouseCatPositionComparator() {}
	public int compare(MouseCatPosition o1, MouseCatPosition o2) {
		int d1 =Utils. distance(o1);
		int d2 = Utils.distance(o2);
		int diag1 = Utils.diagonality(o1);
		int diag2 = Utils.diagonality(o2);
		boolean isEven1 = d1%2==0;
		boolean isEven2 = d2%2==0;
		if (isEven1&&!isEven2)
			return 1;
		else if (!isEven1&&isEven2)
			return -1;
		if (isEven1&&isEven2){
			if (d1<d2)return 1;
			if (d1>d2)return -1;
			return -Integer.compare(diag1, diag2);
		}else{
			boolean lose1 = d1==1;
			boolean lose2 = d2==1;
			if (!lose1&&lose2)
				return 1;
			else if(lose1&&!lose2)
				return -1;
			if (lose1&&lose2)return 0;
			boolean shadowAttack1 = isShadowAttack(o1);
			boolean shadowAttack2 = isShadowAttack(o2);
			if (shadowAttack1&&!shadowAttack2)
				return 1;
			else if (!shadowAttack1&&shadowAttack2){
				return -1;
			}
			if (diag1>diag2)return 1;
			if (diag1<diag2)return -1;
			return -Integer.compare(d1, d2);
		}
	}
	private boolean isShadowAttack(MouseCatPosition o1) {
		Position myNewPosition, myOldPosition, hisNewPosition, hisOldPosition;
		Player me, he;
		if (mode==MouseCatGame.CAT){
			me = o1.getCat();
			he = o1.getMouse();
		}else{
			he = o1.getCat();
			me = o1.getMouse();
		}
		myNewPosition = me.getPlayerPosition();
		myOldPosition = me.getShadowPosition();
		hisNewPosition = he.getPlayerPosition();
		hisOldPosition = he.getShadowPosition();
		int shadowDistBefore = Utils.distance(myOldPosition, hisNewPosition);
		int shadowDiagBefore = Utils.diagonality(myOldPosition, hisNewPosition);
		int shadowDistAfter = Utils.distance(myNewPosition, hisOldPosition);
		int shadowDiagAfter = Utils.diagonality(myNewPosition, hisOldPosition);
		if (shadowDiagAfter==shadowDiagBefore&&shadowDistAfter==shadowDistBefore)return true;
		else return false;
	}
	public void setMode(int mode) {
		switch (mode) {
		case MouseCatGame.CAT:case MouseCatGame.MOUSE:
			break;
		default:
			throw new IllegalArgumentException();
		}
		this.mode=mode;
	}
	public int getMode() {
		return mode;
	}
}
