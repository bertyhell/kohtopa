package gui.actions;

import Language.Language;
import data.entities.Rentable;
import gui.JActionButton;
import gui.Main;
import gui.interfaces.IRentableListContainer;
import gui.addremovetab.RentableDialog;
import java.awt.event.ActionEvent;
import javax.swing.Icon;
import javax.swing.JComponent;
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
		Object[] selected = ((IRentableListContainer)((JActionButton)e.getSource()).getRoot()).getSelectedRentables();
		if (selected.length == 1) {
			new RentableDialog(Main.getInstance(), ((Rentable)selected[0]).getId(), false).setVisible(true);
			//TODO getinstance isn't correct cause it can also be a building dialog, how to fix?
		} else {
			JOptionPane.showMessageDialog(Main.getInstance(), "please select exacly 1 rentable to edit", Language.getString("error"), JOptionPane.ERROR_MESSAGE);

		}


	}
}
