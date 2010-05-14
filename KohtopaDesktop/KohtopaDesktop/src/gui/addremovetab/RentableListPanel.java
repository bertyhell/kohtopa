package gui.addremovetab;

import Language.Language;
import gui.AbstractListPanel;
import gui.Main;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class RentableListPanel extends AbstractListPanel {

	private String renter;
	private int floorNumber;

	public RentableListPanel(int id, BufferedImage preview, String type, String renter, int floorNumber, String description) {
		super(id);
		this.renter = renter;
		this.floorNumber = floorNumber;
		this.setMaximumSize(new Dimension(1000, 100));
		this.setMinimumSize(new Dimension(300, 100));
		this.setPreferredSize(new Dimension(300, 100));


		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(gbl);

		//adding information labels

		//preview
		JLabel lblImage = new JLabel(new ImageIcon(Main.resizeImage(preview, 100)));
		buildConstraints(gbc, 0, 0, 1, 3, 30, 30, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblImage, gbc);
		this.add(lblImage);

		//type + description
		JLabel lblType = new JLabel(type + " " + description);
		buildConstraints(gbc, 1, 1, 1, 1, 100, 30, GridBagConstraints.EAST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblType, gbc);
		this.add(lblType);

		//renter
		JLabel lblRenter = new JLabel(Language.getString("renter") + ": " + renter);
		buildConstraints(gbc, 1, 2, 1, 1, 100, 15, GridBagConstraints.EAST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblRenter, gbc);
		this.add(lblRenter);


	}

	public int getNumber() {
		return floorNumber;
	}

	public String getRenter() {
		return renter;
	}
}
