package gui.actions;

import Language.Language;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JOptionPane;

public class AbstractIconAction extends AbstractAction {

    public AbstractIconAction(String id, Icon img) {
	super(Language.getString(id), img);
	super.putValue("SHORT_DESCRIPTION", Language.getString(id));
    }

    public void actionPerformed(ActionEvent e) {
	JOptionPane.showMessageDialog(null, "Not yet implemented", "implement error", JOptionPane.ERROR_MESSAGE);
    }
}
