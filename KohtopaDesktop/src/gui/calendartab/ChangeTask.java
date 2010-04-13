/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.calendartab;

import Resources.RelativeLayout;
import data.entities.Task;
import gui.Main;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DateEditor;
import javax.swing.JTextArea;
import javax.swing.SpinnerDateModel;

/**
 *
 * @author jelle
 */
public class ChangeTask extends JDialog {
    public static final String EDIT = "taskEdit";
    public static final String ADD = "taskAdd";
    public static final String REMOVE = "taskRemove";

    private static ChangeTask instance = new ChangeTask();
    private SpinnerDateModel sdm;
    private JTextArea description;
    private CalendarModel model;
    private Date day;

    //private JButton submitButton;
    private static JButton addButton;
    private static JButton editButton;

    /**
     * Returns the instance of the dialog
     * @param model the model where the task will be added
     * @param day the day the task needs to be added, if this is null it will be
     *        the day the model is set to
     */
    public static ChangeTask getInstance(String type, CalendarModel model, Date day) {
        instance.model = model;
        instance.day = day;

        //instance.submitButton.setText(Language.Language.getString(type));

        instance.setTitle(Language.Language.getString(type));
        if(type.equals(EDIT) && TaskDialog.getSelectedTask() != null) {
            instance.description.setText(TaskDialog.getSelectedTask().getDescription());
            instance.sdm.setValue(TaskDialog.getSelectedTask().getDate());
            editButton.setVisible(true);
            addButton.setVisible(false);
        } else {
            instance.description.setText("");
            instance.sdm.setValue(day);
            addButton.setVisible(true);
            editButton.setVisible(false);
        }
        return instance;
    }


    private ChangeTask() {
        super(Main.getInstance(),true);

        JPanel panel = new JPanel();
        panel.setLayout(new RelativeLayout(RelativeLayout.Y_AXIS));

        JPanel spinnerPanel = new JPanel();

        JSpinner spinner = new JSpinner();

        JSpinner sp = new JSpinner();
        Calendar c = Calendar.getInstance();
        c.set(1949, 11, 1);
        Date start = c.getTime();
        c.set(2100, 11, 1);
        Date end = c.getTime();


        sdm = new SpinnerDateModel(TaskDialog.getDate(), start, end, Calendar.DATE);

        
        spinner.setModel(sdm);

        DateEditor e = new DateEditor(spinner, "HH:mm dd/MM/yyyy");
        spinner.setEditor(e);

        spinnerPanel.add(new JLabel("Time: ",JLabel.LEFT));
        spinnerPanel.add(spinner);

        JPanel descriptionPanel = new JPanel();
        description = new JTextArea();
        description.setPreferredSize(new Dimension(200,200));
        description.setLineWrap(true);



        descriptionPanel.add(new JLabel("Description: ",JLabel.LEFT));
        descriptionPanel.add(new JScrollPane(description));


        addButton = new JButton(Language.Language.getString("taskAdd"));
        addButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
//                Calendar c = model.getCalendar();
//                c.setTime(sdm.getDate());
                //System.out.println(c.get(Calendar.DATE)+"/"+c.get(Calendar.MONTH));
                model.addTask(new Task(description.getText(),sdm.getDate()));
                instance.setVisible(false);
                TaskDialog.update();
            }
        });

        editButton = new JButton(Language.Language.getString("taskEdit"));
        editButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if(TaskDialog.getSelectedTask() != null)
                    model.updateTask(TaskDialog.getSelectedTask(),new Task(description.getText(),sdm.getDate()));
                instance.setVisible(false);
                TaskDialog.update();
            }
        });





        panel.add(spinnerPanel);
        panel.add(descriptionPanel);
        panel.add(addButton);
        panel.add(editButton);

        
        setContentPane(panel);

        pack();
        this.setResizable(false);
        this.setLocationRelativeTo(Main.getInstance());

    }
}
