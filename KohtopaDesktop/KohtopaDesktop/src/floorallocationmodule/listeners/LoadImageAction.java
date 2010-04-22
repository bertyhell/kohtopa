/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package floorallocationmodule.listeners;

import floorallocationmodule.model.FloorContent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Ruben
 */
public class LoadImageAction implements ActionListener {

    private FloorContent floorContent;

    public LoadImageAction(FloorContent floorContent) {
        this.floorContent = floorContent;
    }

    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new ImageFileFilter());

        int answer = fileChooser.showOpenDialog(null);
        if (answer == -1) {
            JOptionPane.showMessageDialog(null, "Please make a choice.", "Error", 0);
        } else if (answer == 0) {
            File file = fileChooser.getSelectedFile();

            if (!(fileChooser.getFileFilter().accept(file))) {
                JOptionPane.showMessageDialog(null, "Please make a valid choice.", "Error", 0);
            }

            if (!file.exists()) {
                JOptionPane.showMessageDialog(null, "File doesn't exist.", "Error", 0);
            } else {
                try {
                    floorContent.setImageFile(file);
                } catch (Exception exc) {
                }
            }
        }
    }

}
