package gui.addremovetab;

import Language.CountryNotFoundException;
import Language.Language;
import gui.Layout;
import gui.Main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import javax.swing.*;
import gui.Logger;
import java.awt.Window;

public class BuildingAddDialog extends JFrame {

	private BuildingAddDialog instance;
	//building
	private JTextField txtStreet;
	private JTextField txtStreetNumber;
	private JTextField txtZip;
	private JTextField txtCity;
	private JComboBox cbbCountry;
	//rentable
	private JComboBox cbbType;
	private JTextField txtArea;
	private JComboBox cbbWindowDir;
	private JTextField txtWindowArea;
	private JCheckBox ckbInternet;
	private JCheckBox ckbCable;
	private JTextField txtOutlets;
	private JTextField txtFloor;
	private JTextField txtPrice;
	private JButton btnConfirm;

	public BuildingAddDialog(Window parent) {
		instance = this;
		setTitle(Language.getString("buildingAdd"));
		this.setIconImage(new ImageIcon(getClass().getResource("/images/building_add_23.png")).getImage());
		this.setPreferredSize(new Dimension(450, 550));
		this.setMinimumSize(new Dimension(300, 450));
		this.setLayout(new BorderLayout());

		//info
		Box boxInformation = Box.createVerticalBox();
		boxInformation.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.add(boxInformation);

		//top info: for building
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		JPanel pnlBuildingInformation = new JPanel(gbl);
		pnlBuildingInformation.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray), Language.getString("building")));
		boxInformation.add(pnlBuildingInformation, BorderLayout.PAGE_START);

		int row = 0; //first row

		JLabel lblStreet = new JLabel(Language.getString("street") + ":");
		Layout.buildConstraints(gbc, 0, row, 1, 1, 10, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblStreet, gbc);
		pnlBuildingInformation.add(lblStreet);

		txtStreet = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 3, 1, 150, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtStreet, gbc);
		pnlBuildingInformation.add(txtStreet);

		JLabel lblStreetNumber = new JLabel(Language.getString("streetNumber") + ":");
		Layout.buildConstraints(gbc, 4, row, 1, 1, 10, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblStreetNumber, gbc);
		pnlBuildingInformation.add(lblStreetNumber);

		txtStreetNumber = new JTextField();
		Layout.buildConstraints(gbc, 5, row, 2, 1, 50, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtStreetNumber, gbc);
		pnlBuildingInformation.add(txtStreetNumber);

		row++; //next row

		JLabel lblZip = new JLabel(Language.getString("zipCode") + ":");
		Layout.buildConstraints(gbc, 0, row, 1, 1, 10, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblZip, gbc);
		pnlBuildingInformation.add(lblZip);

		txtZip = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 3, 1, 150, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtZip, gbc);
		pnlBuildingInformation.add(txtZip);

		row++; //next row

		JLabel lblCity = new JLabel(Language.getString("city") + ":");
		Layout.buildConstraints(gbc, 0, row, 1, 1, 10, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblCity, gbc);
		pnlBuildingInformation.add(lblCity);

		txtCity = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 3, 1, 150, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtCity, gbc);
		pnlBuildingInformation.add(txtCity);

		row++; //next row

		JLabel lblCountry = new JLabel(Language.getString("country") + ":");
		Layout.buildConstraints(gbc, 0, row, 1, 1, 10, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblCountry, gbc);
		pnlBuildingInformation.add(lblCountry);

		cbbCountry = Language.getCountryComboBox();
		try {
			cbbCountry.setSelectedIndex(Language.getIndexByCountryCode("BE"));
		} catch (CountryNotFoundException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		Layout.buildConstraints(gbc, 1, row, 3, 1, 150, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(cbbCountry, gbc);
		pnlBuildingInformation.add(cbbCountry);





		//top info: for Rentable
		GridBagLayout gbl1 = new GridBagLayout();
		JPanel pnlRentableInformation = new JPanel(gbl1);
		pnlRentableInformation.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray), Language.getString("rentable")));
		boxInformation.add(pnlRentableInformation);

		row = 0; //first row

		JLabel lblType = new JLabel(Language.getString("type") + ":");
		Layout.buildConstraints(gbc, 0, row, 1, 1, 10, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl1.addLayoutComponent(lblType, gbc);
		pnlRentableInformation.add(lblType);

		cbbType = new JComboBox(Language.getRentableTypes());
		cbbType.setSelectedItem(Language.getRentableTypes()[3]);
		Layout.buildConstraints(gbc, 1, row, 3, 1, 150, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl1.addLayoutComponent(cbbType, gbc);
		pnlRentableInformation.add(cbbType);

		row++; //next row

		JLabel lblArea = new JLabel(Language.getString("area") + ":");
		Layout.buildConstraints(gbc, 0, row, 1, 1, 10, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl1.addLayoutComponent(lblArea, gbc);
		pnlRentableInformation.add(lblArea);

		txtArea = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 3, 1, 50, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl1.addLayoutComponent(txtArea, gbc);
		pnlRentableInformation.add(txtArea);

		JLabel lblAreaUnit = new JLabel("m²");
		Layout.buildConstraints(gbc, 4, row, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl1.addLayoutComponent(lblAreaUnit, gbc);
		pnlRentableInformation.add(lblAreaUnit);

		row++; //next row

		JLabel lblWindow = new JLabel(Language.getString("windowDir") + ":");
		Layout.buildConstraints(gbc, 0, row, 1, 1, 10, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl1.addLayoutComponent(lblWindow, gbc);
		pnlRentableInformation.add(lblWindow);

		cbbWindowDir = new JComboBox(Language.getWindDir());
		Layout.buildConstraints(gbc, 1, row, 3, 1, 150, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl1.addLayoutComponent(cbbWindowDir, gbc);
		pnlRentableInformation.add(cbbWindowDir);

		row++; //next row

		JLabel lblWindowArea = new JLabel(Language.getString("windowArea") + ":");
		Layout.buildConstraints(gbc, 0, row, 1, 1, 10, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl1.addLayoutComponent(lblWindowArea, gbc);
		pnlRentableInformation.add(lblWindowArea);

		txtWindowArea = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 3, 1, 150, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl1.addLayoutComponent(txtWindowArea, gbc);
		pnlRentableInformation.add(txtWindowArea);

		JLabel lblWindowAreaUnit = new JLabel("m²");
		Layout.buildConstraints(gbc, 4, row, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl1.addLayoutComponent(lblWindowAreaUnit, gbc);
		pnlRentableInformation.add(lblWindowAreaUnit);

		row++; //next row

		JLabel lblInternet = new JLabel(Language.getString("internet") + ":");
		Layout.buildConstraints(gbc, 0, row, 1, 1, 10, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl1.addLayoutComponent(lblInternet, gbc);
		pnlRentableInformation.add(lblInternet);

		ckbInternet = new JCheckBox();
		Layout.buildConstraints(gbc, 1, row, 3, 1, 150, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl1.addLayoutComponent(ckbInternet, gbc);
		pnlRentableInformation.add(ckbInternet);

		row++; //next row

		JLabel lblCable = new JLabel(Language.getString("cable") + ":");
		Layout.buildConstraints(gbc, 0, row, 1, 1, 10, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl1.addLayoutComponent(lblCable, gbc);
		pnlRentableInformation.add(lblCable);

		ckbCable = new JCheckBox();
		Layout.buildConstraints(gbc, 1, row, 3, 1, 150, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl1.addLayoutComponent(ckbCable, gbc);
		pnlRentableInformation.add(ckbCable);

		row++; //next row

		JLabel lblOutlets = new JLabel(Language.getString("outletCount") + ":");
		Layout.buildConstraints(gbc, 0, row, 1, 1, 10, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl1.addLayoutComponent(lblOutlets, gbc);
		pnlRentableInformation.add(lblOutlets);

		txtOutlets = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 3, 1, 150, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl1.addLayoutComponent(txtOutlets, gbc);
		pnlRentableInformation.add(txtOutlets);

		row++; //next row

		JLabel lblFloor = new JLabel(Language.getString("floor") + ":");
		Layout.buildConstraints(gbc, 0, row, 1, 1, 10, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl1.addLayoutComponent(lblFloor, gbc);
		pnlRentableInformation.add(lblFloor);

		txtFloor = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 3, 1, 150, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl1.addLayoutComponent(txtFloor, gbc);
		pnlRentableInformation.add(txtFloor);

		row++; //next row

		JLabel lblPrice = new JLabel(Language.getString("price") + ":");
		Layout.buildConstraints(gbc, 0, row, 1, 1, 10, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl1.addLayoutComponent(lblPrice, gbc);
		pnlRentableInformation.add(lblPrice);

		txtPrice = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 3, 1, 150, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl1.addLayoutComponent(txtPrice, gbc);
		pnlRentableInformation.add(txtPrice);

		JLabel lblPriceUnit = new JLabel("€");
		Layout.buildConstraints(gbc, 4, row, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl1.addLayoutComponent(lblPriceUnit, gbc);
		pnlRentableInformation.add(lblPriceUnit);





		//buttons
		JPanel pnlButtons = new JPanel();
		pnlButtons.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));
		pnlButtons.setLayout(new FlowLayout(FlowLayout.RIGHT));
		this.add(pnlButtons, BorderLayout.PAGE_END);
		JButton btnCancel = new JButton(Language.getString("cancel"), new ImageIcon(getClass().getResource("/images/cancel.png")));
		btnCancel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				instance.dispose();
			}
		});
		pnlButtons.add(btnCancel);

		btnConfirm = new JButton(Language.getString("add"), new ImageIcon(getClass().getResource("/images/ok.png")));
		//add building to database

		btnConfirm.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				if (CheckInput()) {
					try {
						Logger.logger.info("add building");
						Main.getDataObject().addBuilding(txtStreet.getText(), txtStreetNumber.getText(), txtZip.getText(), txtCity.getText(), Language.getCountryCodeByIndex(cbbCountry.getSelectedIndex()));
						Main.updateBuildingList();
						JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("confirmAddBuilding"), Language.getString("succes"), JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/images/succes_48.png")));
					} catch (SQLException ex) {
						JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("errAddBuilding") + ": \n" + ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		pnlButtons.add(btnConfirm);

		//info opvullen:
		pack();
		setLocationRelativeTo(parent);
	}

	public boolean CheckInput() {
		String errorMessage = Language.getString("faultyInput") + ":\n";
		boolean error = false;

		//check buildings inputs
		if (txtStreet.getText().isEmpty()) {
			errorMessage += "   * " + Language.getString("errStreet") + "\n";
			error = true;
			txtStreet.setBackground(Color.pink);
		} else {
			txtStreet.setBackground(Color.white);
		}
		if (!txtStreetNumber.getText().matches("[0-9]+.*")) {
			errorMessage += "   * " + Language.getString("errStreetNumber") + "\n";
			error = true;
			txtStreet.setBackground(Color.pink);
		} else {
			txtStreet.setBackground(Color.white);
		}
		if (!txtCity.getText().matches("[^0-9]+")) {
			errorMessage += "   * " + Language.getString("errCity") + "\n";
			error = true;
			txtCity.setBackground(Color.pink);
		} else {
			txtCity.setBackground(Color.white);
		}
		if (!txtZip.getText().matches("[0-9]*")) {
			errorMessage += "   * " + Language.getString("errZip") + "\n";
			error = true;
			txtCity.setBackground(Color.pink);
		} else {
			txtCity.setBackground(Color.white);
		}

		//check rentable inputs
			if (!txtArea.getText().matches("[0-9]+\\.?[0-9]*")) {
			errorMessage += "   * " + Language.getString("errArea") + "\n";
			error = true;
			txtArea.setBackground(Color.pink);
		} else {
			txtArea.setBackground(Color.white);
		}
		if (!txtWindowArea.getText().matches("[0-9]+\\.?[0-9]*")) {
			errorMessage += "   * " + Language.getString("errWindowArea") + "\n";
			error = true;
			txtWindowArea.setBackground(Color.pink);
		} else {
			txtWindowArea.setBackground(Color.white);
		}
		if (!txtOutlets.getText().matches("[0-9]*")) {
			errorMessage += "   * " + Language.getString("errOutlets") + "\n";
			error = true;
			txtOutlets.setBackground(Color.pink);
		} else {
			txtOutlets.setBackground(Color.white);
		}
		if (!txtFloor.getText().matches("[0-9]*")) {
			errorMessage += "   * " + Language.getString("errFloor") + "\n";
			error = true;
			txtFloor.setBackground(Color.pink);
		} else {
			txtFloor.setBackground(Color.white);
		}
		if (!txtPrice.getText().matches("[0-9]+\\.?[0-9]*")) {
			errorMessage += "   * " + Language.getString("errPrice") + "\n";
			error = true;
			txtPrice.setBackground(Color.pink);
		} else {
			txtPrice.setBackground(Color.white);
		}

		if (error) {
			JOptionPane.showMessageDialog(this, errorMessage, Language.getString("error"), JOptionPane.ERROR_MESSAGE);
		}
		return !error;
	}
}
