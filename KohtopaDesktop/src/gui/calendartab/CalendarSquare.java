/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.calendartab;

import Resources.RelativeLayout;
import data.entities.Task;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Jelle
 */
public class CalendarSquare extends JPanel implements MouseListener {

    private Color backColor;
    private Color foreColor;
    private Date day;
    private JLabel lblTasks;
    private JLabel lblDay;
    private CalendarModel model;

    public CalendarSquare(CalendarModel model, Date day, boolean currentMonth, boolean odd) {
        this.model = model;
        if (!currentMonth) {
            foreColor = Color.white;
        } else {
            foreColor = Color.black;
        }

        this.day = day;


        Calendar c = Calendar.getInstance();
        c.setTime(day);
        RelativeLayout rl = new RelativeLayout(RelativeLayout.Y_AXIS);
        rl.setFill(true);
        this.setLayout(rl);

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

        this.add(lblDay);

        this.add(lblTasks);

        setOpaque(true);
        setBackground(backColor);

        //setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.addMouseListener(this);
    }

    public CalendarSquare(CalendarModel model, Date day, boolean odd) {
        this(model, day, true, odd);
    }

    public void setTasks(ArrayList<Task> tasks) {
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

    public void mouseReleased(MouseEvent e) {
        try {
            if (this.contains(e.getPoint())) {
                TaskDialog f = TaskDialog.getInstance(day);
                f.setVisible(true);
            }
        } catch (IllegalAccessException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void mouseEntered(MouseEvent e) {
        setBackground(Color.BLACK);
        lblDay.setForeground(Color.white);
        lblTasks.setForeground(Color.white);
    }

    public void mouseExited(MouseEvent e) {
        setBackground(backColor);
        lblDay.setForeground(foreColor);
        lblTasks.setForeground(foreColor);
    }
}
