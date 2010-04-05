package gui.actions;

import gui.Main;
import gui.addremovetab.RentableDialog;
import java.awt.event.ActionEvent;
import javax.swing.Icon;

/**
 *
 * @author bert
 */
public class RentableEditAction extends AbstractIconAction {

    public RentableEditAction(String id, Icon img) {
	super(id, img);
    }

    @Override
    public void actionPerformed(ActionEvent e) { //TODO if no selected > do nothing (nullpointerexception)
	new RentableDialog(Main.getInstance(), Main.getDataObject().getSelectedRentableId(), false).setVisible(true);
	//TODO replce main with e.source or something like that
    }
}
