package gui.actions;

import gui.calendartab.ChangeTask;
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

        ChangeTask.getInstance(ChangeTask.ADD,model,date).setVisible(true);

    }
}