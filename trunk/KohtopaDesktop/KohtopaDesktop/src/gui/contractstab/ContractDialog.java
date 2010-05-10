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

    private PersonInputPanel personInputPanel;

    private JComboBox cbbMonthFrom;
	private JComboBox cbbYearFrom;
    private JComboBox cbbMonthTo;
	private JComboBox cbbYearTo;

    private JTextField txtPrice;
    private JTextField txtMonthlyCost;
    private JTextField txtGuarantee;

    private JComboBox cbbRentable;

    public ContractDialog(Contract contract) {
        instance = this;

        this.contract = contract;

        rentablesFromOwner = Main.getDataObject().getRentablesFromOwner(ProgramSettings.getOwnerId());
        
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

		pnlPersons.add(new PersonPanel(Main.getDataObject().getPerson(ProgramSettings.getOwnerId()), Language.getString("homeOwner")), BorderLayout.LINE_START);
        if (contract == null) {
            personInputPanel = new PersonInputPanel(null, Language.getString("renter"));
            pnlPersons.add(personInputPanel, BorderLayout.LINE_END);
        } else {
            personInputPanel = new PersonInputPanel(contract.getRenter(), Language.getString("renter"));
            pnlPersons.add(personInputPanel, BorderLayout.LINE_END);
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

		Calendar contractStartDate = Calendar.getInstance();
		contractStartDate.setTime(contract.getStart());

        if (contract == null) {
            cbbMonthFrom.setSelectedIndex(0);
        } else {
            cbbMonthFrom.setSelectedIndex(contractStartDate.get(Calendar.MONTH));
        }

		cbbYearFrom = new JComboBox();
		int yearFrom = GregorianCalendar.getInstance().get(Calendar.YEAR) - 10;
		for (int i = yearFrom; i < yearFrom + 100; i++) {
			cbbYearFrom.addItem(i);
		}
        pnlDates.add(cbbYearFrom);
        if (contract == null) {
            cbbYearFrom.setSelectedItem(GregorianCalendar.getInstance().get(Calendar.YEAR));
        } else {
            cbbYearFrom.setSelectedItem(contractStartDate.get(Calendar.YEAR));
        }

		pnlDates.add(new JLabel(Language.getString("to")));

		Calendar contractEndDate = Calendar.getInstance();
		contractStartDate.setTime(contract.getEnd());

		cbbMonthTo = new JComboBox(Language.getMonthsOfYear());
		pnlDates.add(cbbMonthTo);
        if (contract == null) {
            cbbMonthTo.setSelectedIndex(0);
        } else {
            cbbMonthTo.setSelectedIndex(contractEndDate.get(Calendar.MONTH));
        }

		cbbYearTo = new JComboBox();
        int yearTo = GregorianCalendar.getInstance().get(Calendar.YEAR) - 10 + 1;
		for (int i = yearTo; i < yearTo + 100; i++) {
			cbbYearTo.addItem(i);
		}
        pnlDates.add(cbbYearTo);
        if (contract == null) {
            cbbYearTo.setSelectedItem(GregorianCalendar.getInstance().get(Calendar.YEAR) + 1);
        } else {
            cbbYearTo.setSelectedItem(contractEndDate.get(Calendar.YEAR));
        }

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
            txtMonthlyCost = new JTextField();
            txtMonthlyCost.setColumns(5);
            pnlMontlyCost.add(txtMonthlyCost);
        } else {
            txtMonthlyCost = new JTextField("" + contract.getMonthly_cost());
            txtMonthlyCost.setColumns(5);
            pnlMontlyCost.add(txtMonthlyCost);
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
            } catch (Exception ex) {
				Main.logger.error("Error in getting Address from building: " + ex.getMessage());
				Main.logger.debug("StackTrace: ", ex);
            }
        }
        if (contract == null) {
            cbbRentable.setSelectedIndex(0);
        } else {
            int index = 0;
            for (int i = 0; i < rentablesFromOwner.size(); i++) {
                if (contract.getRentable().getId() == rentablesFromOwner.get(i).getId()) {
                    index = i;
                }
            }
            cbbRentable.setSelectedIndex(index);
        }
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
            personInputPanel.setTxtFirstName(person.getFirstName());
            personInputPanel.setTxtName(person.getName());
            Address homeAddress = person.getHomeAddress();
            personInputPanel.setTxtStreet(homeAddress.getStreet());
            personInputPanel.setTxtStreetNumber(homeAddress.getStreetNumber());
            personInputPanel.setTxtZipCode(homeAddress.getZipcode());
            personInputPanel.setTxtCountry(homeAddress.getCountry());
            personInputPanel.setTxtCity(homeAddress.getCity());
            personInputPanel.setTxtCellphone("");
            personInputPanel.setTxtTelephone("");
            personInputPanel.setTxtEmail("");
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("errFetchEIDPerson") + "\n" + exc.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }

    private void insertContract() {
        // TODO: check if every field is filled in

        // add person
//        int personID = Main.getDataObject().addPerson("user", personInputPanel.getTxtStreet(), personInputPanel.getTxtStreetNumber(),
//                                               personInputPanel.getTxtZipCode(), personInputPanel.getTxtCity(),
//                                               personInputPanel.getTxtCountry(), personInputPanel.getTxtName(),
//                                               personInputPanel.getTxtFirstName(), personInputPanel.getTxtEmail(),
//                                               personInputPanel.getTxtTelephone(), personInputPanel.getTxtCellphone(),
//                                               personInputPanel.getTxtFirstName(), personInputPanel.getTxtName());
//        // TODO: check if person already exsits, if so use this person
//
//        // add contract
//        Date startDate = new Date((Integer)cbbYearFrom.getSelectedItem(), (Integer)cbbMonthFrom.getSelectedItem(), 1);
//        Date endDate = new Date((Integer)cbbYearTo.getSelectedItem(), (Integer)cbbMonthTo.getSelectedItem(), 1);
//        Main.getDataObject().addContract(rentablesFromOwner.get(cbbRentable.getSelectedIndex()).getId(),
//                                         personID, startDate, endDate,
//                                         (Float)txtPrice.getText(), (Float)txtMonthlyCost.getText(), (Float)txtGuarantee.getText());
    }

    private void editContract() {
//        Main.getDataObject().updatePerson(contract.getRenter().getId(), "user", personInputPanel.getTxtStreet(), personInputPanel.getTxtStreetNumber(),
//                                               personInputPanel.getTxtZipCode(), personInputPanel.getTxtCity(),
//                                               personInputPanel.getTxtCountry(), personInputPanel.getTxtName(),
//                                               personInputPanel.getTxtFirstName(), personInputPanel.getTxtEmail(),
//                                               personInputPanel.getTxtTelephone(), personInputPanel.getTxtCellphone(),
//                                               personInputPanel.getTxtFirstName(), personInputPanel.getTxtName());
//        Date startDate = new Date((Integer)cbbYearFrom.getSelectedItem(), (Integer)cbbMonthFrom.getSelectedItem(), 1);
//        Date endDate = new Date((Integer)cbbYearTo.getSelectedItem(), (Integer)cbbMonthTo.getSelectedItem(), 1);
//        Main.getDataObject().updateContract(contract.getId(), rentablesFromOwner.get(cbbRentable.getSelectedIndex()).getId(),
//                                         contract.getRenter().getId(), startDate, endDate,
//                                         (Float)txtPrice.getText(), (Float)txtMonthlyCost.getText(), (Float)txtGuarantee.getText());
    }

}
