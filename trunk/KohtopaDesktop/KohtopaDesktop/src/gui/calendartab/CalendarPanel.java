/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.calendartab;

import data.entities.Task;
import gui.Main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Vector;
import java.util.Calendar;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DateEditor;
import javax.swing.SpinnerDateModel;

/**
 * Panel that represents the calendar
 * @author Jelle
 */
public class CalendarPanel extends JPanel implements MouseWheelListener {

    private JPanel calendar;
    private CalendarModel model;
    private boolean nextSquareOdd;
    private SpinnerDateModel spinnerModel;

    /**
     * Creates a new CalendarPanel of the current month
     */
    public CalendarPanel() {
        this(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH));
    }

    /**
     * Creates a new CalendarPanel of the month asked
     * @param year year of Calendar
     * @param month month of calendar
     */
    public CalendarPanel(int year, int month) {
        nextSquareOdd = true;

        this.model = new CalendarModel(year, month, this);

        // set model for taskDialog
        TaskDialog.setModel(model);

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        // fill up panel
        this.setLayout(new BorderLayout());

        JPanel calendarPanel = new JPanel(new BorderLayout());

        // add day tags
        JPanel dayPanel = new JPanel(new GridLayout(0, 7));
        calendarPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        for (int i = 0; i < 7; i++) {
            JLabel l = new JLabel(Language.Language.getDaysOfWeek()[i]);
            l.setOpaque(true);
            l.setBackground(Color.DARK_GRAY);
            l.setForeground(Color.white);

            l.setFont(l.getFont().deriveFont(18.0f));
            l.setHorizontalAlignment(JLabel.CENTER);
            l.setVerticalAlignment(JLabel.BOTTOM);
            dayPanel.add(l);

            //nextSquareOdd = !nextSquareOdd;
        }

        calendar = new JPanel(new GridLayout(0, 7));

        calendarPanel.add(dayPanel, BorderLayout.NORTH);
        calendarPanel.add(calendar, BorderLayout.CENTER);


        this.updatePage();


        // set spinner to change calendar date
        JSpinner sp = new JSpinner();
        Calendar c = Calendar.getInstance();
        c.set(1949, 11, 1);
        Date start = c.getTime();
        c.set(2100, 11, 1);
        Date end = c.getTime();


        spinnerModel = new SpinnerDateModel(model.getTime(), start, end, Calendar.DATE);

        sp.setModel(spinnerModel);
        DateEditor e = new DateEditor(sp, "MMMMM yyyy");
        sp.setEditor(e);
        sp.setFont(sp.getFont().deriveFont(18.0f));
        sp.addChangeListener(model);
        //sp.setValue(model.getTimeInMillis());

        this.add(sp, BorderLayout.NORTH);
        this.add(calendarPanel, BorderLayout.CENTER);

        this.addMouseWheelListener(this);


        Main.getAction("taskEdit").setEnabled(false);
        Main.getAction("taskRemove").setEnabled(false);
    }

    /**
     * Getter for the model
     * @return the model
     */
    public CalendarModel getModel() {
        return model;
    }

    /**
     * Getter for a specific square, indexed starting from zero
     * @param index the square needed
     * @return the square
     */
    public CalendarSquare getSquare(int index) {
        return (CalendarSquare) calendar.getComponent(index + 7);
    }

    /**
     * Setter for the date of the calendar
     * @param year new year
     * @param month new month
     */
    public void setDate(int year, int month) {
        model.setDate(year, month);
    }

    /**
     * Changes the date with certain amount of years/months
     * @param nYears the amount of years to add (can be negative)
     * @param nMonths the amount of months to add (can be negative)
     */
    public void changeDate(int nYears, int nMonths) {
        model.changeDate(nYears, nMonths);
    }

    /**
     * Changes a square
     * @param place the number of the square
     * @param number the new number
     * @param white is it white(is it current month)
     */
    public void changeSquare(int place, int number, boolean white) {
        JLabel l = (JLabel) calendar.getComponent(7 + place);
        l.setText(String.valueOf(number));
        if (white) {
            l.setForeground(Color.WHITE);
        } else {
            l.setForeground(Color.BLACK);
        }
    }

    /**
     * Updates the calendar, call this if you change stuff manually
     */
    public void updatePage() {
        // if calendar not yet initialized, do nothing
        if(calendar == null)
            return;

        // clear the calendar pane
        calendar.removeAll();
        calendar.updateUI();

        calendar.setLayout(new GridLayout(0, 7));


        Calendar cal = model.getCalendar();

        // get month
        int currMonth = cal.get(CalendarModel.MONTH);

        //cal.set(cal.get(CalendarModel.YEAR), cal.get(CalendarModel.MONTH), 1);

        cal.set(Calendar.HOUR,0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        // change date so that calendar starts on monday(roll back one day by default so we
        // get more symmetry when month starts on monday
        if (cal.get(CalendarModel.DAY_OF_WEEK) == CalendarModel.MONDAY) {
            cal.add(CalendarModel.DAY_OF_YEAR, -7);
        } else {
            while(cal.get(CalendarModel.DAY_OF_WEEK) != CalendarModel.MONDAY)
                cal.add(CalendarModel.DAY_OF_WEEK,-1);
        }


        // other rows
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                Vector<Task> tasks = model.getTasksForDay(cal.getTime());

                if (cal.get(CalendarModel.MONTH) == currMonth) {
                    CalendarSquare cs = new CalendarSquare(cal.getTime(), nextSquareOdd);
                    cs.setTasks(tasks);
                    calendar.add(cs);
                } else {
                    CalendarSquare cs = new CalendarSquare(cal.getTime(), false, nextSquareOdd);
                    cs.setTasks(tasks);
                    calendar.add(cs);
                    //calendar.add(new CalendarSquare(cal.getTime(),false,nextSquareOdd));
                }
                cal.add(CalendarModel.DATE, 1);

                nextSquareOdd = !nextSquareOdd;
            }
        }



    }

    /**
     * Listens to mouse scrolls, changes months
     * @param e
     */
    public void mouseWheelMoved(MouseWheelEvent e) {
        int n = e.getWheelRotation();

        Calendar c = model.getCalendar();
        c.setTime(spinnerModel.getDate());
        c.add(Calendar.MONTH, n);

        spinnerModel.setValue(c.getTime());

    }
}
