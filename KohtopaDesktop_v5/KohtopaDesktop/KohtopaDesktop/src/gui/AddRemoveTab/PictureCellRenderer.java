package gui.addremovetab;

import data.entities.Picture;
import gui.GuiConstants;
import gui.Main;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class PictureCellRenderer implements ListCellRenderer {

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		return new PictureListPanel(Main.resizeImage(((Picture) list.getModel().getElementAt(index)).getPicture(), GuiConstants.previewSize), isSelected);
	}
}
