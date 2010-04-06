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
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeSet;
import javax.swing.*;
import data.entities.Floor;
import data.entities.Building;
import data.entities.Rentable;

public class BuildingDialog extends JDialog {

	private BuildingDialog instance;
	private int buildingId;
	private JTextField txtStreet;
	private JTextField txtStreetNumber;
	private JTextField txtZip;
	private JTextField txtCity;
	private JComboBox cbbCountry;
	private JList lstFloors;
	private JList lstRentables;
	private ArrayList<Rentable> rentables;
	private JButton btnConfirm;
	private JLabel lblPreview;
	private JList lstPicture;

	public BuildingDialog(Main parent, int buildingId, boolean newBuilding) {
		//TODO fix layout, make 2 panels add them with border layout so that floors and rentables are equal size

		this.buildingId = buildingId;
		instance = this;
		setTitle(Language.getString(newBuilding ? "buildingAdd" : "buildingEdit"));
		this.setIconImage(new ImageIcon(getClass().getResource("/images/building_edit_23.png")).getImage());
		this.setModal(false);
		this.setPreferredSize(new Dimension(700, 600));
		this.setMinimumSize(new Dimension(400, 405));
		this.setLayout(new BorderLayout());

		//images
		JPanel pnlImages = new JPanel(new BorderLayout(10, 10));
		pnlImages.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
		
		pnlImages.setPreferredSize(new Dimension(200,10000));
		pnlImages.setMinimumSize(new Dimension(200,120));
		this.add(pnlImages, BorderLayout.LINE_START);

		//preview
		GridBagLayout gbl1 = new GridBagLayout();
		GridBagConstraints gbc1 = new GridBagConstraints();
		JPanel pnlPictureButtons = new JPanel(gbl1);
		pnlImages.add(pnlPictureButtons, BorderLayout.PAGE_START);

		lblPreview = new JLabel();
		Layout.buildConstraints(gbc1, 0, 0, 3, 1, 3, 3, GridBagConstraints.CENTER, GridBagConstraints.CENTER);
		gbl1.addLayoutComponent(lblPreview, gbc1);
		pnlPictureButtons.add(lblPreview);

		JButton btnPictureAdd = new JButton(Main.getAction("pictureAdd"));
		btnPictureAdd.setName("building"); //for identification in the action (building pic or rentable pic)
		Layout.buildConstraints(gbc1, 0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.CENTER);
		gbl1.addLayoutComponent(btnPictureAdd, gbc1);
		pnlPictureButtons.add(btnPictureAdd);

		JButton btnPicturePreview = new JButton(Main.getAction("picturePreview"));
		btnPicturePreview.setName("building"); //for identification in the action (building pic or rentable pic)
		Layout.buildConstraints(gbc1, 1, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.CENTER);
		gbl1.addLayoutComponent(btnPicturePreview, gbc1);
		pnlPictureButtons.add(btnPicturePreview);

		JButton btnPictureRemove = new JButton(Main.getAction("pictureRemove"));
		btnPictureRemove.setName("building"); //for identification in the action (building pic or rentable pic)
		Layout.buildConstraints(gbc1, 2, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.CENTER);
		gbl1.addLayoutComponent(btnPictureRemove, gbc1);
		pnlPictureButtons.add(btnPictureRemove);

		//pictures
		lstPicture = new JList();
		lstPicture.setBackground(new Color(217, 217, 217));
		lstPicture.setCellRenderer(new PictureCellRenderer());
		JScrollPane picScroller = new JScrollPane(lstPicture, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pnlImages.add(picScroller, BorderLayout.CENTER);


		//info
		JPanel pnlInformation = new JPanel(new BorderLayout());
		this.add(pnlInformation, BorderLayout.CENTER);

		//top info (not scaling)
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		JPanel pnlTopInformation = new JPanel(gbl);
		pnlTopInformation.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
		pnlInformation.add(pnlTopInformation, BorderLayout.PAGE_START);

		int row = 0; //first row

		JLabel lblStreet = new JLabel(Language.getString("street") + ":");
		Layout.buildConstraints(gbc, 0, row, 1, 1, 10, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblStreet, gbc);
		pnlTopInformation.add(lblStreet);

		txtStreet = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 3, 1, 150, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtStreet, gbc);
		pnlTopInformation.add(txtStreet);

		JLabel lblStreetNumber = new JLabel(Language.getString("streetNumber") + ":");
		Layout.buildConstraints(gbc, 4, row, 1, 1, 10, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblStreetNumber, gbc);
		pnlTopInformation.add(lblStreetNumber);

		txtStreetNumber = new JTextField();
		Layout.buildConstraints(gbc, 5, row, 2, 1, 50, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtStreetNumber, gbc);
		pnlTopInformation.add(txtStreetNumber);

		row++; //next row

		JLabel lblZip = new JLabel(Language.getString("zipCode") + ":");
		Layout.buildConstraints(gbc, 0, row, 1, 1, 10, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblZip, gbc);
		pnlTopInformation.add(lblZip);

		txtZip = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 3, 1, 150, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtZip, gbc);
		pnlTopInformation.add(txtZip);

