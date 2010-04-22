package gui.actions;

import Language.Language;
import gui.Main;
import gui.addremovetab.RentableDialog;
import java.awt.event.ActionEvent;
import javax.swing.Icon;
import javax.swing.JOptionPane;

/**
 *
 * @author bert
 */
public class RentableEditAction extends AbstractIconAction {

    public RentableEditAction(String id, Icon img) {
	super(id, img);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
		if(Main.getDataObject().isRentableSelected()){
new RentableDialog(Main.getInstance().getRootPane(), Main.getDataObject().getSelectedRentableId(), false).setVisible(true);
		}else{
			JOptionPane.showMessageDialog(Main.getInstance(), "please select a rentable first \n", Language.getString("error"), JOptionPane.ERROR_MESSAGE);

		}
	

    }
}
