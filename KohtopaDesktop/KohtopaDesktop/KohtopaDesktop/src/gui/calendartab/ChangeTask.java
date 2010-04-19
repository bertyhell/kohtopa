/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.calendartab;

import Resources.RelativeLayout;
import data.DataConnector;
import data.ProgramSettings;
import data.entities.Rentable;
import data.entities.Task;
import gui.Layout;
import gui.Main;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DateEditor;
import javax.swing.JTextArea;
import javax.swing.SpinnerDateModel;

/**
 * Class ChangeTask, this is a dialog to change a certain task, is used to add
 * tasks too
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

    private static JButton addButton;
    private static JButton editButton;

    private JComboBox rentablePicker;

    /**
     * Returns the instance of the dialog
     * @param model the model where the task will be added
     * @param day the day the task needs to be added, if this is null it will be
     *        the day the model is set to
     */
    public static ChangeTask getInstance(String type, CalendarModel model, Date day) {
        instance.model = model;

        addButton.setEnabled(!instance.description.getText().isEmpty());
        editButton.setEnabled(!instance.description.getText().isEmpty());

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


    /**
     * Constructor, creates a task
     */
    private ChangeTask() {
        super(Main.getInstance(),true);

        JPanel panel = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
        panel.setLayout(gbl);
        GridBagConstraints gbc = gbl.getConstraints(this);

        //JPanel spinnerPanel = new JPanel();

        JSpinner spinner = new JSpinner();

        Calendar c = Calendar.getInstance();
        c.set(1949, 11, 1);
        Date start = c.getTime();
        c.set(2100, 11, 1);
        Date end = c.getTime();


        sdm = new SpinnerDateModel(TaskDialog.getDate(), start, end, Calendar.DATE);

        
        spinner.setModel(sdm);

        DateEditor e = new DateEditor(spinner, "HH:mm dd/MM/yyyy");
        spinner.setEditor(e);


        //GridBagConstraints gbc, int x, int y, int w, int h, int wx, int wy, int fill, int anchor) {

        Layout.buildConstraints(gbc, 0, 0, 1, 1, 10, 10, GridBagConstraints.BOTH, GridBagConstraints.WEST);
        panel.add(new JLabel("Time: ",JLabel.LEFT),gbc);
        
        Layout.buildConstraints(gbc, 1, 0, 1, 1, 20, 10, GridBagConstraints.BOTH, GridBagConstraints.WEST);
        panel.add(spinner,gbc);



        addButton = new JButton(Language.Language.getString("taskAdd"));
        addButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                //TODO: sdm getDate for end, should change but i have no clue what it should be
                model.addTask(new Task(0,((Rentable)rentablePicker.getSelectedItem()).getId(),
                        description.getText(),sdm.getDate(),sdm.getDate(),0));
                instance.setVisible(false);
                TaskDialog.update();
            }
        });

        editButton = new JButton(Language.Language.getString("taskEdit"));
        editButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if(TaskDialog.getSelectedTask() != null)
                    model.updateTask(TaskDialog.getSelectedTask(),
                           new Task(0,((Rentable)rentablePicker.getSelectedItem()).getId(),
                            description.getText(),sdm.getDate(),sdm.getDate(),0));
                instance.setVisible(false);
                TaskDialog.update();
            }
        });


        // add a combobox to pick a rentable
        //JPanel rentablePanel = new JPanel();

        // add rentables to rentablePicker
        ArrayList<Rentable> rentables = DataConnector.getRentablesFromUser(ProgramSettings.getUserID());
        rentablePicker = new JComboBox();
        for(Rentable r:rentables)
            rentablePicker.addItem(r);

        Layout.buildConstraints(gbc, 0, 1, 1, 1, 10, 10, GridBagConstraints.BOTH, GridBagConstraints.EAST);
        panel.add(new JLabel("Rentable: ",JLabel.LEFT),gbc);
        Layout.buildConstraints(gbc, 1, 1, 1, 1, 20, 10, GridBagConstraints.BOTH, GridBagConstraints.WEST);
        panel.add(rentablePicker,gbc);

        //JPanel descriptionPanel = new JPanel();
        description = new JTextArea();
        description.addKeyListener(new KeyListener() {

            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
                addButton.setEnabled(!description.getText().isEmpty());
                editButton.setEnabled(!description.getText().isEmpty());
            }
        });
        description.setPreferredSize(new Dimension(200,200));
        description.setLineWrap(true);



        Layout.buildConstraints(gbc, 0, 2, 1, 1, 10, 10, GridBagConstraints.BOTH, GridBagConstraints.EAST);
        panel.add(new JLabel("Description: ",JLabel.LEFT),gbc);
        Layout.buildConstraints(gbc, 1, 2, 1, 1, 20, 50, GridBagConstraints.BOTH, GridBagConstraints.WEST);
        panel.add(new JScrollPane(description),gbc);

        //panel.add(spinnerPanel);
        //panel.add(rentablePanel);
        //panel.add(descriptionPanel);
        Layout.buildConstraints(gbc, 0, 3, 2, 1, 30, 10, GridBagConstraints.NONE, GridBagConstraints.CENTER);
        panel.add(addButton,gbc);
        panel.add(editButton,gbc);


        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(panel);

        pack();
        //this.setResizable(false);
        this.setLocationRelativeTo(Main.getInstance());

    }
}
