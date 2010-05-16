package gui.invoicestab;

import Language.Language;
import data.entities.Invoice;
import gui.AbstractListPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.Format;
import java.text.SimpleDateFormat;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class InvoiceListPanel extends AbstractListPanel {

	public InvoiceListPanel(Invoice invoice) {
		super(invoice.getId());
		this.setMaximumSize(new Dimension(1000, 50));
		this.setMinimumSize(new Dimension(200, 50));
		this.setPreferredSize(new Dimension(200, 50));


		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		setLayout(gbl);

		//adding information labels

		//preview
		JLabel lblImage = new JLabel(new ImageIcon(getClass().getResource("/images/dummy_invoice_preview.png")));
		buildConstraints(gbc, 0, 0, 1, 3, 5, 30, GridBagConstraints.WEST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblImage, gbc);
		this.add(lblImage);

		//type + description
		Format df = new SimpleDateFormat("dd/MM/yyyy");
		JLabel lblDate = new JLabel(Language.getString(invoice.isSend() ? "beenSend" : "willSendOn") + ": " + df.format(invoice.getSendingDate()));
		lblDate.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		buildConstraints(gbc, 1, 1, 1, 1, 100, 30, GridBagConstraints.EAST, GridBagConstraints.WEST);
		gbl.addLayoutComponent(lblDate, gbc);
		this.add(lblDate);
	}
}
