package gui.invoicestab;

import gui.AbstractListPanel;
import java.awt.Dimension;
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


		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(gbl);


		//adding information labels

		//preview
		JLabel lblImage = new JLabel(img);
		buildConstraints(gbc, 0, 0, 1, 3, 30, 30, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblImage, gbc);
		this.add(lblImage);

		//name
		JLabel lblName = new JLabel(name);
		buildConstraints(gbc, 1, 1, 2, 1, 100, 30, GridBagConstraints.VERTICAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblName, gbc);
		this.add(lblName);
	}
}
