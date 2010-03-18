package gui.addremove;

import Language.CountryNotFoundException;
import Language.Language;
import Resources.RelativeLayout;
import gui.Layout;
import gui.Main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import model.data.Building;
import model.Model;
import model.data.Rentable;

public class BuildingDialog extends JDialog {

	private JTextField txtStreet;
	private JTextField txtStreetNumber;
	private JTextField txtZip;
	private JTextField txtCity;
	private JComboBox cbbCountry;
	private JList lstFloors;
	private JList lstRentables;
	private ArrayList<Rentable> rentables;

	public BuildingDialog(Frame owner, int buildingId, boolean newBuilding) {
		super(owner, Language.getString(newBuilding ? "editBuilding" : "addBuilding"), true);
		this.setIconImage(new ImageIcon(getClass().getResource("/images/building_edit_23.png")).getImage());

		this.setPreferredSize(new Dimension(500, 500));
		this.setLayout(new BorderLayout());

		//images
		JPanel pnlImages = new JPanel(new RelativeLayout(RelativeLayout.Y_AXIS, 5));
		this.add(pnlImages, BorderLayout.LINE_START);

		//preview
		JLabel lblPreview = new JLabel();
		pnlImages.add(lblPreview);

		//pictures
		PicturesListModel listModelBuildingPictures = new PicturesListModel();
		JList listPictures = new JList(listModelBuildingPictures);
		listPictures.setBackground(new Color(217, 217, 217));
		listPictures.setCellRenderer(new PictureCellRenderer());

		HashMap<Integer, BufferedImage> buildingPictures = null;
		try {
			buildingPictures = Model.getInstance().getPictures(buildingId, true);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, Language.getString("errConnectDatabaseFail") + "\n" + ex.getMessage(), Language.getString("errConnectDatabaseFailTitle"), JOptionPane.ERROR_MESSAGE);
		}
		for (Integer id : buildingPictures.keySet()) {
			listModelBuildingPictures.add(id, buildingPictures.get(id));
		}

		//info
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		JPanel pnlInformation = new JPanel(gbl);
		pnlInformation.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.add(pnlInformation, BorderLayout.CENTER);

		int row = 0; //first row

		JLabel lblStreet = new JLabel(Language.getString("street") + ":");
		Layout.buildConstraints(gbc, 0, row, 1, 1, 10, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblStreet, gbc);
		pnlInformation.add(lblStreet);

