package gui.addremovetab;

import Exceptions.WrongNumberOfSelectedItemsException;

/**
 *
 * @author Bert Verhelst <verhelst_bert@hotmail.com>
 */
public interface IRentableListContainer {

	public Object[] getSelectedRentables();
	public int getBuildingId() throws WrongNumberOfSelectedItemsException;
}
