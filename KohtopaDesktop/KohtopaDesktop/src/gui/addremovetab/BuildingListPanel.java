package gui.addremovetab;

import gui.AbstractListPanel;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class BuildingListPanel extends AbstractListPanel {

	private ImageIcon preview;
	private String street;
	private String zipcode;
	private String city;

	public BuildingListPanel(int id, ImageIcon preview, String street, String zipcode, String city) {
		super(id);
		if(preview == null){

		}
		this.preview = preview;
		this.street = street;
		this.zipcode = zipcode;
		this.city = city;

		this.setMaximumSize(new Dimension(1000, 50));
		this.setMinimumSize(new Dimension(300, 50));
		this.setPreferredSize(new Dimension(300, 50));


		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(gbl);


		//adding information labels

		//preview
		JLabel lblImage = new JLabel(preview);
		buildConstraints(gbc, 0, 0, 1, 3, 30, 30, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblImage, gbc);
		this.add(lblImage);

		//street
		JLabel lblStreet = new JLabel(street);
		buildConstraints(gbc, 1, 1, 2, 1, 100, 30, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblStreet, gbc);
		this.add(lblStreet);

		//postcode
		JLabel lblZipcode = new JLabel(zipcode);
		buildConstraints(gbc, 1, 2, 1, 1, 100, 15, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblZipcode, gbc);
		this.add(lblZipcode);


		//city
		JLabel lblCity = new JLabel(city);
		buildConstraints(gbc, 2, 2, 1, 1, 100, 15, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblCity, gbc);
		this.add(lblCity);
	}

	public ImageIcon getPreview() {
		return preview;
	}

	public String getCity() {
		return city;
	}

	public String getStreet() {
		return street;
	}

	public String getZipcode() {
		return zipcode;
	}
}
