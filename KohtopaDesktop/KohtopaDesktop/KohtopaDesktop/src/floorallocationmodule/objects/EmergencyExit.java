/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package floorallocationmodule.objects;

import java.awt.Point;
import javax.swing.ImageIcon;

/**
 *
 * @author Ruben
 */
public class EmergencyExit extends Point {

private boolean selected;

    private final ImageIcon emergencyExitIcon = new ImageIcon(getClass().getResource("/images/exit_23.png"));

    public EmergencyExit() {
    }

    public EmergencyExit(int x, int y) {
        super(x ,y);
        selected = false;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public ImageIcon getEmergencyExitIcon() {
        return emergencyExitIcon;
    }

}