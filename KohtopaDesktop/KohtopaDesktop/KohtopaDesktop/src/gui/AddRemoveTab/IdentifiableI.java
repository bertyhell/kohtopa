package gui.addremovetab;

public interface IdentifiableI {
	//observer interface for observer pattern
	//observable is the picturelistmodel
	//the update is a pull update

	public int getId();
	public String getType();
	public int[] getSelectedPictures();
	public void UpdateData();
	public void UpdatePictures();
	public void UpdateDataLists();
}
