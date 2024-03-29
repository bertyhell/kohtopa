package gui.addremovetab;

import gui.interfaces.IPictureListContainer;
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
import data.entities.Rentable;
import gui.JActionButton;
import java.awt.Window;

public class RentableDialog extends JFrame implements IPictureListContainer {

	private RentableDialog instance;
	private int id;
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
	private JLabel lblPreview;
	private JList lstPicture;

	public RentableDialog(Window parent, int rentableId, boolean newRentable) {
		this.id = rentableId;
		instance = this;
		setTitle(Language.getString(newRentable ? "rentableAdd" : "rentableEdit"));
		this.setIconImage(new ImageIcon(getClass().getResource("/images/building_edit_23.png")).getImage());
		this.setPreferredSize(new Dimension(700, 500));
		this.setMinimumSize(new Dimension(290, 405));
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

		//pictures
		lstPicture = new JList();
		lstPicture.setBackground(Color.DARK_GRAY);
		lstPicture.setCellRenderer(new PictureCellRenderer());
		JScrollPane picScroller = new JScrollPane(lstPicture, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pnlImages.add(picScroller, BorderLayout.CENTER);

		//info
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		JPanel pnlBuildingInfo = new JPanel(gbl);
		pnlBuildingInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
		this.add(pnlBuildingInfo, BorderLayout.CENTER);

		int row = 0; //first row

		JLabel lblType = new JLabel(Language.getString("type") + ":");
		Layout.buildConstraints(gbc, 0, row, 1, 1, 10, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblType, gbc);
		pnlBuildingInfo.add(lblType);

		cbbType = new JComboBox(Language.getRentableTypes());
		cbbType.setSelectedItem(Language.getRentableTypes()[3]);
		Layout.buildConstraints(gbc, 1, row, 3, 1, 150, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(cbbType, gbc);
		pnlBuildingInfo.add(cbbType);

		row++; //next row

		JLabel lblArea = new JLabel(Language.getString("area") + ":");
		Layout.buildConstraints(gbc, 0, row, 1, 1, 10, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblArea, gbc);
		pnlBuildingInfo.add(lblArea);

		txtArea = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 3, 1, 50, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtArea, gbc);
		pnlBuildingInfo.add(txtArea);

		JLabel lblAreaUnit = new JLabel("m²");
		Layout.buildConstraints(gbc, 4, row, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblAreaUnit, gbc);
		pnlBuildingInfo.add(lblAreaUnit);

		row++; //next row

		JLabel lblWindow = new JLabel(Language.getString("windowDir") + ":");
		Layout.buildConstraints(gbc, 0, row, 1, 1, 10, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblWindow, gbc);
		pnlBuildingInfo.add(lblWindow);

		cbbWindowDir = new JComboBox(Language.getWindDir());
		Layout.buildConstraints(gbc, 1, row, 3, 1, 150, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(cbbWindowDir, gbc);
		pnlBuildingInfo.add(cbbWindowDir);

		row++; //next row

		JLabel lblWindowArea = new JLabel(Language.getString("windowArea") + ":");
		Layout.buildConstraints(gbc, 0, row, 1, 1, 10, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblWindowArea, gbc);
		pnlBuildingInfo.add(lblWindowArea);

		txtWindowArea = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 3, 1, 150, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtWindowArea, gbc);
		pnlBuildingInfo.add(txtWindowArea);

		JLabel lblWindowAreaUnit = new JLabel("m²");
		Layout.buildConstraints(gbc, 4, row, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblWindowAreaUnit, gbc);
		pnlBuildingInfo.add(lblWindowAreaUnit);

		row++; //next row

		JLabel lblInternet = new JLabel(Language.getString("internet") + ":");
		Layout.buildConstraints(gbc, 0, row, 1, 1, 10, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblInternet, gbc);
		pnlBuildingInfo.add(lblInternet);

		ckbInternet = new JCheckBox();
		Layout.buildConstraints(gbc, 1, row, 3, 1, 150, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(ckbInternet, gbc);
		pnlBuildingInfo.add(ckbInternet);

		row++; //next row

		JLabel lblCable = new JLabel(Language.getString("cable") + ":");
		Layout.buildConstraints(gbc, 0, row, 1, 1, 10, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblCable, gbc);
		pnlBuildingInfo.add(lblCable);

		ckbCable = new JCheckBox();
		Layout.buildConstraints(gbc, 1, row, 3, 1, 150, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(ckbCable, gbc);
		pnlBuildingInfo.add(ckbCable);

		row++; //next row

		JLabel lblOutlets = new JLabel(Language.getString("outletCount") + ":");
		Layout.buildConstraints(gbc, 0, row, 1, 1, 10, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblOutlets, gbc);
		pnlBuildingInfo.add(lblOutlets);

		txtOutlets = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 3, 1, 150, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtOutlets, gbc);
		pnlBuildingInfo.add(txtOutlets);

		row++; //next row

		JLabel lblFloor = new JLabel(Language.getString("floor") + ":");
		Layout.buildConstraints(gbc, 0, row, 1, 1, 10, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblFloor, gbc);
		pnlBuildingInfo.add(lblFloor);

		txtFloor = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 3, 1, 150, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtFloor, gbc);
		pnlBuildingInfo.add(txtFloor);

		row++; //next row

		JLabel lblPrice = new JLabel(Language.getString("price") + ":");
		Layout.buildConstraints(gbc, 0, row, 1, 1, 10, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblPrice, gbc);
		pnlBuildingInfo.add(lblPrice);

		txtPrice = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 3, 1, 150, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtPrice, gbc);
		pnlBuildingInfo.add(txtPrice);

		JLabel lblPriceUnit = new JLabel("€");
		Layout.buildConstraints(gbc, 4, row, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblPriceUnit, gbc);
		pnlBuildingInfo.add(lblPriceUnit);


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
		if (newRentable) {
			//set button action to: add
			btnConfirm.setText(Language.getString("add"));
			btnConfirm.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseReleased(MouseEvent e) {
					if (CheckInput()) {
						try {
							Main.getDataObject().addRentable(
									instance.getId(), //is buildngid
									cbbType.getSelectedIndex(),
									Double.parseDouble(txtArea.getText()),
									Language.getWindDirCodeFromIndex(cbbWindowDir.getSelectedIndex()),
									Double.parseDouble(txtWindowArea.getText()),
									ckbInternet.isSelected() ? "1" : "0",
									ckbCable.isSelected() ? "1" : "0",
									Integer.parseInt(txtOutlets.getText()),
									Integer.parseInt(txtFloor.getText()),
									Double.parseDouble(txtPrice.getText()));
							Main.updateAddRemoveList();
							JOptionPane.showMessageDialog(Main.getInstance(), "Rentable succesfully added", Language.getString("succes"), JOptionPane.ERROR_MESSAGE, new ImageIcon(getClass().getResource("/images/ok.png")));
							instance.dispose();
						} catch (SQLException ex) {
							JOptionPane.showMessageDialog(Main.getInstance(), ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			});
		} else {
			//set button action to: update
			btnConfirm.setText(Language.getString("update"));
			btnConfirm.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseReleased(MouseEvent e) {
					if (CheckInput()) {
						try {
							Main.getDataObject().updateRentable(
									instance.getRentableId(),
									cbbType.getSelectedIndex(),
									Double.parseDouble(txtArea.getText()),
									Language.getWindDirCodeFromIndex(cbbWindowDir.getSelectedIndex()),
									Double.parseDouble(txtWindowArea.getText()),
									ckbInternet.isSelected() ? "1" : "0",
									ckbCable.isSelected() ? "1" : "0",
									Integer.parseInt(txtOutlets.getText()),
									Integer.parseInt(txtFloor.getText()),
									Double.parseDouble(txtPrice.getText()));
							Main.updateAddRemoveList();
							JOptionPane.showMessageDialog(Main.getInstance(), "Rentable succesfully updated", Language.getString("succes"), JOptionPane.ERROR_MESSAGE, new ImageIcon(getClass().getResource("/images/ok.png")));
							instance.dispose();
						} catch (SQLException ex) {
							JOptionPane.showMessageDialog(Main.getInstance(), ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			});
		}

		pnlButtons.add(btnConfirm);

		pack();
		setLocationRelativeTo(parent);

		//insert info in textfields
		fillInfo(newRentable);
	}

	public int getId() {
		return id;
	}

	public String getType() {
		return "RentableDialog";
	}

	public void fillInfo(boolean isNew) {
		if (isNew) {
			//clear fields
			cbbType.setSelectedIndex(0);
			txtArea.setText("");
			cbbWindowDir.setSelectedIndex(0);
			txtWindowArea.setText("");
			ckbInternet.setSelected(true);
			ckbCable.setSelected(true);
			txtOutlets.setText("5");
			txtFloor.setText("1");
			txtPrice.setText("");
		} else {
			try {
				//fill building info
				Rentable rentable = Main.getDataObject().getRentable(id);
				cbbType.setSelectedIndex(rentable.getType());
				txtArea.setText(Integer.toString(rentable.getArea()));
				cbbWindowDir.setSelectedItem(Language.getWindDir(rentable.getWindowsDirection()));
				txtWindowArea.setText(Integer.toString(rentable.getWindowArea()));
				ckbInternet.setSelected(rentable.isInternet());
				ckbCable.setSelected(rentable.isCable());
				txtOutlets.setText(Integer.toString(rentable.getOutletCount()));
				txtFloor.setText(Integer.toString(rentable.getFloor()));
				txtPrice.setText(Double.toString(rentable.getPrice()));

				updatePictureList();

				lblPreview.setIcon(new ImageIcon(rentable.getPreviewImage()));

				//TODO 000 make collecting images from database multihreaded
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(this, Language.getString("errRentableData") + "\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public boolean CheckInput() {
		String errorMessage = Language.getString("faultyInput") + ":\n";
		boolean error = false;
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

	public void UpdateDataLists() {
		//TODO add current user to rentable dialog
	}

	public Object[] getSelectedPictures() {
		return lstPicture.getSelectedValues();
	}

	public void UpdateData() {
		fillInfo(false);
	}

	public void updatePictureList() {
		try {
			lstPicture.setListData(Main.getDataObject().getPicturesFromRentable(id));
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(Main.getInstance(), "Failed to collect pictures from database:  \n" + ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
		}
	}

	public void updatePreview() {
		fillInfo(false);
	}

	private int getRentableId() {
		return id;
	}
}