		row++; //next row

		JLabel lblCity = new JLabel(Language.getString("city") + ":");
		Layout.buildConstraints(gbc, 0, row, 1, 1, 10, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblCity, gbc);
		pnlTopInformation.add(lblCity);

		txtCity = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 3, 1, 150, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtCity, gbc);
		pnlTopInformation.add(txtCity);

		row++; //next row

		JLabel lblCountry = new JLabel(Language.getString("country") + ":");
		Layout.buildConstraints(gbc, 0, row, 1, 1, 10, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblCountry, gbc);
		pnlTopInformation.add(lblCountry);

		cbbCountry = Language.getCountryComboBox();
		Layout.buildConstraints(gbc, 1, row, 3, 1, 150, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(cbbCountry, gbc);
		pnlTopInformation.add(cbbCountry);






		//center info (scaling)

		//floors
		row = 0;

		GridBagLayout gbl2 = new GridBagLayout();
		JPanel pnlRentablesInfo = new JPanel(gbl2);
		pnlRentablesInfo.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		pnlInformation.add(pnlRentablesInfo, BorderLayout.CENTER);

		JLabel lblFloors = new JLabel(Language.getString("floors") + ":");
		Layout.buildConstraints(gbc, 0, row, 1, 1, 30, 1, GridBagConstraints.EAST, GridBagConstraints.WEST);
		gbl2.addLayoutComponent(lblFloors, gbc);
		pnlRentablesInfo.add(lblFloors);

		JLabel lblRentable = new JLabel(Language.getString("rentables") + ":");
		Layout.buildConstraints(gbc, 3, row, 1, 1, 30, 1, GridBagConstraints.EAST, GridBagConstraints.WEST);
		gbl2.addLayoutComponent(lblRentable, gbc);
		pnlRentablesInfo.add(lblRentable);

		row++; //next row

		//lijst met floors
		String[] testFloors = {"testFloor1", "testFloor2", "testFloor3"};
		lstFloors = new JList(testFloors);
		JScrollPane scrolFloor = new JScrollPane(lstFloors, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		Layout.buildConstraints(gbc, 0, row, 1, 2, 120, 4, GridBagConstraints.BOTH, GridBagConstraints.WEST);
		gbl2.addLayoutComponent(scrolFloor, gbc);
		pnlRentablesInfo.add(scrolFloor);

		//buttons floor
		JPanel pnlFloorBtns = new JPanel();
		pnlFloorBtns.setPreferredSize(new Dimension(50, 120));
		Layout.buildConstraints(gbc, 2, row, 1, 1, 10, 1, GridBagConstraints.NONE, GridBagConstraints.PAGE_START);
		gbl2.addLayoutComponent(pnlFloorBtns, gbc);
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
					new RentableDialog(instance, rentables.get(index).getId(), false).setVisible(true);
				}
			}
		});
		JScrollPane scrolRentable = new JScrollPane(lstRentables, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		Layout.buildConstraints(gbc, 3, row, 1, 2, 120, 4, GridBagConstraints.BOTH, GridBagConstraints.WEST);
		gbl2.addLayoutComponent(scrolRentable, gbc);
		pnlRentablesInfo.add(scrolRentable);

		//buttons rentables
		JPanel pnlRentableBtns = new JPanel();
		pnlRentableBtns.setPreferredSize(new Dimension(50, 120));
		Layout.buildConstraints(gbc, 6, row, 1, 1, 10, 1, GridBagConstraints.NONE, GridBagConstraints.PAGE_START);
		gbl2.addLayoutComponent(pnlRentableBtns, gbc);
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

		//info opvullen:
		fillInfo(newBuilding);

		pack();

		setLocationRelativeTo(parent);
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

				lblPreview.setIcon(building.getPreviewImage());

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

			try {
				//TODO make multihreaded
				lstPicture.setModel(Main.getDataObject().updateBuildingPictures(buildingId));
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(this, Language.getString("errConnectDatabaseFail") + "\n" + ex.getMessage(), Language.getString("errConnectDatabaseFailTitle"), JOptionPane.ERROR_MESSAGE);//TODO change message?
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(this, Language.getString("errConnectDatabaseFail") + "\n" + ex.getMessage(), Language.getString("errConnectDatabaseFailTitle"), JOptionPane.ERROR_MESSAGE);
			}

		}
	}
}
