package gui.actions;

import Language.Language;
import gui.Main;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

public class PictureRemoveAction extends AbstractIconAction {

	public PictureRemoveAction(String id, Icon img) {
		super(id, img);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//TODO show message only if settings are true
		if (JOptionPane.showConfirmDialog(((JComponent) e.getSource()).getRootPane(), Language.getString("confirmDeletePictures"), Language.getString("confirm"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
			System.out.println("to be implemented");
//			try {
//				Main.getDataObject().deleteSelectedPictures(Main.getFocusedDialog().getSelectedPictures());
//			Main.getFocusedDialog().UpdatePictures();
//			} catch (SQLException ex) {
//				JOptionPane.showMessageDialog(Main.getInstance(), Language.getString("errInSql") + "\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//			}
		}
	}
}
