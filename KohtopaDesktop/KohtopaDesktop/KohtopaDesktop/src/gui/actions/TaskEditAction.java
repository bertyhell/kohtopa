package gui.actions;

import gui.calendartab.CalendarModel;
import gui.calendartab.ChangeTask;
import gui.calendartab.TaskDialog;
import java.awt.event.ActionEvent;
import java.util.Date;
import javax.swing.Icon;

/**
 *
 * @author bert
 */
public class TaskEditAction extends AbstractIconAction {

    public TaskEditAction(String id, Icon img) {
	super(id, img);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Date date = TaskDialog.getDate();
        CalendarModel model = TaskDialog.getModel();

        ChangeTask.getInstance(ChangeTask.EDIT,model,date).setVisible(true);

    }
}