package data.addremove;

import data.DataConnector;
import data.entities.Picture;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.AbstractListModel;

public class PictureListModel extends AbstractListModel {

	protected ArrayList<Picture> items = new ArrayList<Picture>();

	public PictureListModel() {
		super();
	}

	public int getSize() {
		return items.size();
	}

	public Picture getElementAt(int index) {
		return items.get(index);
	}

	public void clear() {
		items.clear();
	}

	public void addElement(Picture pic) {
		items.add(pic);
	}

	public void updateItems(int buildingId, boolean isBuilding) throws SQLException, IOException {
		if (isBuilding) {
			items = DataConnector.getBuildingPictures(buildingId);
		} else {
			items = DataConnector.getRentablePictures(buildingId);
		}
	}

	public int getId(int index) {
		return items.get(index).getId();
	}

	public void removeSelectedPictures(int[] indexs) throws SQLException {
		for (int i = indexs.length - 1; i >= 0; i--) {
			//remove from database
			DataConnector.removePicture(items.get(indexs[i]).getId());
			//remove from local items
			items.remove(indexs[i]);
		}
	}
}
