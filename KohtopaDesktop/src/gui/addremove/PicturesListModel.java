package gui.addremove;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.AbstractListModel;

public class PicturesListModel extends AbstractListModel {

	private ArrayList<Integer> ids = new ArrayList<Integer>();
	private ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();


	public PicturesListModel() {
	}

	public void add(Integer id, BufferedImage img){
		ids.add(id);
		images.add(img);
	}

	public int getSize() {
		return ids.size();
	}

	public Object getElementAt(int index) {
		return images.get(index);
	}
}
