/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.calendartab;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 *
 * @author Jelle
 */
public class CalendarSquare extends JLabel {
    private boolean odd;

    public CalendarSquare(int dayOfMonth, boolean currentMonth, boolean odd) {
        this(dayOfMonth,odd);
        if(!currentMonth) {
            this.setForeground(Color.white);
        }
    }

    public CalendarSquare(int dayOfMonth, boolean odd) {
        setText(String.valueOf(dayOfMonth));
        setOpaque(true);
        setHorizontalAlignment(JLabel.RIGHT);
        setVerticalAlignment(JLabel.TOP);
        if (odd) {
            setBackground(new Color(150, 150, 150));
        } else {
            setBackground(Color.LIGHT_GRAY);
        }

        //setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    }
}
