package gui.addremovetab;

import Exceptions.WrongNumberOfSelectedItemsException;

/**
 *
 * @author Bert Verhelst <verhelst_bert@hotmail.com>
 */
public interface IRentableListContainer extends IIdentifiable{

	public Object[] getSelectedRentables();
	public int getBuildingId() throws WrongNumberOfSelectedItemsException;
	public void updateRentableList();
}
