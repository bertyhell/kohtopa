package gui.invoicestab;

import Exceptions.ContractNotValidException;
import Exceptions.XmlGenerationException;
import Language.Language;
import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import javax.swing.*;
import data.entities.Invoice;
import data.entities.InvoiceItem;
import gui.Logger;
import gui.Main;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import javax.swing.Box;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author Bert Verhelst
 */
public class InvoiceDialog extends JFrame {

	private InvoiceDialog instance;
	private Invoice invoice;
	private boolean newInvoice;
	private JButton btnConfirm;
	private JComboBox cbbMonthFrom;
	private JComboBox cbbYearFrom;
	private JComboBox cbbMonthTo;
	private JComboBox cbbYearTo;
	private DefaultTableModel tmInvoices;
	private JTable tableInvoices;
	private JRadioButton rdbBasic;
	private JRadioButton rdbConsumption;
	private JRadioButton rdbFinal;
	private JTextField txtTotal;
	private JDateChooser dch;

	public InvoiceDialog(int personId, int invoiceId, boolean newInvoice) {
		invoice = new Invoice(personId, invoiceId, newInvoice);
		instance = this;
		this.newInvoice = newInvoice;
		setTitle(Language.getString(newInvoice ? "invoiceAdd" : "invoiceEdit"));
		this.setIconImage(new ImageIcon(getClass().getResource("/images/invoice_64.png")).getImage());
		this.setPreferredSize(new Dimension(600, 750));
		this.setMinimumSize(new Dimension(510, 405));
		this.setLayout(new BorderLayout());

		//total
		JPanel pnlInfo = new JPanel(new BorderLayout());
		this.add(pnlInfo, BorderLayout.CENTER);

		//header (persons and dates)
		JPanel pnlHeader = new JPanel(new BorderLayout());
		pnlInfo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		pnlInfo.add(pnlHeader, BorderLayout.PAGE_START);

		pnlHeader.add(new PersonPanel(invoice.getOwner(), Language.getString("homeOwner")), BorderLayout.LINE_START);
		pnlHeader.add(new PersonPanel(invoice.getRenter(), Language.getString("renter")), BorderLayout.LINE_END);

		//options
		Box boxOptions = Box.createVerticalBox();
		boxOptions.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		pnlHeader.add(boxOptions, BorderLayout.PAGE_END);

		//invoice interval in months
		JPanel pnlDates = new JPanel(new FlowLayout(FlowLayout.LEFT));
		boxOptions.add(pnlDates);

		pnlDates.add(new JLabel(Language.getString("invoiceInterval")));

		cbbMonthFrom = new JComboBox(Language.getMonthsOfYear()); //TODO set selected date to last factuur date
		pnlDates.add(cbbMonthFrom);

		cbbYearFrom = new JComboBox();
		int year = GregorianCalendar.getInstance().get(Calendar.YEAR);
		for (int i = year; i > year - 100; i--) {
			cbbYearFrom.addItem(i);
		}
		cbbYearFrom.setSelectedIndex(0);
		pnlDates.add(cbbYearFrom);

		pnlDates.add(new JLabel(Language.getString("to")));

		cbbMonthTo = new JComboBox(Language.getMonthsOfYear()); //TODO set selected date to last factuur date
		pnlDates.add(cbbMonthTo);

		cbbYearTo = new JComboBox();
		for (int i = year; i > year - 100; i--) {
			cbbYearTo.addItem(i);
		}
		cbbYearTo.setSelectedIndex(0);
		pnlDates.add(cbbYearTo);

		//type of invoice
		Box boxTypes = Box.createHorizontalBox();
		boxOptions.add(boxTypes);

		ButtonGroup invoiceTypes = new ButtonGroup();

		rdbBasic = new JRadioButton(Language.getString("basic"));
		rdbBasic.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		rdbBasic.setToolTipText(Language.getString("hlpBasic"));
		rdbBasic.setSelected(true); //TODO store last selected value
		boxTypes.add(rdbBasic);
		rdbConsumption = new JRadioButton(Language.getString("utilities"));
		rdbConsumption.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		rdbConsumption.setToolTipText(Language.getString("hlpUtilities"));
		boxTypes.add(rdbConsumption);
		rdbFinal = new JRadioButton(Language.getString("final"));
		rdbFinal.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		rdbFinal.setToolTipText(Language.getString("hlpFinal"));
		boxTypes.add(rdbFinal);
		ImageIcon img = new ImageIcon(getClass().getResource("/images/help_23.png"));
		JLabel lblHelpTypes = new JLabel(img);
		lblHelpTypes.setToolTipText(Language.getString("hlpIcon"));
		boxTypes.add(lblHelpTypes);
		//TODO overleg: radiobuttons of check box buttons?
		//TODO add option to terminate contract at end date? > overleg met team

		invoiceTypes.add(rdbBasic);
		invoiceTypes.add(rdbConsumption);
		invoiceTypes.add(rdbFinal);

		boxTypes.add(Box.createHorizontalGlue());

		JButton btnUpdateInvoiceItems = new JButton(Language.getString("updateInvoiceItems"), new ImageIcon(getClass().getResource("/images/refresh_23.png")));
		boxTypes.add(btnUpdateInvoiceItems);
		btnUpdateInvoiceItems.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (checkInput()) {

					DefaultTableModel tmItems = instance.getTmInvoices();
					tmItems.setNumRows(0);//TODO 010 make sure added items by homeowner are not deleted

					//adding Invoice items

					int months = 0;
					if (((Integer) cbbYearFrom.getSelectedItem()).equals(cbbYearTo.getSelectedItem())) {
						months = cbbMonthTo.getSelectedIndex() - cbbMonthFrom.getSelectedIndex() + 1;
					} else {
						months = 12 - cbbMonthFrom.getSelectedIndex()
								+ ((Integer) cbbYearTo.getSelectedItem() - (Integer) cbbYearFrom.getSelectedItem()) * 12
								+ cbbMonthTo.getSelectedIndex() + 1;
					}
					//TODO 020 fix utilities prices by use over specified period of time
					//TODO 000 store times in invoices so that there is no overlap possible
					try {
						for (InvoiceItem item : Main.getDataObject().getInvoiceItems(instance.getRenterId(), instance.isNewInvoice(), rdbConsumption.isSelected() || rdbFinal.isSelected(), rdbFinal.isSelected(), months)) {
							tmItems.addRow(item.toObjects());
						}
					} catch (ContractNotValidException ex) {
						JOptionPane.showMessageDialog(Main.getInstance(), "Contract isn't valid anymore, please restart app \nif this problem precists, contact support", Language.getString("error"), JOptionPane.ERROR_MESSAGE);
					}
				}

			}
		});

		//invoice details
		JScrollPane scrollerItems = new JScrollPane();
		pnlInfo.add(scrollerItems, BorderLayout.CENTER);
		tmInvoices = new DefaultTableModel(
				new Object[][]{},
				new String[]{Language.getString("description"), Language.getString("price") + " (€)"}) {

			@Override
			public Class getColumnClass(int columnIndex) {
				if (columnIndex == 0) {
					return String.class;
				} else {
					return Integer.class;
				}
			}

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return true;
			}
		};
		tableInvoices = new JTable(tmInvoices);
		scrollerItems.setViewportView(tableInvoices);

		tableInvoices.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		tableInvoices.getColumnModel().getColumn(1).setMaxWidth(100);
		tableInvoices.setColumnSelectionAllowed(false);
		tableInvoices.getTableHeader().setReorderingAllowed(false);
		tableInvoices.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tableInvoices.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tableInvoices.setAutoCreateRowSorter(true);
		tmInvoices.addTableModelListener(new TableModelListener() {

			public void tableChanged(TableModelEvent e) {

				double total = 0;
				for (int i = 0; i < tmInvoices.getRowCount(); i++) {
					total += Double.parseDouble(tmInvoices.getValueAt(i, 1).toString());
				}
				txtTotal.setText(total + " €");
			}
		});


		//total price:
		Box pnlTotal = Box.createHorizontalBox();
		pnlTotal.add(new JLabel(Language.getString("sendOn") + ": "));
		dch = new JDateChooser(GregorianCalendar.getInstance().getTime());
		dch.setMaximumSize(new Dimension(130, 30));
		dch.setMinSelectableDate(GregorianCalendar.getInstance().getTime());
		dch.setPreferredSize(new Dimension(130, 30));
		pnlTotal.add(dch);
		pnlTotal.add(Box.createHorizontalGlue());
		pnlTotal.add(new JLabel(Language.getString("total")));
		txtTotal = new JTextField();
		txtTotal.setEditable(false);
		txtTotal.setPreferredSize(new Dimension(tableInvoices.getColumnModel().getColumn(1).getWidth(), 30));
		txtTotal.setMaximumSize(new Dimension(100, 30));
		pnlTotal.add(txtTotal);
		pnlInfo.add(pnlTotal, BorderLayout.PAGE_END);

		//buttons
		Box boxButtons = Box.createHorizontalBox();
		boxButtons.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
		this.add(boxButtons, BorderLayout.PAGE_END);

		JButton btnAddInvoiceItem = new JButton("", new ImageIcon(getClass().getResource("/images/add_23.png")));
		btnAddInvoiceItem.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				tmInvoices.addRow(new Object[]{Language.getString("newInvoiceItem"), 0.0});
			}
		});
		boxButtons.add(btnAddInvoiceItem);

		JButton btnRemoveInvoiceItem = new JButton("", new ImageIcon(getClass().getResource("/images/remove_23.png")));
		btnRemoveInvoiceItem.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {

				int[] rows = tableInvoices.getSelectedRows();
				for (int i = rows.length - 1; i >= 0; i--) {
					tmInvoices.removeRow(rows[i]);
				}
			}
		});
		boxButtons.add(btnRemoveInvoiceItem);

		boxButtons.add(Box.createHorizontalGlue());


		JButton btnCancel = new JButton(Language.getString("cancel"), new ImageIcon(getClass().getResource("/images/cancel.png")));
		btnCancel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				instance.dispose();
			}
		});
		boxButtons.add(btnCancel);

		btnConfirm = new JButton("", new ImageIcon(getClass().getResource("/images/ok.png")));
		if (newInvoice) {
			btnConfirm.setText(Language.getString("add"));
			//add new invoice
			btnConfirm.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseReleased(MouseEvent e) {
					try {
						//add new invoice
						//generate xml file and put it in database
						Main.getDataObject().addInvoice(invoice.getRenter().getId(), dch.getDate(), generateXMLString());
						Main.updateInvoiceList();
						JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("invoiceSuccesAdd") + "\n" + Language.getString("invoiceSuccesAdd2"), Language.getString("succes"), JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/images/succes_48.png")));
						instance.dispose();
					} catch (XmlGenerationException ex) {
						JOptionPane.showMessageDialog(instance, ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		} else {
			btnConfirm.setText(Language.getString("update"));
			//update invoice in database
			btnConfirm.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseReleased(MouseEvent e) {
					try {
						//update invoice
						Main.getDataObject().updateInvoice(invoice.getId(), dch.getDate(), generateXMLString());
						Main.updateInvoiceList();
						JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("invoiceSuccesAdd") + "\n" + Language.getString("invoiceSuccesAdd2"), Language.getString("succes"), JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/images/succes_48.png")));
						instance.dispose();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(Main.getInstance(), ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		}
		boxButtons.add(btnConfirm);
		try {
			if (!newInvoice) {
				//info opvullen:
				fillInfo();
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(Main.getInstance(), "Failed to fill info \n" + ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
		}
		pack();
		setLocationRelativeTo(null);
	}

	@SuppressWarnings("unchecked")
	private String generateXMLString() throws XmlGenerationException {
		try {
			//create xml string
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.newDocument();

			//create the root element and add it to the document
			Element root = doc.createElement("invoice");
			doc.appendChild(root);

			//create item elements of every item in table
			System.out.println("getdatavector size: " + tmInvoices.getDataVector().size());
			for (Object item : tmInvoices.getDataVector()) {
				Element invoiceItem = doc.createElement("invoice_item");
				Element desc = doc.createElement("description");
				desc.setTextContent(((Vector<Object>) item).get(0).toString());
				invoiceItem.appendChild(desc);
				Element price = doc.createElement("price");
				price.setTextContent(((Vector<Object>) item).get(1).toString());
				invoiceItem.appendChild(price);
				root.appendChild(invoiceItem);
			}

			//owner info
			Element owner = doc.createElement("owner");
			Element property = doc.createElement("firstname");
			property.setTextContent(invoice.getOwner().getFirstName());
			owner.appendChild(property);
			property = doc.createElement("lastname");
			property.setTextContent(invoice.getOwner().getName());
			owner.appendChild(property);
			property = doc.createElement("street");
			property.setTextContent(invoice.getOwner().getAddress().getStreetLine());
			owner.appendChild(property);
			property = doc.createElement("city");
			property.setTextContent(invoice.getOwner().getAddress().getCityLine());
			owner.appendChild(property);
			property = doc.createElement("country");
			property.setTextContent(Language.getCountryByCode(invoice.getOwner().getAddress().getCountry()));
			owner.appendChild(property);
			property = doc.createElement("telephone");
			property.setTextContent(invoice.getOwner().getTelephone());
			owner.appendChild(property);
			property = doc.createElement("cellphone");
			property.setTextContent(invoice.getOwner().getCellphone());
			owner.appendChild(property);
			property = doc.createElement("email");
			property.setTextContent(invoice.getOwner().getEmail());
			owner.appendChild(property);
			root.appendChild(owner);

			//renter info
			Element renter = doc.createElement("renter");
			property = doc.createElement("firstname");
			property.setTextContent(invoice.getRenter().getFirstName());
			renter.appendChild(property);
			property = doc.createElement("lastname");
			property.setTextContent(invoice.getRenter().getName());
			renter.appendChild(property);
			property = doc.createElement("street");
			property.setTextContent(invoice.getRenter().getAddress().getStreetLine());
			renter.appendChild(property);
			property = doc.createElement("city");
			property.setTextContent(invoice.getRenter().getAddress().getCityLine());
			renter.appendChild(property);
			property = doc.createElement("country");
			property.setTextContent(Language.getCountryByCode(invoice.getRenter().getAddress().getCountry()));
			renter.appendChild(property);
			property = doc.createElement("telephone");
			property.setTextContent(invoice.getRenter().getTelephone());
			renter.appendChild(property);
			property = doc.createElement("cellphone");
			property.setTextContent(invoice.getRenter().getCellphone());
			renter.appendChild(property);
			property = doc.createElement("email");
			property.setTextContent(invoice.getRenter().getEmail());
			renter.appendChild(property);
			root.appendChild(renter);

			//invoice date info
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Calendar calendar = Calendar.getInstance();
			calendar.set((Integer) cbbYearFrom.getSelectedItem(), cbbMonthFrom.getSelectedIndex(), 1);
			Date start = calendar.getTime();

			calendar.set((Integer) cbbYearTo.getSelectedItem(), cbbMonthTo.getSelectedIndex(), 1);
			calendar.add(Calendar.MONTH, 1);
			calendar.add(Calendar.DATE, -1);
			Date end = calendar.getTime();

			Element dateStart = doc.createElement("invoice_start_date");
			dateStart.setTextContent(formatter.format(start));
			root.appendChild(dateStart);

			Element dateEnd = doc.createElement("invoice_end_date");
			dateEnd.setTextContent(formatter.format(end));
			root.appendChild(dateEnd);

			//set up a transformer
			TransformerFactory transfac = TransformerFactory.newInstance();
			Transformer trans = transfac.newTransformer();
			trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			trans.setOutputProperty(OutputKeys.INDENT, "yes");

			//create string from xml tree
			StringWriter sw = new StringWriter();
			StreamResult result = new StreamResult(sw);
			DOMSource source = new DOMSource(doc);
			trans.transform(source, result);

			Logger.logger.info("xml stored in database: \n" + sw.toString() + "\n\n");
			return sw.toString();
		} catch (Exception ex) {
			Logger.logger.error("Failed to generate XML file: " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
			throw new XmlGenerationException("Failed to generate XML file:\n" + ex.getMessage());
		}
	}

	public void fillInfo() throws ParserConfigurationException, SAXException {
		Calendar c = Calendar.getInstance();
		c.setTime(invoice.getStart());
		cbbMonthFrom.setSelectedIndex(c.get(Calendar.MONTH));
		cbbYearFrom.setSelectedItem(c.get(Calendar.YEAR));
		System.out.println("date end: " + invoice.getEnd());
		c.setTime(invoice.getEnd());
		cbbMonthTo.setSelectedIndex(c.get(Calendar.MONTH));
		cbbYearTo.setSelectedItem(c.get(Calendar.YEAR));


		dch.setDate(invoice.getSendingDate());

		for (InvoiceItem item : invoice.getItems()) {
			tmInvoices.addRow(item.toObjects());
		}
	}

	private boolean checkInput() {
		if (((Integer) cbbYearFrom.getSelectedItem()).intValue() < ((Integer) cbbYearTo.getSelectedItem()).intValue()
				|| ((Integer) cbbYearTo.getSelectedItem()).equals((Integer) cbbYearFrom.getSelectedItem())
				&& (cbbMonthFrom.getSelectedIndex() + 1) <= (cbbMonthTo.getSelectedIndex() + 1)) {
			return true;
		} else {
			Logger.logger.error(Language.getString("errDates") + " in checkInput of invoice Dialog");
			JOptionPane.showMessageDialog(instance, Language.getString("errDates"), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	public int getRenterId() {
		return invoice.getRenter().getId();
	}

	public boolean isNewInvoice() {
		return newInvoice;
	}

	public DefaultTableModel getTmInvoices() {
		return tmInvoices;
	}
}

