package gui.addremovetab;

import gui.AbstractListPanel;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class BuildingListPanel extends AbstractListPanel {

	public BuildingListPanel(int id, ImageIcon preview, String streetLine, String cityLine) {
		super(id);
		if (preview == null) {
		}
		this.setMaximumSize(new Dimension(1000, 50));
		this.setMinimumSize(new Dimension(300, 50));
		this.setPreferredSize(new Dimension(300, 50));


		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(gbl);


		//adding information labels

		//preview
		JLabel lblImage = new JLabel(preview); //TODO make smaler
		buildConstraints(gbc, 0, 0, 1, 3, 10, 30, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblImage, gbc);
		this.add(lblImage);

		//street + number
		JLabel lblStreet = new JLabel(streetLine);
		buildConstraints(gbc, 1, 1, 2, 1, 100, 30, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblStreet, gbc);
		this.add(lblStreet);

		//postcode + city
		JLabel lblZipcode = new JLabel(cityLine);
		buildConstraints(gbc, 1, 2, 1, 1, 100, 30, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblZipcode, gbc);
		this.add(lblZipcode);
	}
}
