package gui.AddRemoveTab;

import Language.CountryNotFoundException;
import Language.Language;
import Resources.RelativeLayout;
import gui.Layout;
import gui.Main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import javax.swing.*;
import model.Floor;
import model.data.Building;
import model.data.Rentable;

public class BuildingDialog extends JDialog {

	private static BuildingDialog instance;
	private int buildingId;
	private JTextField txtStreet;
	private JTextField txtStreetNumber;
	private JTextField txtZip;
	private JTextField txtCity;
	private JComboBox cbbCountry;
	private JList lstFloors;
	private JList lstRentables;
	private ArrayList<Rentable> rentables; //TODO put rentables in model (out of gui)
	private JButton btnConfirm;

	public static void show(Frame owner, int buildingId, boolean newBuilding) {
		if (instance == null) {
			instance = new BuildingDialog(owner);
		}
		instance.setLocationRelativeTo(owner);
		instance.setTitle(Language.getString(newBuilding ? "buildingAdd" : "buildingEdit"));

		instance.setBuildingId(buildingId);
		instance.fillInfo(newBuilding);
		instance.setVisible(true);
	}

	private BuildingDialog(Frame owner) {
		super(owner);
		this.setIconImage(new ImageIcon(getClass().getResource("/images/building_edit_23.png")).getImage());
		this.setModal(true);
		this.setPreferredSize(new Dimension(500, 500));
		this.setMinimumSize(new Dimension(290, 405));
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
			buildingPictures = Main.getDataObject().getPictures(buildingId, true);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, Language.getString("errConnectDatabaseFail") + "\n" + ex.getMessage(), Language.getString("errConnectDatabaseFailTitle"), JOptionPane.ERROR_MESSAGE);
		}
		for (Integer id : buildingPictures.keySet()) {
			listModelBuildingPictures.add(id, buildingPictures.get(id));
		}

		//info
		JPanel pnlInformation = new JPanel(new BorderLayout());
		this.add(pnlInformation, BorderLayout.CENTER);

		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		JPanel pnlBuildingInfo = new JPanel(gbl);
		pnlBuildingInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
		pnlInformation.add(pnlBuildingInfo, BorderLayout.PAGE_START);

		int row = 0; //first row

		JLabel lblStreet = new JLabel(Language.getString("street") + ":");
		Layout.buildConstraints(gbc, 0, row, 1, 1, 10, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblStreet, gbc);
		pnlBuildingInfo.add(lblStreet);

		txtStreet = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 3, 1, 150, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtStreet, gbc);
		pnlBuildingInfo.add(txtStreet);

		JLabel lblStreetNumber = new JLabel(Language.getString("streetNumber") + ":");
		Layout.buildConstraints(gbc, 4, row, 1, 1, 10, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblStreetNumber, gbc);
		pnlBuildingInfo.add(lblStreetNumber);

		txtStreetNumber = new JTextField();
		Layout.buildConstraints(gbc, 5, row, 2, 1, 50, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtStreetNumber, gbc);
		pnlBuildingInfo.add(txtStreetNumber);

		row++; //next row

		JLabel lblZip = new JLabel(Language.getString("zipCode") + ":");
		Layout.buildConstraints(gbc, 0, row, 1, 1, 10, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblZip, gbc);
		pnlBuildingInfo.add(lblZip);

		txtZip = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 3, 1, 150, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtZip, gbc);
		pnlBuildingInfo.add(txtZip);

		row++; //next row

		JLabel lblCity = new JLabel(Language.getString("city") + ":");
		Layout.buildConstraints(gbc, 0, row, 1, 1, 10, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblCity, gbc);
		pnlBuildingInfo.add(lblCity);

		txtCity = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 3, 1, 150, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtCity, gbc);
		pnlBuildingInfo.add(txtCity);

		row++; //next row

		JLabel lblCountry = new JLabel(Language.getString("country") + ":");
		Layout.buildConstraints(gbc, 0, row, 1, 1, 10, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblCountry, gbc);
		pnlBuildingInfo.add(lblCountry);

		cbbCountry = Language.getCountryComboBox();
		Layout.buildConstraints(gbc, 1, row, 3, 1, 150, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(cbbCountry, gbc);
		pnlBuildingInfo.add(cbbCountry);



		//floors
		row = 0;

		GridBagLayout gbl2 = new GridBagLayout();
		JPanel pnlRentablesInfo = new JPanel(gbl);
		pnlRentablesInfo.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		pnlInformation.add(pnlRentablesInfo, BorderLayout.CENTER);

		JLabel lblFloors = new JLabel(Language.getString("floors") + ":");
		Layout.buildConstraints(gbc, 0, row, 1, 1, 30, 1, GridBagConstraints.EAST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblFloors, gbc);
		pnlRentablesInfo.add(lblFloors);

		JLabel lblRentable = new JLabel(Language.getString("rentables") + ":");
		Layout.buildConstraints(gbc, 3, row, 1, 1, 30, 1, GridBagConstraints.EAST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblRentable, gbc);
		pnlRentablesInfo.add(lblRentable);

		row++; //next row

		//lijst met floors
		String[] testFloors = {"testFloor1", "testFloor2", "testFloor3"};
		lstFloors = new JList(testFloors);
		JScrollPane scrolFloor = new JScrollPane(lstFloors, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		Layout.buildConstraints(gbc, 0, row, 1, 2, 120, 4, GridBagConstraints.BOTH, GridBagConstraints.WEST);
		gbl.addLayoutComponent(scrolFloor, gbc);
		pnlRentablesInfo.add(scrolFloor);

		//buttons floor
		JPanel pnlFloorBtns = new JPanel();
		pnlFloorBtns.setPreferredSize(new Dimension(50, 120));
		Layout.buildConstraints(gbc, 2, row, 1, 1, 10, 1, GridBagConstraints.NONE, GridBagConstraints.PAGE_START);
		gbl.addLayoutComponent(pnlFloorBtns, gbc);
		pnlRentablesInfo.add(pnlFloorBtns);

		JButton btnAddFloor = new JButton(Main.getAction("floorAdd"));
		btnAddFloor.setHideActionText(Main.disableBtnText);
		pnlFloorBtns.add(btnAddFloor);

		JButton btnEditFloor = new JButton(Main.getAction("floorEdit"));
		btnEditFloor.setHideActionText(Main.disableBtnText);
		pnlFloorBtns.add(btnEditFloor);

		JButton btnRemoveFloor = new JButton(Main.getAction("floorRemove"));
		btnRemoveFloor.setHideActionText(Main.disableBtnText);
		pnlFloorBtns.add(btnRemoveFloor);


		//lijst met rentables
		lstRentables = new JList();
		lstRentables.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int index = lstRentables.locationToIndex(e.getPoint());
				if (e.getClickCount() == 1) {
					//TODO show rentables in list next to buildings
				} else {
					RentableDialog.show(instance, rentables.get(index).getId(), false);
				}
			}
		});
		JScrollPane scrolRentable = new JScrollPane(lstRentables, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		Layout.buildConstraints(gbc, 3, row, 1, 2, 120, 4, GridBagConstraints.BOTH, GridBagConstraints.WEST);
		gbl.addLayoutComponent(scrolRentable, gbc);
		pnlRentablesInfo.add(scrolRentable);

		//buttons rentables
		JPanel pnlRentableBtns = new JPanel();
		pnlRentableBtns.setPreferredSize(new Dimension(50, 120));
		Layout.buildConstraints(gbc, 6, row, 1, 1, 10, 1, GridBagConstraints.NONE, GridBagConstraints.PAGE_START);
		gbl.addLayoutComponent(pnlRentableBtns, gbc);
		pnlRentablesInfo.add(pnlRentableBtns);

		JButton btnAddRentable = new JButton(Main.getAction("rentableAdd"));
		btnAddRentable.setHideActionText(Main.disableBtnText);
		pnlRentableBtns.add(btnAddRentable);

		JButton btnEditRentable = new JButton(Main.getAction("rentableEdit"));
		btnEditRentable.setHideActionText(Main.disableBtnText);
		pnlRentableBtns.add(btnEditRentable);

		JButton btnRemoveRentable = new JButton(Main.getAction("rentableRemove"));
		btnRemoveRentable.setHideActionText(Main.disableBtnText);
		pnlRentableBtns.add(btnRemoveRentable);

		//buttons
		JPanel pnlButtons = new JPanel();
		pnlButtons.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));
		pnlButtons.setLayout(new FlowLayout(FlowLayout.RIGHT));
		this.add(pnlButtons, BorderLayout.PAGE_END);


		JButton btnCancel = new JButton(Language.getString("cancel"), new ImageIcon(getClass().getResource("/images/cancel.png")));
		btnCancel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				instance.setVisible(false);
			}
		});
		pnlButtons.add(btnCancel);

		btnConfirm = new JButton("", new ImageIcon(getClass().getResource("/images/OK.png")));
		btnConfirm.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {

				JOptionPane.showMessageDialog(null, "Not yet implemented", "implement error", JOptionPane.ERROR_MESSAGE);
			}
		});
		pnlButtons.add(btnConfirm);

		pack();
	}

	public int getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(int buildingId) {
		this.buildingId = buildingId;
	}

	public void fillInfo(boolean isNew) {
		if (isNew) {
			//clear fields
			txtStreet.setText("");
			txtStreetNumber.setText("");
			txtZip.setText("");
			txtCity.setText("");
			try {
				cbbCountry.setSelectedIndex(Language.getIndexByCountryCode("BE"));
			} catch (CountryNotFoundException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), Language.getString("errAnalyseFailTitle"), JOptionPane.ERROR_MESSAGE);
			}
			btnConfirm.setText(Language.getString("add"));
		} else {
			try {
				//fill building info
				Building building = Main.getDataObject().getBuilding(buildingId);
				txtStreet.setText(building.getStreet());
				txtStreetNumber.setText(building.getNumber());
				txtZip.setText(building.getZipcode());
				txtCity.setText(building.getCity());
				try {
					cbbCountry.setSelectedIndex(Language.getIndexByCountryCode(building.getCountry()));
				} catch (CountryNotFoundException ex) {
					JOptionPane.showMessageDialog(this, ex.getMessage(), Language.getString("errAnalyseFailTitle"), JOptionPane.ERROR_MESSAGE);
				}

				rentables = Main.getDataObject().getRentablesFromBuilding(buildingId);
				lstRentables.setListData(rentables.toArray());
				TreeSet<Floor> floors = new TreeSet<Floor>();
				for (Rentable rent : rentables) {
					floors.add(new Floor(rent.getFloor()));
				}
				lstFloors.setListData(floors.toArray());
				btnConfirm.setText(Language.getString("update"));
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(this, Language.getString("errBuildingData") + "\n" + ex.getMessage(), Language.getString("errDatabaseFailTitle"), JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
