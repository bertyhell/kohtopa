/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package webcammodule;

import java.awt.Window;
import javax.swing.JFrame;

/**
 *
 * @author Ruben
 */
public class WebcamDialog extends JFrame {

    public WebcamDialog(Window parent, int buildingID) {
        setContentPane(new LiveFeedWebcam(buildingID));
        pack();
		setLocationRelativeTo(parent);
    }

}
