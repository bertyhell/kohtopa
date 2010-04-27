package gui.invoicestab;

import gui.AbstractListPanel;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class RenterListPanel extends AbstractListPanel {

	public RenterListPanel(int id, ImageIcon preview, String name) {
		super(id);
		ImageIcon img;
		if (preview == null) {
			img = new ImageIcon(getClass().getResource("/images/dummy_person_preview.png"));
		} else {
			img = preview;
		}

		this.setMaximumSize(new Dimension(1000, 50));
		this.setMinimumSize(new Dimension(200, 50));
		this.setPreferredSize(new Dimension(200, 50));
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		//adding information labels
		this.add(new JLabel(img));
		this.add(new JLabel(name));
	}
}
