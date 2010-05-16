package Language;

import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

public class CountryCellRenderer implements ListCellRenderer {

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		if (index == -1) {
			JLabel lbl = new JLabel(Language.getString("cntryBE"), new ImageIcon(getClass().getResource("/images/flags/be.gif")), SwingConstants.LEFT);
			lbl.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
			return lbl;
		} else {
			JLabel lbl = new JLabel(value.toString(), new ImageIcon(getClass().getResource("/images/flags/" + Language.getCountryCodeByIndex(index).toLowerCase() + ".gif")), SwingConstants.LEADING);
			lbl.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
			return lbl;
		}
	}
}
