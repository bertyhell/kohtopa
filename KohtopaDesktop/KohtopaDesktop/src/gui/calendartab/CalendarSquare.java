/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.calendartab;

import data.entities.Task;
import gui.Logger;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;
import java.util.Calendar;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Represents a square in the calendar
 * @author Jelle
 */
public class CalendarSquare extends JPanel implements MouseListener {

    private Color backColor;
    private Color foreColor;
    private Date day;
    private JLabel lblTasks;
    private JLabel lblDay;

    /**
     * Creates a new calendarSquare
     * @param day the day of the square
     * @param currentMonth is the square of the current month or the previous/next one
     * @param odd is the square odd or even
     */
    public CalendarSquare(Date day, boolean currentMonth, boolean odd) {
        if (!currentMonth) {
            foreColor = Color.WHITE;
        } else {
            foreColor = Color.BLACK;
        }

        this.day = day;


        Calendar c = Calendar.getInstance();
        c.setTime(day);
        this.setLayout(new BorderLayout());
		Box boxVertical = Box.createVerticalBox();
		this.add(boxVertical,BorderLayout.CENTER);

        lblDay = new JLabel();
        lblDay.setForeground(foreColor);

        lblTasks = new JLabel();
        lblTasks.setForeground(foreColor);


        lblDay.setHorizontalAlignment(JLabel.LEFT);
        lblDay.setVerticalAlignment(JLabel.TOP);

        lblDay.setText(String.valueOf(c.get(Calendar.DAY_OF_MONTH)));

        lblDay.setHorizontalAlignment(JLabel.RIGHT);
        lblDay.setVerticalAlignment(JLabel.TOP);

        if (odd) {
            backColor = new Color(150, 150, 150);
        } else {
            backColor = Color.LIGHT_GRAY;
        }

        boxVertical.add(lblDay);

        boxVertical.add(lblTasks);

        setOpaque(true);
        setBackground(backColor);

        //setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.addMouseListener(this);
    }

    /**
     * Creates a square, belonging to current month
     * @param day the day of the square
     * @param odd odd/even square
     */
    public CalendarSquare(Date day, boolean odd) {
        this(day, true, odd);
    }

    /**
     * Setter for the tasks for the square
     * @param tasks the tasks to set
     */
    public void setTasks(Vector<Task> tasks) {
        if (tasks == null) {
            return;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(day);

        if (tasks.size() == 0) {
            return;
        }

        StringBuffer sb = new StringBuffer();
        if (tasks.get(0).toString().length() < 15) {
            sb.append("<html>" + tasks.get(0));
        } else {
            sb.append("<html>" + tasks.get(0).toString().substring(0, 15) + "...");
        }

        int i = 1;
        while (i < 2 && i < tasks.size()) {
            sb.append("<br />");
            if (tasks.get(i).toString().length() < 15) {
                sb.append(tasks.get(i));
            } else {
                sb.append(tasks.get(i).toString().substring(0, 15) + "...");
            }
            i++;
        }
        if (i > 2) {
            sb.append("<br />...");
        }
        sb.append("</html>");


        lblTasks.setText(sb.toString());
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    /**
     * Listens to the mouse released, if it is still within the square, open the
     * taskDialog
     * @param e
     */
    public void mouseReleased(MouseEvent e) {
        try {
            if (this.contains(e.getPoint())) {
                TaskDialog f = TaskDialog.getInstance(day);
                f.setVisible(true);
            }
        } catch (IllegalAccessException ex) {
			Logger.logger.error("IllegalAccessException in mouseReleased in CalendarSquare: " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
        }
    }

    /**
     * Upon entering, change color
     * @param e
     */
    public void mouseEntered(MouseEvent e) {
        setBackground(Color.DARK_GRAY);
        lblDay.setForeground(Color.white);
        lblTasks.setForeground(Color.white);
    }

    /**
     * Upon leaving, change color
     * @param e
     */
    public void mouseExited(MouseEvent e) {
        setBackground(backColor);
        lblDay.setForeground(foreColor);
        lblTasks.setForeground(foreColor);
    }
}
