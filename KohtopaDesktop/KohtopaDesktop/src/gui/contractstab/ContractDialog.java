/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.contractstab;

import Language.Language;
import data.ProgramSettings;
import data.entities.Address;
import data.entities.Building;
import data.entities.Contract;
import gui.contractstab.EIDPerson;
import data.entities.Rentable;
import gui.Main;
import gui.invoicestab.PersonPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Ruben
 */
public class ContractDialog extends JFrame {

    private ContractDialog instance;

    private Contract contract;

    private Vector<Rentable> rentablesFromOwner;

    private JComboBox cbbMonthFrom;
	private JComboBox cbbYearFrom;
    private JComboBox cbbMonthTo;
	private JComboBox cbbYearTo;

    private JTextField txtPrice;
    private JTextField txtMontlyCost;
    private JTextField txtGuarantee;

    private JComboBox cbbRentable;

    public ContractDialog(Contract contract) {
        instance = this;

        this.contract = contract;

        rentablesFromOwner = Main.getDataObject().getRentablesFromOwner(ProgramSettings.getUserID());
        
        setTitle(Language.getString(contract == null ? "contractAdd" : "contractEdit"));
		this.setIconImage(new ImageIcon(getClass().getResource("/images/contract_64.png")).getImage());
		this.setPreferredSize(new Dimension(600, 700));
		this.setMinimumSize(new Dimension(600, 700));
		this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(Main.getInstance());
        this.setResizable(false);

        // totale info
		JPanel pnlInfo = new JPanel(new BorderLayout());
		this.add(pnlInfo, BorderLayout.CENTER);

		// personen
		JPanel pnlPersons = new JPanel(new BorderLayout());
		pnlInfo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		pnlInfo.add(pnlPersons, BorderLayout.NORTH);

		pnlPersons.add(new PersonPanel(Main.getDataObject().getPerson(ProgramSettings.getUserID()), Language.getString("homeOwner")), BorderLayout.LINE_START);
        if (contract == null) {
            pnlPersons.add(new PersonInputPanel(null, Language.getString("renter")), BorderLayout.LINE_END);
        } else {
            pnlPersons.add(new PersonInputPanel(contract.getRenter(), Language.getString("renter")), BorderLayout.LINE_END);
        }

        // eID
        JPanel pnlEID = new JPanel();
        pnlEID.setLayout(new BoxLayout(pnlEID, BoxLayout.X_AXIS));
        pnlEID.add(Box.createHorizontalGlue());

        JButton btnEID = new JButton(Language.getString("eid"), new ImageIcon(getClass().getResource("/images/credit_card.png")));
        btnEID.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				fetchDataFromEIDCard();
			}
		});
        pnlEID.add(btnEID);
        pnlPersons.add(pnlEID, BorderLayout.PAGE_END);


        // options
		JPanel pnlOptions = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlOptions.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		pnlInfo.add(pnlOptions, BorderLayout.CENTER);

        JPanel pnlDates = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlOptions.add(pnlDates);

		pnlDates.add(new JLabel(Language.getString("contractInterval")));

		cbbMonthFrom = new JComboBox(Language.getMonthsOfYear());
		pnlDates.add(cbbMonthFrom);

		cbbYearFrom = new JComboBox();
		int year = GregorianCalendar.getInstance().get(Calendar.YEAR);
		for (int i = year; i < year + 100; i++) {
			cbbYearFrom.addItem(i);
		}
		cbbYearFrom.setSelectedIndex(0);
		pnlDates.add(cbbYearFrom);

		pnlDates.add(new JLabel(Language.getString("to")));

		cbbMonthTo = new JComboBox(Language.getMonthsOfYear());
		pnlDates.add(cbbMonthTo);

		cbbYearTo = new JComboBox();
		for (int i = year; i < year + 100; i++) {
			cbbYearTo.addItem(i);
		}
		cbbYearTo.setSelectedIndex(0);
		pnlDates.add(cbbYearTo);

        // kosten: price
        JPanel pnlPrice = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlOptions.add(pnlPrice);
        JLabel lblPrice = new JLabel(Language.getString("contractPrice") + ": ");
        pnlPrice.add(lblPrice);
        if (contract == null) {
            txtPrice = new JTextField();
            txtPrice.setColumns(5);
            pnlPrice.add(txtPrice);
        } else {
            txtPrice = new JTextField("" + contract.getPrice());
            txtPrice.setColumns(5);
            pnlPrice.add(txtPrice);
        }

        // kosten: montly cost
        JPanel pnlMontlyCost = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlOptions.add(pnlMontlyCost);
        JLabel lblMonthlyCost = new JLabel(Language.getString("contractMonthlyCost") + ": ");
        pnlMontlyCost.add(lblMonthlyCost);
        if (contract == null) {
            txtMontlyCost = new JTextField();
            txtMontlyCost.setColumns(5);
            pnlMontlyCost.add(txtMontlyCost);
        } else {
            txtMontlyCost = new JTextField("" + contract.getMonthly_cost());
            txtMontlyCost.setColumns(5);
            pnlMontlyCost.add(txtMontlyCost);
        }

        // kosten: guarantee
        JPanel pnlGuarantee = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlOptions.add(pnlGuarantee);
        JLabel lblGuarantee = new JLabel(Language.getString("contractGuarantee") + ": ");
        pnlGuarantee.add(lblGuarantee);
        if (contract == null) {
            txtGuarantee = new JTextField();
            txtGuarantee.setColumns(5);
            pnlGuarantee.add(txtGuarantee);
        } else {
            txtGuarantee = new JTextField("" + contract.getGuarentee());
            txtGuarantee.setColumns(5);
            pnlGuarantee.add(txtGuarantee);
        }

        // keuze uit rentables van de eigenaar
        JPanel pnlRentable = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlOptions.add(pnlRentable);

        cbbRentable = new JComboBox();
        for (Rentable rentable: rentablesFromOwner) {
            try {
                Address address = ((Building)Main.getDataObject().getBuilding(rentable.getBuildingID())).getAddress();
                cbbRentable.addItem(address.getStreetLine() + ", " + address.getZipcode() + " " + address.getCity() + ",  " + address.getCountry() + ": " + rentable.getDescription());
            } catch (Exception exc) {
                System.out.println("Error in getting Address from building");
            }
        }
        cbbRentable.setSelectedIndex(0);
        pnlRentable.add(cbbRentable);

        // final Buttons
        Box boxButtons = Box.createHorizontalBox();
		boxButtons.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
		this.add(boxButtons, BorderLayout.PAGE_END);
        boxButtons.add(Box.createHorizontalGlue());

        JButton btnCancel = new JButton(Language.getString("cancel"), new ImageIcon(getClass().getResource("/images/cancel.png")));
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				instance.dispose();
			}
		});
		boxButtons.add(btnCancel);

        JButton btnOK;
        if (contract == null) {
            btnOK = new JButton(Language.getString("add"), new ImageIcon(getClass().getResource("/images/ok.png")));
            btnOK.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    insertContract();
                }
            });
        } else {
            btnOK = new JButton(Language.getString("edit"), new ImageIcon(getClass().getResource("/images/ok.png")));
            btnOK.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    editContract();
                }
            });
        }
        boxButtons.add(btnOK);
    }

    private void fetchDataFromEIDCard() {
        try {
            EIDPerson person = new EIDPerson();
            System.out.println(person.toString());
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("errFetchEIDPerson") + "\n" + exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }

    private void insertContract() {
        // rentable exists so only the rentableID has to be determined
//        int rentableID = ((Rentable)rentablesFromOwner.get(cbbRentable.getSelectedIndex())).getId();
    }

    private void editContract() {

    }

}
