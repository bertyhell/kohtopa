
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import model.DataConnector;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jannes
 */
public class testDataConnector {

    public static void main(String[] args) {
        try{
            BufferedImage bi = ImageIO.read(new File("menu.jpg"));
            DataConnector.addPicture(1, bi);
        }catch(Exception exc){
            System.out.println(exc.getMessage());
        }
    }
}
