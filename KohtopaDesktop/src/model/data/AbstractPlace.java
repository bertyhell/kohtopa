package model.data;

public abstract class AbstractPlace {

	protected int id;

	public AbstractPlace(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
