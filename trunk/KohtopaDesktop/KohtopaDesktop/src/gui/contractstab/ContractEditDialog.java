package gui.contractstab;

import Language.Language;
import data.DataModel;
import data.ProgramSettings;
import data.entities.Contract;
import data.entities.Person;
import gui.Layout;
import gui.Main;
import gui.invoicestab.PersonPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Bert Verhelst <verhelst_bert@hotmail.com>
 */
public class ContractEditDialog extends JDialog {

	private ContractEditDialog instance;
	private Contract contract;
	private JLabel txtMonthFrom;
	private JLabel txtYearFrom;
	private JComboBox cbbMonthTo;
	private JComboBox cbbYearTo;
	private JLabel txtPrice;
	private JLabel txtMonthlyCost;
	private JLabel txtGuarantee;
	private JLabel txtBuildings;
	private JLabel txtRentables;
	private JLabel txtFirstName;
	private JLabel txtLastName;
	private JLabel txtStreet;
	private JLabel txtZip;
	private JLabel txtCity;
	private JLabel cbbCountry;
	private JLabel txtTel;
	private JLabel txtCellphone;
	private JLabel txtEmail;
	private JLabel txtStreetNumber;
	private JButton btnOK;

	public ContractEditDialog(int contractId, DataModel data) {
		this.setTitle(Language.getString("contractEdit"));
		this.setLayout(new BorderLayout());
		instance = this;
		this.setIconImage(new ImageIcon(getClass().getResource("/images/user_23.png")).getImage());

		//get contract:
		contract = data.getContract(contractId);

		


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
		pnlInputRenter.setPreferredSize(new Dimension(400, 300));

		int row = 0;

		JLabel lblFirstName = new JLabel(Language.getString("firstName") + ":");
		Layout.buildConstraints(gbc, 0, ++row, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblFirstName, gbc);
		pnlInputRenter.add(lblFirstName);

		txtFirstName = new JLabel();
		Layout.buildConstraints(gbc, 1, row, 2, 1, 10, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtFirstName, gbc);
		pnlInputRenter.add(txtFirstName);

		JLabel lblLastName = new JLabel(Language.getString("lastName") + ":");
		Layout.buildConstraints(gbc, 0, ++row, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblLastName, gbc);
		pnlInputRenter.add(lblLastName);

		txtLastName = new JLabel();
		Layout.buildConstraints(gbc, 1, row, 2, 1, 10, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtLastName, gbc);
		pnlInputRenter.add(txtLastName);

		JLabel lblStreet = new JLabel(Language.getString("street") + ":");
		Layout.buildConstraints(gbc, 0, ++row, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblStreet, gbc);
		pnlInputRenter.add(lblStreet);

		txtStreet = new JLabel();
		Layout.buildConstraints(gbc, 1, row, 2, 1, 10, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtStreet, gbc);
		pnlInputRenter.add(txtStreet);

		JLabel lblStreetNumber = new JLabel(Language.getString("streetNumber") + ":");
		Layout.buildConstraints(gbc, 3, row, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblStreetNumber, gbc);
		pnlInputRenter.add(lblStreetNumber);

		txtStreetNumber = new JLabel();
		Layout.buildConstraints(gbc, 4, row, 1, 1, 5, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtStreetNumber, gbc);
		pnlInputRenter.add(txtStreetNumber);

		JLabel lblZip = new JLabel(Language.getString("zipCode") + ":");
		Layout.buildConstraints(gbc, 0, ++row, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblZip, gbc);
		pnlInputRenter.add(lblZip);

		txtZip = new JLabel();
		Layout.buildConstraints(gbc, 1, row, 2, 1, 10, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtZip, gbc);
		pnlInputRenter.add(txtZip);

		JLabel lblCity = new JLabel(Language.getString("city") + ":");
		Layout.buildConstraints(gbc, 0, ++row, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblCity, gbc);
		pnlInputRenter.add(lblCity);

		txtCity = new JLabel();
		Layout.buildConstraints(gbc, 1, row, 2, 1, 10, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtCity, gbc);
		pnlInputRenter.add(txtCity);

		JLabel lblCountry = new JLabel(Language.getString("country") + ":");
		Layout.buildConstraints(gbc, 0, ++row, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblCountry, gbc);
		pnlInputRenter.add(lblCountry);

		cbbCountry = new JLabel();
		Layout.buildConstraints(gbc, 1, row, 2, 1, 10, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(cbbCountry, gbc);
		pnlInputRenter.add(cbbCountry);

		JLabel lblTel = new JLabel(Language.getString("telephone") + ":");
		Layout.buildConstraints(gbc, 0, ++row, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblTel, gbc);
		pnlInputRenter.add(lblTel);

		txtTel = new JLabel();
		Layout.buildConstraints(gbc, 1, row, 2, 1, 10, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtTel, gbc);
		pnlInputRenter.add(txtTel);

		JLabel lblCellphone = new JLabel(Language.getString("cellphone") + ":");
		Layout.buildConstraints(gbc, 0, ++row, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblCellphone, gbc);
		pnlInputRenter.add(lblCellphone);

		txtCellphone = new JLabel();
		Layout.buildConstraints(gbc, 1, row, 2, 1, 10, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtCellphone, gbc);
		pnlInputRenter.add(txtCellphone);

		JLabel lblEmail = new JLabel(Language.getString("email") + ":");
		Layout.buildConstraints(gbc, 0, ++row, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.EAST);
		gbl.addLayoutComponent(lblEmail, gbc);
		pnlInputRenter.add(lblEmail);

		txtEmail = new JLabel();
		Layout.buildConstraints(gbc, 1, row, 2, 1, 10, 1, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
		gbl.addLayoutComponent(txtEmail, gbc);
		pnlInputRenter.add(txtEmail);

		// options
		JPanel pnlDates = new JPanel(new FlowLayout(FlowLayout.LEFT));
		Layout.buildConstraints(gbc, 0, 1, 2, 1, 6, 1, GridBagConstraints.BOTH, GridBagConstraints.EAST);
		gbl1.addLayoutComponent(pnlDates, gbc);
		pnlInfo.add(pnlDates);

		pnlDates.add(new JLabel(Language.getString("contractInterval") + ":"));

		txtMonthFrom = new JLabel();
		pnlDates.add(txtMonthFrom);

		txtYearFrom = new JLabel();
		pnlDates.add(txtYearFrom);

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

		txtPrice = new JLabel();
		pnlPrice.add(txtPrice);

		//kosten: montly cost
		JPanel pnlMontlyCost = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlPrice.add(pnlMontlyCost);
		JLabel lblMonthlyCost = new JLabel(Language.getString("contractMonthlyCost") + ": ");
		pnlMontlyCost.add(lblMonthlyCost);
		txtMonthlyCost = new JLabel();
		pnlMontlyCost.add(txtMonthlyCost);


		//kosten: guarantee
		JPanel pnlGuarantee = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlPrice.add(pnlGuarantee);
		JLabel lblGuarantee = new JLabel(Language.getString("invoiceGuarantee") + ": ");
		pnlGuarantee.add(lblGuarantee);
		txtGuarantee = new JLabel();
		pnlGuarantee.add(txtGuarantee);


		//choise building
		JPanel pnlRentable = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlDates.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		Layout.buildConstraints(gbc, 0, 3, 3, 1, 8, 1, GridBagConstraints.BOTH, GridBagConstraints.EAST);
		gbl1.addLayoutComponent(pnlRentable, gbc);
		pnlInfo.add(pnlRentable);

		txtBuildings = new JLabel();
		txtRentables = new JLabel();
		pnlRentable.add(txtBuildings);
		pnlRentable.add(txtRentables);

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


		//update button
		btnOK = new JButton(Language.getString("update"), new ImageIcon(getClass().getResource("/images/ok.png")));
		btnOK.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (checkInput()) {
					//correct input
					Calendar calendar = Calendar.getInstance();
					calendar.set(Integer.parseInt(cbbYearTo.getSelectedItem().toString()), cbbMonthTo.getSelectedIndex() + 1, 1);
					calendar.add(Calendar.DATE, -1);
					Date end = calendar.getTime();
					try {
						Main.getDataObject().updateContract(instance.getContract().getId(), end);
						instance.dispose();
						JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("ContractSuccesUpdate"), Language.getString("succes"), JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/images/succes_48.png")));
					} catch (SQLException ex) {
						JOptionPane.showMessageDialog(Main.getInstance(), ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});


		//TODO 100 implement this button (add mouse listner)
		pnlButtons.add(btnOK);


		fillInfo();
		this.pack();
		this.setLocationRelativeTo(null);
	}

	public void fillInfo() {
		//existing contract
		//fill renter info
		Person renter = contract.getRenter();
		txtFirstName.setText(renter.getFirstName());
		txtLastName.setText(renter.getName());
		txtStreet.setText(renter.getAddress().getStreet());
		txtStreetNumber.setText(renter.getAddress().getStreetNumber());
		txtZip.setText(renter.getAddress().getZipcode());
		txtCity.setText(renter.getAddress().getCity());
		cbbCountry.setText(Language.getCountryByCode(renter.getAddress().getCountry()));
		txtTel.setText(renter.getTelephone());
		txtCellphone.setText(renter.getCellphone());
		txtEmail.setText(renter.getEmail());

		//set times
		Calendar contractStartDate = Calendar.getInstance();
		contractStartDate.setTime(contract.getStart());
		Calendar contractEndDate = Calendar.getInstance();
		contractEndDate.setTime(contract.getEnd());
		txtMonthFrom.setText(Language.getMonthsOfYear()[contractStartDate.get(Calendar.MONTH) - 1]);
		txtYearFrom.setText(Integer.toString(contractStartDate.get(Calendar.YEAR)));
		cbbMonthTo.setSelectedIndex(contractEndDate.get(Calendar.MONTH));
		cbbYearTo.setSelectedItem(contractEndDate.get(Calendar.YEAR));
		//set price
		txtPrice.setText(Double.toString(contract.getPrice()));
		txtMonthlyCost.setText(Double.toString(contract.getMonthly_cost()));
		txtGuarantee.setText(Double.toString(contract.getGuarentee()));
		try {
			System.out.println("buildingid: " + contract.getRentable().getBuildingID());
			txtBuildings.setText(Main.getDataObject().getBuilding(contract.getRentable().getBuildingID()).toString());
			txtRentables.setText(Main.getDataObject().getRentable(contract.getRentable().getId()).getDescription());
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(Main.getInstance(), ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
		}
	}

	private boolean checkInput() {
		Calendar calendar_start = Calendar.getInstance();
		calendar_start.setTime(contract.getStart());
		Calendar calendar_end = Calendar.getInstance();
		calendar_end.setTime(contract.getEnd());


		if (!(calendar_start.get(Calendar.YEAR) < calendar_end.get(Calendar.YEAR))
				|| (calendar_start.get(Calendar.YEAR) == calendar_end.get(Calendar.YEAR))
				&& calendar_start.get(Calendar.MONTH) > calendar_end.get(Calendar.MONTH)) {
			JOptionPane.showMessageDialog(Main.getInstance(), "   * " + Language.getString("errDates"), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
			return false;
		} else {
			return true;
		}

	}

	public Contract getContract() {
		return contract;
	}
}
