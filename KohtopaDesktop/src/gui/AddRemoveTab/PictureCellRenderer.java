package gui.AddRemoveTab;

import java.awt.Component;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

public class PictureCellRenderer implements ListCellRenderer {

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		System.out.println("list.getModel(): " +  list.getModel());
		return new JLabel("",new ImageIcon((Image)list.getModel().getElementAt(index)),SwingConstants.CENTER);
	}
}
