package model;

public class Pion {

	private int index;

	public Pion(int index) {
		super();
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Pion) {
			Pion p = (Pion) o;
			return this.index == p.index;
		}
		return false;
	}

}
