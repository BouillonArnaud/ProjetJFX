package model;

public class Case {
	
	private final int index;
	private final int x,y;
	
	public Case(int index, int x, int y) {
		super();
		this.index = index;
		this.x = x;
		this.y = y;
	}

	public int getIndex() {
		return index;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Case) {
			Case c = (Case)o;
			return this.index == c.index && this.x == c.x && this.y == c.y;
		}
		return false;
	}
	
	

}
