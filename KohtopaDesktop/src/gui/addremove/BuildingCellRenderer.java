package gui.addremove;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class BuildingCellRenderer implements ListCellRenderer {

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		BuildingListPanel comp = (BuildingListPanel) list.getModel().getElementAt(index);

		if(index%2 == 0){
			comp.setBgColor(Color.LIGHT_GRAY);
		}else{
			comp.setBgColor(new Color(170,170,170));
		}
		comp.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e){
				((Component)e.getSource()).setBackground(Color.GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e){
				((BuildingListPanel)e.getSource()).resoreColor();
			}
			

		});
		return comp;
	}
}
