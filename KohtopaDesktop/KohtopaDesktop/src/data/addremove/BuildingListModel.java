package data.addremove;

import javax.swing.AbstractListModel;

public class BuildingListModel extends AbstractListModel {

	public int getSize() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public Object getElementAt(int index) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

//	protected ArrayList<Building> items = new ArrayList<Building>();
//
//	public int getSize() {
//		return items.size();
//	}
//
//	public Building getElementAt(int index) {
//		try{
//			return items.get(index);
//		}catch(ArrayIndexOutOfBoundsException ex){
//			System.out.println("error building selected not in scope: " + ex.getMessage());
//			return null;
//		}
//	}
//
//	public void clear() {
//		items.clear();
//	}
//
//	public void addElement(Building building) {
//		items.add(building);
//	}
//
//	public void updateItems() throws SQLException, IOException {
//		items = DataConnector.selectBuildingPreviews();
//	}
//
//	public int getId(int buildingIndex) {
//		return items.get(buildingIndex).getId();
//	}
}
