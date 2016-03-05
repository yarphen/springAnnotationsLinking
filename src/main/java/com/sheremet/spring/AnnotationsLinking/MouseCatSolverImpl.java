package com.sheremet.spring.AnnotationsLinking;

import java.awt.Color;
import java.util.Comparator;

import org.springframework.stereotype.Component;

@Component("solver")
public class MouseCatSolverImpl implements MouseCatSolver{
	public synchronized void solveStep(MouseCatGame game) {
		Position thisPlayer;
		Player otherPlayer;
		int mineven, optimalodd, diagonality; 
		MouseCatPosition position = null;
		Comparator<MouseCatPosition> comp ;
		int dist;
		boolean isCat;
		switch (game.gameStatus()){
		case MouseCatGame.CAT:
			thisPlayer = game.getPosition().getCat().getPlayerPosition();
			otherPlayer = game.getPosition().getMouse();
			comp =new MouseCatPostionComparator(MouseCatGame.CAT);
			isCat = true;
			break;
		case MouseCatGame.MOUSE:
			thisPlayer = game.getPosition().getMouse().getPlayerPosition();
			otherPlayer = game.getPosition().getCat();
			comp =new MouseCatPostionComparator(MouseCatGame.MOUSE);
			isCat = false;
			break;
		default: 
			return;
		}
		dist = Utils.distance(thisPlayer, otherPlayer.getPlayerPosition());
		for(int i=-1; i<=1; i++){
			for(int j=-1; j<=1; j++){
				int i1=i, j1=j;
				if (i1==0&&j1==0)
					continue;
				if (i1*j1==0){
					i1*=2;
					j1*=2;
				}
				Position moved = thisPlayer.add(i1,j1);
				if (game.allow(moved)){
					MouseCatPosition temp = new MouseCatPosition(isCat?game.getPosition().getMouse():new Player(moved, thisPlayer), isCat?new Player(moved, thisPlayer):game.getPosition().getCat());
					if (position==null){
						position = temp;
					}else{
						if (comp.compare(temp, position)>0)
							position = temp;
					}
//					add(i1,j1, otherPlayer.getPlayerPosition());
//					if (dist%2==0){
//						int nextdist = distance(thisPlayer.add(i1, j1), otherPlayer.getPlayerPosition());
//						if (nextdist!=1)
//							add(i1,j1,otherPlayer.getShadowPosition());
//					}
				}
			}
		}
		game.turn(isCat?position.getCat().getPlayerPosition():position.getMouse().getPlayerPosition());
	}
	/*private void add(int i1, int j1, Position p) {
		int d =distance(thisPlayer, p);
		if (d%2==0){
			int dx = Math.abs(i1+thisPlayer.getX()-p.getX());
			int dy = Math.abs(j1+thisPlayer.getY()-p.getY());
			int diagon=Math.abs(dy-dx);
			if ((d<mineven||(d==mineven&&(diagonality>diagon||diagonality==-1)))||even==null){
				mineven=d;
				diagonality=diagon;
				even = thisPlayer.add(i1,j1);
			}
		}else{
			if (odd==null||(optimalodd==1||(d!=1&&d<optimalodd))){
				optimalodd=d;
				odd = thisPlayer.add(i1,j1);
			}
		}
	}
*/
}
