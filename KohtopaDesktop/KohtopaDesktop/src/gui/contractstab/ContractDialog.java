package gui.contractstab;

import Language.Language;
import data.DataConnector;
import data.DataModel;
import data.ProgramSettings;
import data.entities.Address;
import data.entities.Building;
import data.entities.Contract;
import data.entities.Person;
import gui.Layout;
import gui.Logger;
import gui.Main;
import gui.invoicestab.PersonPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Bert Verhelst <verhelst_bert@hotmail.com>
 */
public class ContractDialog extends JDialog {

	private ContractDialog instance;
	private PersonInputPanel personInputPanel;
	private JComboBox cbbMonthFrom;
	private JComboBox cbbYearFrom;
	private JComboBox cbbMonthTo;
	private JComboBox cbbYearTo;
	private JTextField txtPrice;
	private JTextField txtMonthlyCost;
	private JTextField txtGuarantee;
	private JComboBox cbbBuildings;
	private JComboBox cbbRentables;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JTextField txtStreet;
	private JTextField txtZip;
	private JTextField txtCity;
	private JTextField txtCountry;
	private JTextField txtTel;
	private JTextField txtCellphone;
	private JTextField txtEmail;
	private JTextField txtStreetNumber;
	private JButton btnOK;

	public ContractDialog(int contractId, boolean newContract, DataModel data) {
		if (newContract) {
			this.setTitle(Language.getString("contractAdd"));
		} else {
			this.setTitle(Language.getString("contractEdit"));
		}
		this.setLayout(new BorderLayout());

		instance = this;
		this.setIconImage(new ImageIcon(getClass().getResource("/images/user_23.png")).getImage());

		//info
		GridBagLayout gbl1 = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		JPanel pnlInfo = new JPanel(gbl1);
		pnlInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.add(pnlInfo, BorderLayout.CENTER); //TODO 000 add scrolpanes to all forms? or make minimal size

		//persons info
		JPanel pnlPersons = new JPanel(new GridLayout(1, 2));
		Layout.buildConstraints(gbc, 0, 0, 3, 1, 8, 4, GridBagConstraints.BOTH, GridBagConstraints.EAST);
		gbl1.addLayoutComponent(pnlPersons, gbc);
		pnlInfo.add(pnlPersons);

		//owner
		JPanel pnlOwner = new PersonPanel(Main.getDataObject().getPerson(ProgramSettings.getOwnerId()), Language.getString("homeOwner"));
		pnlPersons.add(pnlOwner);

		//renter
		GridBagLayout gbl = new GridBagLayout();
		JPanel pnlInputRenter = new JPanel(gbl);
		pnlPersons.add(pnlInputRenter);
		pnlInputRenter.setBorder(BorderFactory.createTitledBorder(Language.getString("renter")));
		pnlInputRenter.setMinimumSize(new Dimension(200, 300));
		pnlInputRenter.setPreferredSize(new Dimension(300, 300));

		int row = 0;

		JLabel lblFirstName = new JLabel(Language.getString("firstName") + ":");
		Layout.buildConstraints(gbc, 0, ++row, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblFirstName, gbc);
		pnlInputRenter.add(lblFirstName);

		txtFirstName = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 2, 1, 10, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtFirstName, gbc);
		pnlInputRenter.add(txtFirstName);

		JLabel lblLastName = new JLabel(Language.getString("lastName") + ":");
		Layout.buildConstraints(gbc, 0, ++row, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblLastName, gbc);
		pnlInputRenter.add(lblLastName);

