/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.calendartab;

import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Jelle
 */
public class CalendarModel extends GregorianCalendar implements ChangeListener {
    CalendarPanel pane;
    
    protected CalendarModel(int year, int month, CalendarPanel pane) {
        super(year, month, 1);
        this.pane = pane;
    }

    public void setDate(int year, int month) {
        this.set(CalendarModel.YEAR, year);
        this.set(CalendarModel.MONTH, month);
        pane.updatePage();
    }

    public void changeDate(int nYears, int nMonths) {
        this.add(CalendarModel.YEAR, nYears);
        this.add(CalendarModel.MONTH, nMonths);
        pane.updatePage();
    }

    public Calendar getCalendar() {
        Calendar c = Calendar.getInstance();
        c.setTime(this.getTime());
        return c;
    }

    public void stateChanged(ChangeEvent e) {
        JSpinner s = (JSpinner) e.getSource();
        SpinnerDateModel sModel = (SpinnerDateModel)s.getModel();

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(sModel.getDate().getTime());
        setDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH));
        
    }



}
