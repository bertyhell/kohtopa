package gui.actions;

import gui.calendartab.AddTask;
import gui.calendartab.CalendarModel;
import gui.calendartab.TaskDialog;
import java.awt.event.ActionEvent;
import java.util.Date;
import javax.swing.Icon;

/**
 *
 * @author bert
 */
public class TaskAddAction extends AbstractIconAction {

    public TaskAddAction(String id, Icon img) {
	super(id, img);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Date date = TaskDialog.getDate();
        CalendarModel model = TaskDialog.getModel();

        AddTask.getInstance(model,date).setVisible(true);

    }
}