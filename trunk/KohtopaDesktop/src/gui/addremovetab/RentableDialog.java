package gui.addremovetab;

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
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import javax.swing.*;
import data.entities.Rentable;
import java.awt.event.WindowAdapter;
import java.io.IOException;

public class RentableDialog extends JFrame implements IdentifiableI {

	private RentableDialog instance;
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
	private JLabel lblPreview;
	private JList lstPicture;

	public RentableDialog(JRootPane parent, int rentableId, boolean newRentable) {
		this.rentableId = rentableId;
		instance = this;
		this.addWindowFocusListener(new WindowAdapter() {

			@Override
			public void windowGainedFocus(WindowEvent e) {
				Main.setFocusedDialog(instance);
			}
		});
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

		JButton btnPictureAdd = new JButton(Main.getAction("pictureAdd"));
		Layout.buildConstraints(gbc1, 0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.CENTER);
		gbl1.addLayoutComponent(btnPictureAdd, gbc1);
		pnlPictureButtons.add(btnPictureAdd);

		JButton btnPicturePreview = new JButton(Main.getAction("picturePreview"));
		Layout.buildConstraints(gbc1, 1, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.CENTER);
		gbl1.addLayoutComponent(btnPicturePreview, gbc1);
		pnlPictureButtons.add(btnPicturePreview);

		JButton btnPictureRemove = new JButton(Main.getAction("pictureRemove"));
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
				instance.dispose();
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
		setLocationRelativeTo(parent);

		//insert info in textfields
		fillInfo(newRentable);
	}

	public int getId() {
		return rentableId;
	}

	public String getType() {
		return "RentableDialog";
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
				txtType.setText(rentable.getType());
				txtArea.setText(Integer.toString(rentable.getArea()));
				txtWindowDir.setText(rentable.getWindowsDirection());
				txtWindowArea.setText(Integer.toString(rentable.getWindowArea()));
				ckbInternet.setSelected(rentable.isInternet());
				ckbCable.setSelected(rentable.isCable());
				txtOutlets.setText(Integer.toString(rentable.getOutletCount()));
				txtFloor.setText(Integer.toString(rentable.getFloor()));
				txtPrice.setText(Double.toString(rentable.getPrice()));
				btnConfirm.setText(Language.getString("update"));


				lblPreview.setIcon(rentable.getPreviewImage());

				//TODO make multihreaded
				lstPicture.setModel(Main.getDataObject().updateRentablePictures(rentableId));
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(this, Language.getString("errConnectDatabaseFail") + "\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);//TODO change message?

			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(this, Language.getString("errRentableData") + "\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void UpdatePictures() {
		lstPicture.repaint();
	}

	public void UpdateDataLists() {
		//TODO add current user to rentable dialog
	}

	public int[] getSelectedPictures() {
		return lstPicture.getSelectedIndices();
	}

	public void UpdateData() {
		fillInfo(false);
	}
}