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
import data.entities.Building;
import data.entities.Rentable;
import java.awt.Window;
import java.util.Vector;

public class BuildingDialog extends JFrame implements IRentableListContainer, IFloorListContainer, IPictureListContainer {

	private BuildingDialog instance;
	private int buildingId;
	private JTextField txtStreet;
	private JTextField txtStreetNumber;
	private JTextField txtZip;
	private JTextField txtCity;
	private JComboBox cbbCountry;
	private JList lstFloors;
	private JList lstRentables;
	private JButton btnConfirm;
	private JLabel lblPreview;
	private JList lstPicture;
	private Vector<Integer> floors;

	public BuildingDialog(Window parent, int buildingId, boolean newBuilding) {
		this.buildingId = buildingId;
		instance = this;
//		this.addWindowFocusListener(new WindowAdapter() {
//
//			@Override
//			public void windowGainedFocus(WindowEvent e) {
//				Main.setFocusedDialog(instance);
//			}
//		});
		setTitle(Language.getString(newBuilding ? "buildingAdd" : "buildingEdit"));
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

		lblPreview = new JLabel();
		Layout.buildConstraints(gbc1, 0, 0, 3, 1, 3, 3, GridBagConstraints.CENTER, GridBagConstraints.CENTER);
		gbl1.addLayoutComponent(lblPreview, gbc1);
		pnlPictureButtons.add(lblPreview);

		JButton btnPictureAdd = new JButton(Main.getAction("pictureAdd"));
		btnPictureAdd.setHideActionText(true);
		Layout.buildConstraints(gbc1, 0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.CENTER);
		gbl1.addLayoutComponent(btnPictureAdd, gbc1);
		pnlPictureButtons.add(btnPictureAdd);

		JButton btnPicturePreview = new JButton(Main.getAction("picturePreview"));
		btnPicturePreview.setHideActionText(true);
		Layout.buildConstraints(gbc1, 1, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.CENTER);
		gbl1.addLayoutComponent(btnPicturePreview, gbc1);
		pnlPictureButtons.add(btnPicturePreview);

		JButton btnPictureRemove = new JButton(Main.getAction("pictureRemove"));
		btnPictureRemove.setHideActionText(true);
		Layout.buildConstraints(gbc1, 2, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.CENTER);
		gbl1.addLayoutComponent(btnPictureRemove, gbc1);
		pnlPictureButtons.add(btnPictureRemove);

		//pictures
		lstPicture = new JList();
		lstPicture.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		lstPicture.setBackground(Color.DARK_GRAY);
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
		lstFloors.setBackground(new Color(217, 217, 217));
		lstFloors.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() > 1) {
					new FloorDialog(instance.getRootPane(), instance.getBuildingId(), (Integer)lstFloors.getSelectedValue(), false).setVisible(true);
				}
			}
		});
		JScrollPane scrolFloor = new JScrollPane(lstFloors, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		Layout.buildConstraints(gbc, 0, row, 1, 2, 20, 4, GridBagConstraints.BOTH, GridBagConstraints.WEST);
		gbl2.addLayoutComponent(scrolFloor, gbc);
		pnlRentablesInfo.add(scrolFloor);

		//buttons floor
		JPanel pnlFloorBtns = new JPanel();
		pnlFloorBtns.setPreferredSize(new Dimension(50, 120));
		pnlFloorBtns.setMaximumSize(new Dimension(50, 120));
		pnlFloorBtns.setMinimumSize(new Dimension(50, 120));
		Layout.buildConstraints(gbc, 2, row, 1, 1, 10, 1, GridBagConstraints.NONE, GridBagConstraints.PAGE_START);
		gbl2.addLayoutComponent(pnlFloorBtns, gbc);
		pnlRentablesInfo.add(pnlFloorBtns);

		JButton btnAddFloor = new JButton(Main.getAction("floorAdd"));
		btnAddFloor.setHideActionText(true);
		pnlFloorBtns.add(btnAddFloor);

		JButton btnEditFloor = new JButton(Main.getAction("floorEdit"));
		btnEditFloor.setHideActionText(true);
		pnlFloorBtns.add(btnEditFloor);

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
				int index = lstRentables.locationToIndex(e.getPoint());
				if (e.getClickCount() > 1) {
					if(instance == null){
						System.out.println("instance == 0");
					}
					new RentableDialog(instance, ((Rentable)lstRentables.getSelectedValue()).getId(), false).setVisible(true);
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

		btnConfirm = new JButton("", new ImageIcon(getClass().getResource("/images/ok.png")));
		if (newBuilding) {
			//add building to database
			System.out.println("adding add handler");
			btnConfirm.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseReleased(MouseEvent e) {
					if(CheckInput()){
						try {
							Main.getDataObject().addBuilding(txtStreet.getText(), txtStreetNumber.getText(), txtZip.getText(), txtCity.getText(), Language.getCountryByIndex(cbbCountry.getSelectedIndex()));
							JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("confirmAddBuilding"), Language.getString("succes"), JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/images/succes_48.png")));
						} catch (SQLException ex) {
							JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("errAddBuilding") + ": \n" + ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			});
		} else {
			//update database
			System.out.println("adding update handler");
			btnConfirm.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseReleased(MouseEvent e) {
					System.out.println("click");
					if(CheckInput()){
						try {
							Main.getDataObject().updateBuilding(instance.getBuildingId(), txtStreet.getText(), txtStreetNumber.getText(), txtZip.getText(), txtCity.getText(), Language.getCountryByIndex(cbbCountry.getSelectedIndex()));
						JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("confirmUpdateBuilding"), Language.getString("succes"), JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/images/succes_48.png")));
						} catch (SQLException ex) {
							JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("errUpdateBuilding") + ": \n" + ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			});
		}
		pnlButtons.add(btnConfirm);

		//info opvullen:
		fillInfo(newBuilding);
		pack();
		setLocationRelativeTo(parent);
	}

	public String getType() {
		return "BuildingDialog";
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
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
			btnConfirm.setText(Language.getString("add"));
		} else {
			try {
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

				lblPreview.setIcon(building.getPreviewImage());

				lstRentables.setListData(Main.getDataObject().getRentablesFromBuilding(buildingId));

				
				floors = Main.getDataObject().getFloors(buildingId);
				lstFloors.setListData(floors);
				btnConfirm.setText(Language.getString("update"));
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(this, Language.getString("errBuildingData") + "\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}

//			try {
//				//TODO make multihreaded
//				lstPicture.setModel(Main.getDataObject().updateBuildingPictures(buildingId));
//			} catch (IOException ex) {
//				JOptionPane.showMessageDialog(this, Language.getString("errConnectDatabaseFail") + "\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);//TODO change message?
//			} catch (SQLException ex) {
//				JOptionPane.showMessageDialog(this, Language.getString("errConnectDatabaseFail") + "\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//			}

		}
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
		if (error) {
			JOptionPane.showMessageDialog(this, errorMessage, Language.getString("error"), JOptionPane.ERROR_MESSAGE);
		}
		return !error;
	}

	public void UpdateData() {
		fillInfo(false);
	}

	public void UpdatePictures() {
		lstPicture.repaint();
	}

	public void UpdateDataLists() {
		lstFloors.repaint();
		lstRentables.repaint();
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
}
