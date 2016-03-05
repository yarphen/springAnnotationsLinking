package com.sheremet.spring.AnnotationsLinking;

public class MouseCatPosition {
	private Player mouse,cat;
	public MouseCatPosition(Player mouse, Player cat) {
		super();
		this.mouse = mouse;
		this.cat = cat;
	}
	public Player getMouse() {
		return mouse;
	}
	public void setMouse(Player mouse) {
		this.mouse = mouse;
	}
	public Player getCat() {
		return cat;
	}
	public void setCat(Player cat) {
		this.cat = cat;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MouseCatPosition other = (MouseCatPosition) obj;
		if (cat == null) {
			if (other.cat != null)
				return false;
		} else if (!cat.equals(other.cat))
			return false;
		if (mouse == null) {
			if (other.mouse != null)
				return false;
		} else if (!mouse.equals(other.mouse))
			return false;
		return true;
	}
}
