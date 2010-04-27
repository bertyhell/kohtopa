package gui.invoicestab;

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
import javax.swing.*;
import data.entities.Building;

/**
 *
 * @author Bert Verhelst
 */
public class InvoiceDialog extends JFrame {

//	private InvoiceDialog instance;
//	private int invoiceId;
//	private JButton btnConfirm;
//	private JLabel lblRole;
//
//	public InvoiceDialog(JRootPane parent, int invoiceId, boolean newInvoice) {
//		this.invoiceId = invoiceId;
//		instance = this;
//		setTitle(Language.getString(newInvoice ? "invoiceAdd" : "invoiceEdit"));
//		this.setIconImage(new ImageIcon(getClass().getResource("/images/invoice_64.png")).getImage());
//		this.setPreferredSize(new Dimension(1000, 600));
//		this.setMinimumSize(new Dimension(600, 405));
//		this.setLayout(new BorderLayout());
//
//		JPanel pnlInfo = new JPanel(new BorderLayout());
//
//		//top
//		GridBagLayout gbl = new GridBagLayout();
//		GridBagConstraints gbc = new GridBagConstraints();
//
//
//		JPanel pnlHoofding = new JPanel(gbl);
//		pnlInfo.add(pnlHoofding, BorderLayout.PAGE_START);
//
//		lblRole = new JLabel(Language.getString("homeOwner"));
//		Layout.buildConstraints(gbc, 0, 0, 3, 1, 3, 3, GridBagConstraints.CENTER, GridBagConstraints.CENTER);
//		gbl.addLayoutComponent(lblRole, gbc);
//		pnlHoofding.add(lblRole);
//
//		lblName = new JLabel();
//		Layout.buildConstraints(gbc, 0, 0, 3, 1, 3, 3, GridBagConstraints.CENTER, GridBagConstraints.CENTER);
//		gbl.addLayoutComponent(lblRole, gbc);
//		pnlHoofding.add(lblRole);
//
//
//
//		//buttons
//		JPanel pnlButtons = new JPanel();
//		pnlButtons.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));
//		pnlButtons.setLayout(new FlowLayout(FlowLayout.RIGHT));
//		this.add(pnlButtons, BorderLayout.PAGE_END);
//		JButton btnCancel = new JButton(Language.getString("cancel"), new ImageIcon(getClass().getResource("/images/cancel.png")));
//		btnCancel.addMouseListener(new MouseAdapter() {
//
//			@Override
//			public void mouseReleased(MouseEvent e) {
//				instance.dispose();
//			}
//		});
//		pnlButtons.add(btnCancel);
//
//		btnConfirm = new JButton("", new ImageIcon(getClass().getResource("/images/ok.png")));
//		if (newInvoice) {
//			//add building to database
//			System.out.println("adding add handler");
//			btnConfirm.addMouseListener(new MouseAdapter() {
//
//				@Override
//				public void mouseReleased(MouseEvent e) {
//					if(CheckInput()){
//						try {
//							Main.getDataObject().addBuilding(txtStreet.getText(), txtStreetNumber.getText(), txtZip.getText(), txtCity.getText());
//							JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("confirmAddBuilding"), Language.getString("succes"), JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/images/succes_48.png")));
//						} catch (SQLException ex) {
//							JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("errAddBuilding") + ": \n" + ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
//						}
//					}
//				}
//			});
//		} else {
//			//update database
//			System.out.println("adding update handler");
//			btnConfirm.addMouseListener(new MouseAdapter() {
//
//				@Override
//				public void mouseReleased(MouseEvent e) {
//					System.out.println("click");
//					if(CheckInput()){
//						try {
//							Main.getDataObject().updateBuilding(instance.getId(), txtStreet.getText(), txtStreetNumber.getText(), txtZip.getText(), txtCity.getText());
//						JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("confirmUpdateBuilding"), Language.getString("succes"), JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/images/succes_48.png")));
//						} catch (SQLException ex) {
//							JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("errUpdateBuilding") + ": \n" + ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
//						}
//					}
//				}
//			});
//		}
//		pnlButtons.add(btnConfirm);
//
//		//info opvullen:
//		fillInfo(newInvoice);
//		pack();
//		setLocationRelativeTo(parent);
//	}
//
//	public int getId() {
//		return invoiceId;
//	}
//
//	public String getType() {
//		return "BuildingDialog";
//	}
//
//	public void fillInfo(boolean isNew) {
//		if (isNew) {
//			//clear fields
//			txtStreet.setText("");
//			txtStreetNumber.setText("");
//			txtZip.setText("");
//			txtCity.setText("");
//			try {
//				cbbCountry.setSelectedIndex(Language.getIndexByCountryCode("BE"));
//			} catch (CountryNotFoundException ex) {
//				JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//			}
//			btnConfirm.setText(Language.getString("add"));
//		} else {
//			try {
//				//fill building info
//				Building building = Main.getDataObject().getBuilding(invoiceId);
//				txtStreet.setText(building.getStreet());
//				txtStreetNumber.setText(building.getNumber());
//				txtZip.setText(building.getZipcode());
//				txtCity.setText(building.getCity());
//				try {
//					cbbCountry.setSelectedIndex(Language.getIndexByCountryCode(building.getCountry()));
//				} catch (CountryNotFoundException ex) {
//					JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//				}
//
//				lblPreview.setIcon(building.getPreviewImage());
//
//				lstRentables.setModel(Main.getDataObject().getLmRentable());
//
//
//				floors = Main.getDataObject().getFloors();
//				lstFloors.setListData(floors.toArray());
//				btnConfirm.setText(Language.getString("update"));
//			} catch (SQLException ex) {
//				JOptionPane.showMessageDialog(this, Language.getString("errBuildingData") + "\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//			}
//
//			try {
//				//TODO make multihreaded
//				lstPicture.setModel(Main.getDataObject().updateBuildingPictures(invoiceId));
//			} catch (IOException ex) {
//				JOptionPane.showMessageDialog(this, Language.getString("errConnectDatabaseFail") + "\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);//TODO change message?
//			} catch (SQLException ex) {
//				JOptionPane.showMessageDialog(this, Language.getString("errConnectDatabaseFail") + "\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//			}
//
//		}
//	}
//
//	public boolean CheckInput() {
//		String errorMessage = Language.getString("faultyInput") + ":\n";
//		boolean error = false;
//		if (txtStreet.getText().isEmpty()) {
//			errorMessage += "   * " + Language.getString("errStreet") + "\n";
//			error = true;
//			txtStreet.setBackground(Color.pink);
//		} else {
//			txtStreet.setBackground(Color.white);
//		}
//		if (!txtStreetNumber.getText().matches("[0-9]+.*")) {
//			errorMessage += "   * " + Language.getString("errStreetNumber") + "\n";
//			error = true;
//			txtStreet.setBackground(Color.pink);
//		} else {
//			txtStreet.setBackground(Color.white);
//		}
//		if (!txtCity.getText().matches("[^0-9]+")) {
//			errorMessage += "   * " + Language.getString("errCity") + "\n";
//			error = true;
//			txtCity.setBackground(Color.pink);
//		} else {
//			txtCity.setBackground(Color.white);
//		}
//		if (!txtZip.getText().matches("[0-9]*")) {
//			errorMessage += "   * " + Language.getString("errZip") + "\n";
//			error = true;
//			txtCity.setBackground(Color.pink);
//		} else {
//			txtCity.setBackground(Color.white);
//		}
//		if (error) {
//			JOptionPane.showMessageDialog(this, errorMessage, Language.getString("error"), JOptionPane.ERROR_MESSAGE);
//		}
//		return !error;
//	}
}

