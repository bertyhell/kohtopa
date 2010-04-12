/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.calendartab;

import data.entities.Task;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DateEditor;
import javax.swing.SpinnerDateModel;

/**
 *
 * @author Jelle
 */
public class CalendarPanel extends JPanel implements MouseWheelListener {

    private final String[] days = {"Mo", "Tu", "We", "Th", "Fr", "Sa", "Su"};
    private JPanel calendar;
    private CalendarModel model;
    private boolean nextSquareOdd;
    private SpinnerDateModel spinnerModel;

    public CalendarPanel() {
        this(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH));
        //System.out.println(Calendar.getInstance().getTimeInMillis()-System.currentTimeMillis());
    }

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
            JLabel l = new JLabel(days[i]);
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
    }

    public CalendarModel getModel() {
        return model;
    }

    public CalendarSquare getSquare(int index) {
        return (CalendarSquare) calendar.getComponent(index + 7);
    }

    public void setDate(int year, int month) {
        model.setDate(year, month);
    }

    public void changeDate(int nYears, int nMonths) {
        model.changeDate(nYears, nMonths);
    }

    public void changeSquare(int place, int number, boolean white) {
        JLabel l = (JLabel) calendar.getComponent(7 + place);
        l.setText(String.valueOf(number));
        if (white) {
            l.setForeground(Color.white);
        } else {
            l.setForeground(Color.black);
        }
    }

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
                ArrayList<Task> tasks = model.getTasksForDay(cal.getTime());

                if (cal.get(CalendarModel.MONTH) == currMonth) {
                    CalendarSquare cs = new CalendarSquare(model, cal.getTime(), nextSquareOdd);
                    cs.setTasks(tasks);
                    calendar.add(cs);
                } else {
                    CalendarSquare cs = new CalendarSquare(model, cal.getTime(), false, nextSquareOdd);
                    cs.setTasks(tasks);
                    calendar.add(cs);
                    //calendar.add(new CalendarSquare(cal.getTime(),false,nextSquareOdd));
                }
                cal.add(CalendarModel.DATE, 1);

                nextSquareOdd = !nextSquareOdd;
            }
        }



    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        int n = e.getWheelRotation();

        Calendar c = model.getCalendar();
        c.setTime(spinnerModel.getDate());
        c.add(Calendar.MONTH, n);

        spinnerModel.setValue(c.getTime());

    }
}
