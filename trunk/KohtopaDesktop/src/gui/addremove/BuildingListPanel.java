package gui.addremove;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BuildingListPanel extends JPanel {

	private Icon preview;
	private String name;
	private String street;
	private String zipcode;
	private String city;
	private Color bgColor;

	public BuildingListPanel(int id,
			ImageIcon preview,
			String name,
			String street,
			String zipcode,
			String city) {
		this.setMaximumSize(new Dimension(1000,100));
		this.setMinimumSize(new Dimension(200,100));
		this.setPreferredSize(new Dimension(120,100));


		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(gbl);

		//adding information labels

		//preview
		JLabel lblImage = new JLabel(preview);
		buildConstraints(gbc, 0, 0, 1, 3, 30, 30, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblImage, gbc);
		this.add(lblImage);

		//name
		JLabel lblName = new JLabel(name);
		buildConstraints(gbc, 1, 0, 2, 1, 100, 30, GridBagConstraints.EAST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblName, gbc);
		this.add(lblName);

		//street
		JLabel lblStreet = new JLabel(street);
		buildConstraints(gbc, 1, 1, 2, 1, 100, 30, GridBagConstraints.EAST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblStreet, gbc);
		this.add(lblStreet);

		//postcode
		JLabel lblZipcode = new JLabel(zipcode);
		buildConstraints(gbc, 1, 2, 1, 1, 100, 30, GridBagConstraints.EAST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblZipcode, gbc);
		this.add(lblZipcode);


		//postcode
		JLabel lblCity = new JLabel(city);
		buildConstraints(gbc,2, 2, 1, 1, 100, 30, GridBagConstraints.EAST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblCity, gbc);
		this.add(lblCity);
	}

	public String getCity() {
		return city;
	}

	public Icon getPreview() {
		return preview;
	}

	public String getStreet() {
		return street;
	}

	public String getZipcode() {
		return zipcode;
	}

	public Color getBgColor() {
		return bgColor;
	}

	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
		this.setBackground(bgColor);
	}

	public void resoreColor(){
		this.setBackground(bgColor);
	}



	private void buildConstraints(GridBagConstraints gbc, int x, int y, int w, int h, int wx, int wy, int fill, int anchor) {

		gbc.gridx = x; // start cell in a row
		gbc.gridy = y; // start cell in a column
		gbc.gridwidth = w; // how many column does the control occupy in the row
		gbc.gridheight = h; // how many column does the control occupy in the column
		gbc.weightx = wx; // relative horizontal size
		gbc.weighty = wy; // relative vertical size
		gbc.fill = fill; // the way how the control fills cells
		gbc.anchor = anchor; // alignment
	}
}
