/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package floorallocationmodule.listeners;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Ruben
 */
public class ImageFileFilter extends FileFilter {

    public ImageFileFilter() {
    }

    public boolean accept (File file) {
        String fileName = file.getName().toLowerCase();
        return ((fileName.endsWith(".gif")) || (fileName.endsWith(".png")) || (fileName.endsWith(".jpg")));
    }

    public String getDescription() {
            return "*.gif, *.png, *.jpg";
    }

}
