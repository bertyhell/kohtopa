package gui.AddRemoveTab;

import Language.Language;
import Resources.RelativeLayout;
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
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.HashMap;
import javax.swing.*;
import model.data.Rentable;

public class RentableDialog extends JDialog {

	private static RentableDialog instance;
	private int rentableId;
	private JTextField txtType;
	private JTextField txtArea;
	private JTextField txtWindowDir;
	private JTextField txtWindowArea;
	private JCheckBox ckbInternet;
	private JCheckBox ckbCable;
	private JTextField txtOutlets;
	private JTextField txtFloor;
	private JTextField txtPrice;
	private JButton btnConfirm;

	public static void show(JDialog owner, int rentableId, boolean newRentable) {
		if (instance == null) {
			instance = new RentableDialog(owner);
		}
		instance.setLocationRelativeTo(owner);
		instance.setTitle(Language.getString(newRentable ? "rentableAdd" : "rentableEdit"));

		instance.setBuildingId(rentableId);
		instance.fillInfo(newRentable);
		instance.setVisible(true);
	}

	private RentableDialog(JDialog owner) {
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

		HashMap<Integer, BufferedImage> rentablePictures = null;
		try {
			rentablePictures = Main.getDataObject().getPictures(rentableId, false); //TODO wss nog verkeerd, uitzoeken
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, Language.getString("errConnectDatabaseFail") + "\n" + ex.getMessage(), Language.getString("errConnectDatabaseFailTitle"), JOptionPane.ERROR_MESSAGE);
		}
		for (Integer id : rentablePictures.keySet()) {
			listModelBuildingPictures.add(id, rentablePictures.get(id));
		}

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

		txtType = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 3, 1, 150, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtType, gbc);
		pnlBuildingInfo.add(txtType);

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

		txtWindowDir = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 3, 1, 150, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtWindowDir, gbc);
		pnlBuildingInfo.add(txtWindowDir);

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
		return rentableId;
	}

	public void setBuildingId(int buildingId) {
		this.rentableId = buildingId;
	}

	public void fillInfo(boolean isNew) {
		if (isNew) {
			//clear fields
			txtType.setText("");
			txtArea.setText("");
			txtWindowDir.setText("");
			txtWindowArea.setText("");
			ckbInternet.setSelected(true);
			ckbCable.setSelected(true);
			txtOutlets.setText("5");
			txtFloor.setText("1");
			txtPrice.setText("");
			btnConfirm.setText(Language.getString("add"));
		} else {
			try {
				//fill building info
				Rentable rentable = Main.getDataObject().getRentable(rentableId);
				txtType.setText(Language.getString("rentableType" + rentable.getType()));
				txtArea.setText(Integer.toString(rentable.getArea()));
				txtWindowDir.setText(rentable.getWindowsDirection());
				txtWindowArea.setText(Integer.toString(rentable.getWindowArea()));
				ckbInternet.setSelected(rentable.isInternet());
				ckbCable.setSelected(rentable.isCable());
				txtOutlets.setText(Integer.toString(rentable.getOutletCount()));
				txtFloor.setText(Integer.toString(rentable.getFloor()));
				txtPrice.setText(Double.toString(rentable.getPrice()));
				btnConfirm.setText(Language.getString("update"));
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(this, Language.getString("errRentableData") + "\n" + ex.getMessage(), Language.getString("errDatabaseFailTitle"), JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