		txtLastName = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 2, 1, 10, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtLastName, gbc);
		pnlInputRenter.add(txtLastName);

		JLabel lblStreet = new JLabel(Language.getString("street") + ":");
		Layout.buildConstraints(gbc, 0, ++row, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblStreet, gbc);
		pnlInputRenter.add(lblStreet);

		txtStreet = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 2, 1, 10, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtStreet, gbc);
		pnlInputRenter.add(txtStreet);

		JLabel lblStreetNumber = new JLabel(Language.getString("streetNumber") + ":");
		Layout.buildConstraints(gbc, 3, row, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblStreetNumber, gbc);
		pnlInputRenter.add(lblStreetNumber);

		txtStreetNumber = new JTextField();
		Layout.buildConstraints(gbc, 4, row, 1, 1, 5, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtStreetNumber, gbc);
		pnlInputRenter.add(txtStreetNumber);

		JLabel lblZip = new JLabel(Language.getString("zipCode") + ":");
		Layout.buildConstraints(gbc, 0, ++row, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblZip, gbc);
		pnlInputRenter.add(lblZip);

		txtZip = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 2, 1, 10, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtZip, gbc);
		pnlInputRenter.add(txtZip);

		JLabel lblCity = new JLabel(Language.getString("city") + ":");
		Layout.buildConstraints(gbc, 0, ++row, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblCity, gbc);
		pnlInputRenter.add(lblCity);

		txtCity = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 2, 1, 10, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtCity, gbc);
		pnlInputRenter.add(txtCity);

		JLabel lblCountry = new JLabel(Language.getString("country") + ":");
		Layout.buildConstraints(gbc, 0, ++row, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblCountry, gbc);
		pnlInputRenter.add(lblCountry);

		txtCountry = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 2, 1, 10, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtCountry, gbc);
		pnlInputRenter.add(txtCountry);

		JLabel lblTel = new JLabel(Language.getString("telephone") + ":");
		Layout.buildConstraints(gbc, 0, ++row, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblTel, gbc);
		pnlInputRenter.add(lblTel);

		txtTel = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 2, 1, 10, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtTel, gbc);
		pnlInputRenter.add(txtTel);

		JLabel lblCellphone = new JLabel(Language.getString("cellphone") + ":");
		Layout.buildConstraints(gbc, 0, ++row, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblCellphone, gbc);
		pnlInputRenter.add(lblCellphone);

		txtCellphone = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 2, 1, 10, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtCellphone, gbc);
		pnlInputRenter.add(txtCellphone);

		JLabel lblEmail = new JLabel(Language.getString("email") + ":");
		Layout.buildConstraints(gbc, 0, ++row, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblEmail, gbc);
		pnlInputRenter.add(lblEmail);

		txtEmail = new JTextField();
		Layout.buildConstraints(gbc, 1, row, 2, 1, 10, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtEmail, gbc);
		pnlInputRenter.add(txtEmail);


		//eID
		JPanel pnlEID = new JPanel();
		pnlEID.setLayout(new BoxLayout(pnlEID, BoxLayout.X_AXIS));
		Layout.buildConstraints(gbc, 2, 1, 1, 2, 2, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl1.addLayoutComponent(pnlEID, gbc);
		pnlInfo.add(pnlEID);
		pnlEID.add(Box.createHorizontalGlue());
		JButton btnEID = new JButton(Language.getString("eid"), new ImageIcon(getClass().getResource("/images/credit_card.png")));
		btnEID.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				fetchDataFromEIDCard();
			}
		});
		pnlEID.add(btnEID);


		// options
		JPanel pnlDates = new JPanel(new FlowLayout(FlowLayout.LEFT));
		Layout.buildConstraints(gbc, 0, 1, 2, 1, 6, 1, GridBagConstraints.BOTH, GridBagConstraints.EAST);
		gbl1.addLayoutComponent(pnlDates, gbc);
		pnlInfo.add(pnlDates);

		pnlDates.add(new JLabel(Language.getString("contractInterval") + ":"));

		cbbMonthFrom = new JComboBox(Language.getMonthsOfYear());
		pnlDates.add(cbbMonthFrom);


		cbbYearFrom = new JComboBox();
		int yearFrom = GregorianCalendar.getInstance().get(Calendar.YEAR) - 10;
		for (int i = yearFrom; i < yearFrom + 100; i++) {
			cbbYearFrom.addItem(i);
		}
		pnlDates.add(cbbYearFrom);

		pnlDates.add(new JLabel(Language.getString("to")));

		cbbMonthTo = new JComboBox(Language.getMonthsOfYear());
		pnlDates.add(cbbMonthTo);

		cbbYearTo = new JComboBox();
		int yearTo = GregorianCalendar.getInstance().get(Calendar.YEAR) - 10 + 1;
		for (int i = yearTo; i < yearTo + 100; i++) {
			cbbYearTo.addItem(i);
		}
		pnlDates.add(cbbYearTo);


		//kosten: price
		JPanel pnlPrice = new JPanel(new FlowLayout(FlowLayout.LEFT));
		Layout.buildConstraints(gbc, 0, 2, 2, 1, 6, 1, GridBagConstraints.BOTH, GridBagConstraints.EAST);
		gbl1.addLayoutComponent(pnlPrice, gbc);
		pnlInfo.add(pnlPrice);

		JLabel lblPrice = new JLabel(Language.getString("contractPrice") + ": ");
		pnlPrice.add(lblPrice);

		txtPrice = new JTextField();
		txtPrice.setColumns(5);
		pnlPrice.add(txtPrice);

		//kosten: montly cost
		JPanel pnlMontlyCost = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlPrice.add(pnlMontlyCost);
		JLabel lblMonthlyCost = new JLabel(Language.getString("contractMonthlyCost") + ": ");
		pnlMontlyCost.add(lblMonthlyCost);
		txtMonthlyCost = new JTextField();
		txtMonthlyCost.setColumns(5);
		pnlMontlyCost.add(txtMonthlyCost);


		//kosten: guarantee
		JPanel pnlGuarantee = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlPrice.add(pnlGuarantee);
		JLabel lblGuarantee = new JLabel(Language.getString("invoiceGuarantee") + ": ");
		pnlGuarantee.add(lblGuarantee);
		txtGuarantee = new JTextField();
		txtGuarantee.setColumns(5);
		pnlGuarantee.add(txtGuarantee);


		//choise building
		JPanel pnlRentable = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlDates.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		Layout.buildConstraints(gbc, 0, 3, 3, 1, 8, 1, GridBagConstraints.BOTH, GridBagConstraints.EAST);
		gbl1.addLayoutComponent(pnlRentable, gbc);
		pnlInfo.add(pnlRentable);

		try {
			System.out.println("building previews at combo: " + data.getBuildingPreviews());
			cbbBuildings = new JComboBox(data.getBuildingPreviews());
		} catch (Exception ex) {
			System.out.println("Exception in constructor ContractsPane while getting buildingpreviews: " + ex.getMessage());
			ex.printStackTrace();
			Logger.logger.error("Exception in constructor ContractsPane while getting buildingpreviews: " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			cbbBuildings = new JComboBox();
		}
		cbbBuildings.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				try {
					//TODO 080 trows nullpointer
					System.out.println("combo building item changed");
					cbbRentables = new JComboBox(DataConnector.getRentablesFromBuilding(((Building) ((JComboBox) e.getSource()).getSelectedItem()).getId()));

				} catch (SQLException ex) {
					Logger.logger.error("SQLException in ContractsPane while getting rentablePreviews on select building from combo: " + ex.getMessage());
					Logger.logger.debug("StackTrace: ", ex);
				}
			}
		});
		pnlRentable.add(cbbBuildings);
		try {
			//choise rentable from building
			System.out.println("setting rentables combobox with rentables from building: " + ((Building) cbbBuildings.getSelectedItem()).getId());
			cbbRentables = new JComboBox(DataConnector.getRentablesFromBuilding(((Building) cbbBuildings.getSelectedItem()).getId()));
		} catch (SQLException ex) {
			Logger.logger.error("Exception in constructor ContractsPane while getting rentables from building: " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			cbbRentables = new JComboBox(new Object[]{Language.getString("error")});
		}
		pnlRentable.add(cbbRentables);


		//buttons// final Buttons
		JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnlButtons.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		this.add(pnlButtons, BorderLayout.PAGE_END);

		JButton btnCancel = new JButton(Language.getString("cancel"), new ImageIcon(getClass().getResource("/images/cancel.png")));
		btnCancel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				instance.dispose();
			}
		});
		pnlButtons.add(btnCancel);

		btnOK = new JButton(Language.getString(newContract ? "add" : "update"), new ImageIcon(getClass().getResource("/images/ok.png")));
		pnlButtons.add(btnOK);

		this.pack();
		this.setLocationRelativeTo(null);
	}

	public void fillInfo(Contract contract, boolean newContract) {
		if (newContract) {
			//new contract
			cbbBuildings.setSelectedIndex(0);
			txtFirstName.setText("");
			txtLastName.setText("");
			txtStreet.setText("");
			txtStreetNumber.setText("");
			txtZip.setText("");
			txtCity.setText("");
			txtCountry.setText("");
			txtTel.setText("");
			txtCellphone.setText("");
			txtEmail.setText("");
			//set times in combobox
			cbbMonthFrom.setSelectedIndex(GregorianCalendar.getInstance().get(Calendar.MONTH));
			cbbYearFrom.setSelectedItem(GregorianCalendar.getInstance().get(Calendar.YEAR));
			cbbMonthTo.setSelectedIndex(GregorianCalendar.getInstance().get(Calendar.MONTH));
			cbbYearTo.setSelectedItem(GregorianCalendar.getInstance().get(Calendar.YEAR) + 1);
			//price
			txtPrice.setText("");
			txtMonthlyCost.setText("");
			txtGuarantee.setText("");
		} else {
			//existing contract
			//fill renter info
			Person renter = contract.getRenter();
			txtFirstName.setText(renter.getFirstName());
			txtLastName.setText(renter.getName());
			txtStreet.setText(renter.getAddress().getStreet());
			txtStreetNumber.setText(renter.getAddress().getStreetNumber());
			txtZip.setText(renter.getAddress().getZipcode());
			txtCity.setText(renter.getAddress().getCity());
			txtCountry.setText(renter.getAddress().getCountry());
			txtTel.setText(renter.getTelephone());
			txtCellphone.setText(renter.getCellphone());
			txtEmail.setText(renter.getEmail());

			//set times
			Calendar contractStartDate = Calendar.getInstance();
			contractStartDate.setTime(contract.getStart());
			Calendar contractEndDate = Calendar.getInstance();
			contractStartDate.setTime(contract.getEnd());
			cbbMonthFrom.setSelectedIndex(contractStartDate.get(Calendar.MONTH));
			cbbYearFrom.setSelectedItem(contractStartDate.get(Calendar.YEAR));
			cbbMonthTo.setSelectedIndex(contractEndDate.get(Calendar.MONTH));
			cbbYearTo.setSelectedItem(contractEndDate.get(Calendar.YEAR));
			//set price
			txtPrice.setText(Double.toString(contract.getPrice()));
			txtMonthlyCost.setText(Double.toString(contract.getMonthly_cost()));
			txtGuarantee.setText(Double.toString(contract.getGuarentee()));

			try {
				//TODO 030 isn't perfect buildingpreview object != building object
				cbbBuildings.setSelectedItem(Main.getDataObject().getBuilding(contract.getRentable().getBuildingID())); //TODO 030 isn't perfect buildingpreview object != building object
			} catch (SQLException ex) {
				Logger.logger.error("Exception in contractspane fillinfo during get building from database " + ex.getMessage());
				Logger.logger.debug("StackTrace: ", ex);
			}
		}
	}

	private void fetchDataFromEIDCard() {
		try {
			EIDPerson person = new EIDPerson();
			personInputPanel.setTxtFirstName(person.getFirstName());
			personInputPanel.setTxtName(person.getName());
			Address homeAddress = person.getAddress();
			personInputPanel.setTxtStreet(homeAddress.getStreet());
			personInputPanel.setTxtStreetNumber(homeAddress.getStreetNumber());
			personInputPanel.setTxtZipCode(homeAddress.getZipcode());
			personInputPanel.setTxtCountry(homeAddress.getCountry());
			personInputPanel.setTxtCity(homeAddress.getCity());
			personInputPanel.setTxtCellphone("");
			personInputPanel.setTxtTelephone("");
			personInputPanel.setTxtEmail("");
		} catch (Exception ex) {
			Logger.logger.error("Exception in fetchDataFromEIDCard in contractspane: " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("errFetchEIDPerson") + "\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
