package gui.actions;

import Language.Language;
import data.entities.Rentable;
import gui.JActionButton;
import gui.Logger;
import gui.Main;
import gui.interfaces.IRentableListContainer;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author bert
 */
public class RentableRemoveAction extends AbstractIconAction {

	public RentableRemoveAction(String id, Icon img) {
		super(id, img);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		IRentableListContainer root = (IRentableListContainer)((JActionButton)e.getSource()).getRoot();
		Vector<Integer> selected = new Vector<Integer>();
		for(Object rentable : root.getSelectedRentables()){
			selected.add(((Rentable)rentable).getId());
		}
		if(JOptionPane.showConfirmDialog(
				null,
				Language.getString("confirmDelete") + " " + selected.size() + " " + Language.getString("rentable_s"), //TODO 000 change rentable(s) to rentable if 1 else rentables
				Language.getString("confirm"),
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
			try {
				//delete images
				Main.getDataObject().deleteSelectedRentables(selected);
				JOptionPane.showMessageDialog((Component)root, Language.getString("RentableSuccesDelete"), Language.getString("succes"), JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/images/succes_48.png")));
				Main.updateAddRemoveList();
			} catch (SQLException ex) {
				Logger.logger.error("Exception in RentableRemoveAction preformed " + ex.getMessage());
				Logger.logger.debug("StackTrace: ", ex);
				JOptionPane.showMessageDialog(Main.getInstance(), ex.getMessage(), "???", JOptionPane.ERROR_MESSAGE);
			}
		}

	}
}
