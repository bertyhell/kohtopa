package gui.actions;

import Language.Language;
import data.ProgramSettings;
import gui.Logger;
import gui.Main;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Bert Verhelst <verhelst_bert@hotmail.com>
 */
public class RestartAction extends AbstractAction {

	public RestartAction(ImageIcon img) {
		super("Apply",img);
	}

	public void actionPerformed(ActionEvent ae) {
		Logger.logger.info("Restart action triggert");
		if (!Main.getSelectedLanguage().equals(Language.getLanguage())) {
			//language selection changed
			Object[] options = {"Now", "Later", "Cancel"};
			int choise = JOptionPane.showOptionDialog(
					Main.getInstance(),
					"Program needs to restart for changes to take effect. Restart now?",
					Language.getString("confirm"),
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					options,
					options[0]);
			if (choise != 2) {
				//change language
				ProgramSettings.setLanguage(Main.getSelectedLanguage());
				if (choise == 0) {
					Logger.logger.info("option 0: restart now");
					//restart now
					try {
						Main.getInstance().dispose();
						System.exit(100);
					} catch (Exception ex) {
						Logger.logger.error("Exception during restart attempt " + ex.getMessage());
						Logger.logger.debug("StackTrace: ", ex);
					}
				}else{
					Logger.logger.info("option 1: restart later");
				}
			}else{
				Logger.logger.info("option 2: cancel");
				JOptionPane.showMessageDialog(Main.getInstance(), "Language already applied", Language.getString("error"), JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
}
