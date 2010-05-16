package gui.invoicestab;

import data.entities.Invoice;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class InvoiceCellRenderer implements ListCellRenderer {

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		InvoiceListPanel pnlInvoice = new InvoiceListPanel((Invoice) list.getModel().getElementAt(index));
		if (isSelected) {
			pnlInvoice.setBgColor(new Color(150, 150, 150));
		} else {
			if (index % 2 == 0) {
				pnlInvoice.setBgColor(Color.LIGHT_GRAY);
			} else {
				pnlInvoice.setBgColor(new Color(170, 170, 170));
			}
		}
		return pnlInvoice;
	}
}
