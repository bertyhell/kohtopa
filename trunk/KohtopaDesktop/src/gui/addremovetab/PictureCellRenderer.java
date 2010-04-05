package gui.addremovetab;

import data.entities.Picture;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

public class PictureCellRenderer implements ListCellRenderer {

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		System.out.println("list.getModel(): " +  list.getModel());
		return new JLabel("",((Picture)list.getModel().getElementAt(index)).getPicture(),SwingConstants.CENTER);
	}
}
