/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package floorallocationmodule.listeners;

import floorallocationmodule.view.FloorImage;
import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

/**
 *
 * @author Ruben
 */
public class CustomKeyDispatcher implements KeyEventDispatcher {

    private FloorImage floorImage;

    public CustomKeyDispatcher(FloorImage floorImage) {
        this.floorImage = floorImage;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        if (e.getID() == KeyEvent.KEY_PRESSED) {

        } else if (e.getID() == KeyEvent.KEY_RELEASED) {
            if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                floorImage.getFloorContent().removeSelected();
            }
        } else if (e.getID() == KeyEvent.KEY_TYPED) {

        }
        return false;
    }

}

