package data.entities;

import javax.swing.ImageIcon;

public class Picture {

    private ImageIcon picture;
    private int id;

    public Picture(int id, ImageIcon picture) {
	this.id = id;
	this.picture = picture;
    }

    public int getId() {
	return id;
    }

    public ImageIcon getPicture() {
	return picture;
    }
}
