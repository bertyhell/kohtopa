
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
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
            //BufferedImage bi = ImageIO.read(new File("menu.jpg"));
            //DataConnector.addPicture(1, bi);
            ArrayList<Integer> ids = DataConnector.getPictureIds(1);
            int id = ids.get(0);
            BufferedImage bi = DataConnector.getPicture(id);            
            ImageIO.write(bi, "jpg", new File("testMenu.jpg"));
        }catch(Exception exc){
            System.out.println(exc.getMessage());
        }

    }
}
