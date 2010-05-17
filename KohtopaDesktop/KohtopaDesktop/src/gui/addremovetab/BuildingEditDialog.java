package gui.addremovetab;

import gui.interfaces.IPictureListContainer;
import gui.interfaces.IRentableListContainer;
import gui.interfaces.IFloorListContainer;
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
import data.entities.Building;
import data.entities.Rentable;
import gui.JActionButton;
import gui.Logger;
import java.awt.Window;
import java.util.Vector;

public class BuildingEditDialog extends JFrame implements IRentableListContainer, IFloorListContainer, IPictureListContainer {

	private BuildingEditDialog instance;
	private int buildingId;
	private JTextField txtStreet;
	private JTextField txtStreetNumber;
	private JTextField txtZip;
	private JTextField txtCity;
	private JComboBox cbbCountry;
	private JList lstFloors;
	private JList lstRentables;
	private JButton btnConfirm;
	private JList lstPicture;
	private Vector<Integer> floors;
	private PicturePreviewPanel pnlPreview;
	private JTextField txtIp;

	public BuildingEditDialog(Window parent, int buildingId) {
		this.buildingId = buildingId;
		instance = this;
		setTitle(Language.getString("buildingEdit"));
		this.setIconImage(new ImageIcon(getClass().getResource("/images/building_edit_23.png")).getImage());
		this.setPreferredSize(new Dimension(1000, 600));
		this.setMinimumSize(new Dimension(600, 405));
		this.setLayout(new BorderLayout());

		//images
		JPanel pnlImages = new JPanel(new BorderLayout(10, 10));
		pnlImages.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));

		pnlImages.setPreferredSize(new Dimension(200, 10000));
		pnlImages.setMinimumSize(new Dimension(200, 120));
		this.add(pnlImages, BorderLayout.LINE_START);

		//preview
		GridBagLayout gbl1 = new GridBagLayout();
		GridBagConstraints gbc1 = new GridBagConstraints();
		JPanel pnlPictureButtons = new JPanel(gbl1);
		pnlImages.add(pnlPictureButtons, BorderLayout.PAGE_START);

		pnlPreview = new PicturePreviewPanel("building");
		Layout.buildConstraints(gbc1, 0, 0, 3, 1, 3, 3, GridBagConstraints.CENTER, GridBagConstraints.CENTER);
		gbl1.addLayoutComponent(pnlPreview, gbc1);
		pnlPictureButtons.add(pnlPreview);

		JActionButton btnPictureAdd = new JActionButton(Main.getAction("pictureAdd"), instance);
		btnPictureAdd.setHideActionText(true);
		Layout.buildConstraints(gbc1, 0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.CENTER);
		gbl1.addLayoutComponent(btnPictureAdd, gbc1);
		pnlPictureButtons.add(btnPictureAdd);

		JActionButton btnPicturePreview = new JActionButton(Main.getAction("picturePreview"), instance);
		btnPicturePreview.setHideActionText(true);
		Layout.buildConstraints(gbc1, 1, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.CENTER);
		gbl1.addLayoutComponent(btnPicturePreview, gbc1);
		pnlPictureButtons.add(btnPicturePreview);

		JActionButton btnPictureRemove = new JActionButton(Main.getAction("pictureRemove"), instance);
		btnPictureRemove.setHideActionText(true);
		Layout.buildConstraints(gbc1, 2, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.CENTER);
		gbl1.addLayoutComponent(btnPictureRemove, gbc1);
		pnlPictureButtons.add(btnPictureRemove);
		try {
			//pictures
			lstPicture = new JList(Main.getDataObject().getPicturesFromBuilding(buildingId));

			lstPicture.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			lstPicture.setBackground(Color.DARK_GRAY);
			lstPicture.setCellRenderer(new PictureCellRenderer());
			JScrollPane picScroller = new JScrollPane(lstPicture, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			pnlImages.add(picScroller, BorderLayout.CENTER);
		} catch (SQLException ex) {
			Logger.logger.error("SQLException in constructor BuildingDialog during getting pictures: " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
		}

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

		row++; //next row

		JLabel lblIp = new JLabel("IP:");
		Layout.buildConstraints(gbc, 0, row, 1, 1, 10, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblIp, gbc);
		pnlTopInformation.add(lblIp);

		txtIp = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 3, 1, 150, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtIp, gbc);
		pnlTopInformation.add(txtIp);






		//center info (scaling)

		//floors
		row = 0;

		GridBagLayout gbl2 = new GridBagLayout();
		JPanel pnlRentablesInfo = new JPanel(gbl2);
		pnlRentablesInfo.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		pnlInformation.add(pnlRentablesInfo, BorderLayout.CENTER);

		try {
			//lijst met floors
			lstFloors = new JList(Main.getDataObject().getFloors(buildingId));
			lstFloors.setBackground(new Color(217, 217, 217));
			lstFloors.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() > 1) {
						new FloorDialog(instance.getRootPane(), instance.getBuildingId(), (Integer) lstFloors.getSelectedValue(), false).setVisible(true);
					}
				}
			});

			JScrollPane scrollFloor = new JScrollPane(lstFloors, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollFloor.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), Language.getString("floors")));
			Layout.buildConstraints(gbc, 0, row, 1, 2, 20, 4, GridBagConstraints.BOTH, GridBagConstraints.WEST);
			gbl2.addLayoutComponent(scrollFloor, gbc);
			pnlRentablesInfo.add(scrollFloor);
		} catch (SQLException ex) {
			Logger.logger.error("SQLException in getting floors from database in constructor BuildingDialog: " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
		}

		//buttons floor
		JPanel pnlFloorBtns = new JPanel();
		pnlFloorBtns.setPreferredSize(new Dimension(50, 120));
		pnlFloorBtns.setMaximumSize(new Dimension(50, 120));
		pnlFloorBtns.setMinimumSize(new Dimension(50, 120));
		Layout.buildConstraints(gbc, 2, row, 1, 1, 10, 1, GridBagConstraints.NONE, GridBagConstraints.PAGE_START);
		gbl2.addLayoutComponent(pnlFloorBtns, gbc);
		pnlRentablesInfo.add(pnlFloorBtns);

		JButton btnRemoveFloor = new JButton(Main.getAction("floorRemove"));
		btnRemoveFloor.setHideActionText(true);
		pnlFloorBtns.add(btnRemoveFloor);


		//lijst met rentables
		lstRentables = new JList();
		lstRentables.setBackground(new Color(217, 217, 217));
		lstRentables.setCellRenderer(new RentableCellRenderer());
		lstRentables.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() > 1) {
					if (instance == null) {
						Logger.logger.error("instance == null in buildingdialog");
					} else {
						new RentableDialog(instance, ((Rentable) lstRentables.getSelectedValue()).getId(), false).setVisible(true);
					}
				}
			}
		});
		JScrollPane scrollRentable = new JScrollPane(lstRentables, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollRentable.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), Language.getString("rentables")));
		Layout.buildConstraints(gbc, 3, row, 1, 2, 120, 4, GridBagConstraints.BOTH, GridBagConstraints.WEST);
		gbl2.addLayoutComponent(scrollRentable, gbc);
		pnlRentablesInfo.add(scrollRentable);

		//buttons rentables
		JPanel pnlRentableBtns = new JPanel();
		pnlRentableBtns.setPreferredSize(new Dimension(50, 120));
		pnlRentableBtns.setMaximumSize(new Dimension(50, 120));
		pnlRentableBtns.setMinimumSize(new Dimension(50, 120));
		Layout.buildConstraints(gbc, 6, row, 1, 1, 10, 1, GridBagConstraints.NONE, GridBagConstraints.PAGE_START);
		gbl2.addLayoutComponent(pnlRentableBtns, gbc);
		pnlRentablesInfo.add(pnlRentableBtns);

		JButton btnAddRentable = new JButton(Main.getAction("rentableAdd"));
		btnAddRentable.setHideActionText(true);
		pnlRentableBtns.add(btnAddRentable);

		JButton btnEditRentable = new JButton(Main.getAction("rentableEdit"));
		btnEditRentable.setHideActionText(true);
		pnlRentableBtns.add(btnEditRentable);

		JButton btnRemoveRentable = new JButton(Main.getAction("rentableRemove"));
		btnRemoveRentable.setHideActionText(true);
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
				instance.dispose();
			}
		});
		pnlButtons.add(btnCancel);

		//update database
		btnConfirm = new JButton(Language.getString("update"), new ImageIcon(getClass().getResource("/images/ok.png")));
		btnConfirm.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				if (CheckInput()) {
					try {
						Logger.logger.info("update building");
						Main.getDataObject().updateBuilding(
								instance.getBuildingId(),
								txtStreet.getText(),
								txtStreetNumber.getText(),
								txtZip.getText(),
								txtCity.getText(),
								Language.getCountryCodeByIndex(cbbCountry.getSelectedIndex()),
								txtIp.getText());
						Main.updateAddRemoveList();
						instance.dispose();
						JOptionPane.showMessageDialog(instance, Language.getString("confirmUpdateBuilding"), Language.getString("succes"), JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/images/succes_48.png")));
					} catch (SQLException ex) {
						JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("errUpdateBuilding") + ": \n" + ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		pnlButtons.add(btnConfirm);

		//info opvullen:
		fillInfo();
		pack();
		setLocationRelativeTo(parent);
	}

	public String getType() {
		return "BuildingDialog";
	}

	public void fillInfo() {
		try {
			Logger.logger.info("filling building info");
			//fill building info
			Building building = Main.getDataObject().getBuilding(buildingId);
			txtStreet.setText(building.getAddress().getStreet());
			txtStreetNumber.setText(building.getAddress().getStreetNumber());
			txtZip.setText(building.getAddress().getZipcode());
			txtCity.setText(building.getAddress().getCity());
			try {
				cbbCountry.setSelectedIndex(Language.getIndexByCountryCode(building.getAddress().getCountry()));
			} catch (CountryNotFoundException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}


			pnlPreview.setImage(building.getPreviewImage());
			pnlPreview.repaint();

			lstRentables.setListData(Main.getDataObject().getRentablesFromBuilding(buildingId));


			floors = Main.getDataObject().getFloors(buildingId);
			lstFloors.setListData(floors);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, Language.getString("errBuildingData") + "\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}

		//TODO 000 make picture get dfrom dartabase multihreaded
	}

	public boolean CheckInput() {
		String errorMessage = Language.getString("faultyInput") + ":\n";
		boolean error = false;
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
		if (txtIp.getText().length()!= 0 && !txtIp.getText().matches("(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])")) {
			errorMessage += "   * " + Language.getString("errIp") + "\n";
			error = true;
			txtIp.setBackground(Color.pink);
		} else {
			txtIp.setBackground(Color.white);
		}
		if (error) {
			JOptionPane.showMessageDialog(this, errorMessage, Language.getString("error"), JOptionPane.ERROR_MESSAGE);
		}
		return !error;
	}

	public Object[] getSelectedPictures() {
		return lstPicture.getSelectedValues();
	}

	public Object[] getSelectedRentables() {
		return lstRentables.getSelectedValues();
	}

	public Object[] getSelectedFloors() {
		return lstFloors.getSelectedValues();
	}

	public int getBuildingId() {
		return buildingId;
	}

	public int getId() {
		return buildingId;
	}

	public void updateRentableList() {
		try {
			lstRentables.setListData(Main.getDataObject().getRentablePreviews(buildingId));
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(Main.getInstance(), "Failed to collect rentables from database:  \n" + ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
		}
	}

	public void updateFloorList() {
		try {
			lstFloors.setListData(Main.getDataObject().getFloors(buildingId));
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(Main.getInstance(), "Failed to collect floors from database:  \n" + ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
		}
	}

	public void updatePictureList() {
		try {
			lstPicture.setListData(Main.getDataObject().getPicturesFromBuilding(buildingId));
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(Main.getInstance(), "Failed to collect pictures from database:  \n" + ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
		}
	}

	public void updatePreview() {
		//TODO 000 fix preview in building and rentable dialogs
	}
}
