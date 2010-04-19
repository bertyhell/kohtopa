package gui.actions;

import gui.calendartab.ChangeTask;
import gui.calendartab.CalendarModel;
import gui.calendartab.TaskDialog;
import java.awt.event.ActionEvent;
import java.util.Date;
import javax.swing.Icon;

/**
 * Action for adding a task to the database
 * @author bert
 */
public class TaskAddAction extends AbstractIconAction {

    public TaskAddAction(String id, Icon img) {
	super(id, img);
    }

    /**
     * Shows the ChangeTask dialog
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Date date = TaskDialog.getDate();
        CalendarModel model = TaskDialog.getModel();

        ChangeTask.getInstance(ChangeTask.ADD,model,date).setVisible(true);

    }
}