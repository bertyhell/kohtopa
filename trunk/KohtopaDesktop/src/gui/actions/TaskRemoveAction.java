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
public class TaskRemoveAction extends AbstractIconAction {

    public TaskRemoveAction(String id, Icon img) {
	super(id, img);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CalendarModel model = TaskDialog.getModel();
        model.removeTask(TaskDialog.getSelectedTask());
        TaskDialog.update();
    }
}