		txtStreet = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 3, 1, 150, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtStreet, gbc);
		pnlInformation.add(txtStreet);

		JLabel lblStreetNumber = new JLabel(Language.getString("streetNumber") + ":");
		Layout.buildConstraints(gbc, 4, row, 1, 1, 10, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblStreetNumber, gbc);
		pnlInformation.add(lblStreetNumber);

		txtStreetNumber = new JTextField();
		Layout.buildConstraints(gbc, 5, row, 2, 1, 50, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtStreetNumber, gbc);
		pnlInformation.add(txtStreetNumber);

		row++; //next row

		JLabel lblZip = new JLabel(Language.getString("zipCode") + ":");
		Layout.buildConstraints(gbc, 0, row, 1, 1, 10, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblZip, gbc);
		pnlInformation.add(lblZip);

		txtZip = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 3, 1, 150, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtZip, gbc);
		pnlInformation.add(txtZip);

		row++; //next row

		JLabel lblCity = new JLabel(Language.getString("city") + ":");
		Layout.buildConstraints(gbc, 0, row, 1, 1, 10, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblCity, gbc);
		pnlInformation.add(lblCity);

		txtCity = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 3, 1, 150, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtCity, gbc);
		pnlInformation.add(txtCity);

		row++; //next row

		JLabel lblCountry = new JLabel(Language.getString("country") + ":");
		Layout.buildConstraints(gbc, 0, row, 1, 1, 10, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblCountry, gbc);
		pnlInformation.add(lblCountry);

		cbbCountry = Language.getCountryComboBox();
		Layout.buildConstraints(gbc, 1, row, 3, 1, 150, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(cbbCountry, gbc);
		pnlInformation.add(cbbCountry);

		row++; //next row

		JLabel lblFloors = new JLabel(Language.getString("floors") + ":");
		Layout.buildConstraints(gbc, 0, row, 3, 1, 150, 1, GridBagConstraints.EAST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblFloors, gbc);
		pnlInformation.add(lblFloors);

		JLabel lblRentable = new JLabel(Language.getString("rentables") + ":");
		Layout.buildConstraints(gbc, 4, row, 4, 1, 150, 1, GridBagConstraints.EAST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblRentable, gbc);
		pnlInformation.add(lblRentable);

		row++; //next row

		//lijst met floors
		String[] testFloors = {"testFloor1", "testFloor2", "testFloor3"};
		lstFloors = new JList(testFloors);
		JScrollPane scrolFloor = new JScrollPane(lstFloors, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		Layout.buildConstraints(gbc, 0, row, 2, 3, 120, 4, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(scrolFloor, gbc);
		pnlInformation.add(scrolFloor);

		//buttons floors
		JButton btnAddFloor = new JButton(Main.getAction("floorAdd"));
		btnAddFloor.setHideActionText(Main.disableBtnText);
		Layout.buildConstraints(gbc, 2, row, 1, 1, 20, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(btnAddFloor, gbc);
		pnlInformation.add(btnAddFloor);

		JButton btnEditFloor = new JButton(Main.getAction("floorEdit"));
		btnEditFloor.setHideActionText(Main.disableBtnText);
		Layout.buildConstraints(gbc, 2, row + 1, 1, 1, 20, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(btnEditFloor, gbc);
		pnlInformation.add(btnEditFloor);

		JButton btnRemoveFloor = new JButton(Main.getAction("floorRemove"));
		btnRemoveFloor.setHideActionText(Main.disableBtnText);
		Layout.buildConstraints(gbc, 2, row + 2, 1, 1, 20, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(btnRemoveFloor, gbc);
		pnlInformation.add(btnRemoveFloor);

		//lijst met rentables
		String[] testRentables = {"testRentable1", "testRentable2", "testRentable3"};
		lstRentables = new JList(testRentables);
		JScrollPane scrolRentable = new JScrollPane(lstRentables, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		Layout.buildConstraints(gbc, 3, row, 3, 3, 120, 4, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(scrolRentable, gbc);
		pnlInformation.add(scrolRentable);

		//buttons rentables
		JButton btnAddRentable = new JButton(Main.getAction("rentableAdd"));
		btnAddRentable.setHideActionText(Main.disableBtnText);
		Layout.buildConstraints(gbc, 6, row, 1, 1, 20, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(btnAddRentable, gbc);
		pnlInformation.add(btnAddRentable);

		JButton btnEditRentable = new JButton(Main.getAction("rentableEdit"));
		btnEditRentable.setHideActionText(Main.disableBtnText);
		Layout.buildConstraints(gbc, 6, row + 1, 1, 1, 20, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(btnEditRentable, gbc);
		pnlInformation.add(btnEditRentable);

		JButton btnRemoveRentable = new JButton(Main.getAction("rentableRemove"));
		btnRemoveRentable.setHideActionText(Main.disableBtnText);
		Layout.buildConstraints(gbc, 6, row + 2, 1, 1, 20, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(btnRemoveRentable, gbc);
		pnlInformation.add(btnRemoveRentable);

		//opvullen
		if (!newBuilding) {
			try {

				//building info
				Building building = Model.getInstance().getBuilding(buildingId);
				txtStreet.setText(building.getStreet());
				txtStreetNumber.setText(building.getNumber());
				txtZip.setText(building.getZipcode());
				txtCity.setText(building.getCity());
				try {
					cbbCountry.setSelectedIndex(Language.getIndexByCountryCode(building.getCountry()));
				} catch (CountryNotFoundException ex) {
					JOptionPane.showMessageDialog(this, ex.getMessage(), Language.getString("errAnalyseFailTitle"), JOptionPane.ERROR_MESSAGE);
				}

				rentables = Model.getInstance().getRentablesFromBuilding(buildingId);
//				String[] rentableIds = new String[rentables.size()];
//				for (int i = 0; i < rentables.size(); i++) {
//					rentableIds[i] = Integer.toString(rentables.get(i).getRantableId());
//
//				}
				lstRentables.setListData(rentables.toArray());


			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(this, Language.getString("errBuildingData") + "\n" + ex.getMessage(), Language.getString("errDatabaseFailTitle"), JOptionPane.ERROR_MESSAGE);
			}
		}

		pack();

		this.setLocationRelativeTo(owner);

	}
}
