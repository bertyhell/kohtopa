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
}
