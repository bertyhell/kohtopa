package gui.interfaces;

import gui.interfaces.IIdentifiable;

/**
 *
 * @author Bert Verhelst <verhelst_bert@hotmail.com>
 */
public interface IBuildingListContainer extends IIdentifiable{

	public Object[] getSelectedBuildings();
	public void updateBuildingList();
}
