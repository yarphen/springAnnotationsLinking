package com.sheremet.spring.AnnotationsLinking;

import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("solver")
public class MouseCatSolverImpl implements MouseCatSolver{
	private MouseCatComparator catComparator ;
	private MouseCatComparator mouseComparator ;
	public void solveStep(MouseCatGame game) {
		Position thisPlayer;
		MouseCatPosition position = null;
		MouseCatComparator comparator ;
		boolean isCat;
		switch (game.gameStatus()){
		case MouseCatGame.CAT:
			thisPlayer = game.getPosition().getCat().getPlayerPosition();
			comparator = catComparator;
			isCat = true;
			break;
		case MouseCatGame.MOUSE:
			thisPlayer = game.getPosition().getMouse().getPlayerPosition();
			comparator = mouseComparator;
			isCat = false;
			break;
		default: 
			return;
		}
		comparator.setMode(game.gameStatus());
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
						if (comparator.compare(temp, position)>0)
							position = temp;
					}
				}
			}
		}
		game.turn(isCat?position.getCat().getPlayerPosition():position.getMouse().getPlayerPosition());
	}
	@Autowired
	public void setCatComp(MouseCatComparator catComp) {
		this.catComparator = catComp;
	}
	@Autowired
	public void setMouseComp(MouseCatComparator mouseComp) {
		this.mouseComparator = mouseComp;
	}
}
