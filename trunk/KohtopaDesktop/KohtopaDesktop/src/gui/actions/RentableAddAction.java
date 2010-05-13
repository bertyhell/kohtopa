package gui.actions;

import Exceptions.WrongNumberOfSelectedItemsException;
import Language.Language;
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
public class RentableAddAction extends AbstractIconAction {

	public RentableAddAction(String id, Icon img) {
		super(id, img);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			new RentableDialog(null, ((IRentableListContainer) ((JActionButton)e.getSource()).getRoot()).getBuildingId(), true).setVisible(true); //TODO show what building your adding the rentable to
		} catch (WrongNumberOfSelectedItemsException ex) {
			JOptionPane.showMessageDialog(Main.getInstance(), "please select exacly 1 rentable to edit", Language.getString("error"), JOptionPane.ERROR_MESSAGE);
			//TODO main.getinstance isn't really correct
		}
	}
}
