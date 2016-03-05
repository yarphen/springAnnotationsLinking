package com.sheremet.spring.AnnotationsLinking;

public class Player {
	private Position player,shadow;
	public Player(Position player, Position shadow) {
		super();
		this.player = player;
		this.shadow = shadow;
	}
	public Position getPlayerPosition() {
		return player;
	}
	public void setPlayerPosition(Position player) {
		this.player = player;
	}
	public Position getShadowPosition() {
		return shadow;
	}
	public void setShadowPosition(Position shadow) {
		this.shadow = shadow;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (player == null) {
			if (other.player != null)
				return false;
		} else if (!player.equals(other.player))
			return false;
		if (shadow == null) {
			if (other.shadow != null)
				return false;
		} else if (!shadow.equals(other.shadow))
			return false;
		return true;
	}
	
}
