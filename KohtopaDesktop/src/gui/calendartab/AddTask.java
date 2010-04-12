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
public class AddTask extends JDialog {
    private static AddTask instance = new AddTask();
    private SpinnerDateModel sdm;
    private JTextArea description;
    private CalendarModel model;
    private Date day;

    /**
     * Returns the instance of the dialog
     * @param model the model where the task will be added
     * @param day the day the task needs to be added, if this is null it will be
     *        the day the model is set to
     */
    public static AddTask getInstance(CalendarModel model, Date day) {
        instance.model = model;
        instance.day = day;
        instance.sdm.setValue(day);
        instance.description.setText("");
        return instance;
    }


    private AddTask() {
        super(Main.getInstance(),true);

        this.setTitle(Language.Language.getString("taskAdd"));
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


        JButton submitButton = new JButton(Language.Language.getString("taskAdd"));
        submitButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Calendar c = model.getCalendar();
                c.setTime(sdm.getDate());
                //System.out.println(c.get(Calendar.DATE)+"/"+c.get(Calendar.MONTH));
                model.addTask(new Task(description.getText(),sdm.getDate()));
                instance.setVisible(false);
                TaskDialog.update();
            }
        });

        panel.add(spinnerPanel);
        panel.add(descriptionPanel);
        panel.add(submitButton);

        
        setContentPane(panel);

        pack();
        this.setResizable(false);
        this.setLocationRelativeTo(Main.getInstance());

    }
}
