/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package floorallocationmodule.listeners;

import floorallocationmodule.objects.Camera;
import floorallocationmodule.objects.FireExtinguisher;
import floorallocationmodule.view.FloorImage;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputAdapter;

/**
 *
 * @author Ruben
 */
public class FloorImageInputListener extends MouseInputAdapter implements KeyListener {

    private FloorImage floorImage;

    public FloorImageInputListener(FloorImage floorImage) {
        this.floorImage = floorImage;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (floorImage.getFloorContent().isAddFireExtinguisher()) {
            floorImage.getFloorContent().addFireExtinguisher(new FireExtinguisher(e.getX(), e.getY()));
        } else if (floorImage.getFloorContent().isAddCamera()) {
            floorImage.getFloorContent().addCamera(new Camera(e.getX(), e.getY()));
        } else if (floorImage.getFloorContent().isDrawEnabled()) {
            if (floorImage.getFloorContent().hasPoint(e.getX(), e.getY())) {
                floorImage.getFloorContent().finishNamedPolygon();
            } else {
                floorImage.getFloorContent().addPointToQueu(new Point(e.getX(), e.getY()));
            }
        } else {
            // 1. select camera OR
                // 2. select fireExtinguisher OR
                    // 3. select namedPolygon
            if (!floorImage.getFloorContent().selectCamera(e.getX(), e.getY())) {
                if (!floorImage.getFloorContent().selecteFireExtinguisher(e.getX(), e.getY())) {
                    floorImage.getFloorContent().selectNamedPolgyon(e.getX(), e.getY());
                }
            }
        }
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DELETE) {
            floorImage.getFloorContent().removeSelected();
        }
    }

    public void keyTyped(KeyEvent e) {
    }

}
