package gui.actions;

import gui.Main;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import Language.Language;

/**
 *
 * @author bert
 */
public class MessageRemoveAction extends AbstractAction {

    public MessageRemoveAction(Icon img) {
        super(Language.getString("removeMessages"), img);
		this.setEnabled(false);
    }

    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(Main.getInstance(), "Not yet implemented", "implement error", JOptionPane.ERROR_MESSAGE);
    }

	@Override
	public void setEnabled(boolean enable){
		super.setEnabled(enable);
		super.putValue("SHORT_DESCRIPTION", enable?Language.getString("removeMessages"):"");
	}
}
