package gui.actions;

import gui.AddRemoveTab.BuildingDialog;
import gui.Main;
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
		BuildingDialog.show(Main.getInstance(), Main.getDataObject().getSelectedRentableId(), false); //TODO replce main with e.source or something like that
	}
}