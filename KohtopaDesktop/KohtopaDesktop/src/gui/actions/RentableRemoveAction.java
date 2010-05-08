package gui.actions;

import Language.Language;
import data.entities.Rentable;
import gui.JActionButton;
import gui.Main;
import gui.addremovetab.IRentableListContainer;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.Icon;
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
				Language.getString("confirmDelete") + " " + selected.size() + " " + Language.getString("rentables"), //TODO p000 change rentable(s) to rentable if 1 else rentables
				Language.getString("confirm"),
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
			try {
				//delete images
				Main.getDataObject().deleteSelectedRentables(selected);
			} catch (SQLException ex) {
				JOptionPane.showMessageDialog(Main.getInstance(), ex.getMessage(), Language.getString("error"), JOptionPane.ERROR_MESSAGE);
			}
		}

	}
}
