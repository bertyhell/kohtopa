package gui.addremove;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class BuildingCellRenderer implements ListCellRenderer {

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		AbstractListPanel comp = (AbstractListPanel) list.getModel().getElementAt(index);

		if(index%2 == 0){
			comp.setBgColor(Color.LIGHT_GRAY);
		}else{
			comp.setBgColor(new Color(170,170,170));
		}
		return comp;
	}
}