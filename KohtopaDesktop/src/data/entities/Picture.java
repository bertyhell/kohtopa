package data.entities;

import java.awt.image.BufferedImage;

public class Picture {

	private BufferedImage picture;
	private int id;

	public Picture(int id, BufferedImage picture) {
		this.id = id;
		this.picture = picture;
	}

	public int getId() {
		return id;
	}

	public BufferedImage getPicture() {
		return picture;
	}
}
