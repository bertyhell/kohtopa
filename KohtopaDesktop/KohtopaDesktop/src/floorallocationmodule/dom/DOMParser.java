/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package floorallocationmodule.dom;

import floorallocationmodule.model.*;
import floorallocationmodule.objects.Camera;
import floorallocationmodule.objects.EmergencyExit;
import floorallocationmodule.objects.FireExtinguisher;
import floorallocationmodule.objects.NamedPolygon;
import gui.Logger;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.apache.commons.codec.binary.Base64;

/**
 * DOMParser writes important information from floorContent into an XML-file
 * using Document Object Model.
 * @author Ruben
 */
public class DOMParser {

    private FloorContent floorContent;
    private Document document;

    /**
     * Create DOM Document
     */
    public DOMParser() {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
            document = builder.newDocument();
        } catch (Exception ex) {
			Logger.logger.error("Exception in DOMParser " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
        }
    }

    public void setFloorContent(FloorContent floorContent) {
        this.floorContent = floorContent;
    }

    /**
    * Create Object Tree
    */
    public void parse() {
        try {
            // Root
            Element root = document.createElement(DOMConstants.FLOORCONTENT);
            document.appendChild(root);

            // Name
            Element name = document.createElement(DOMConstants.NAME);
            Text txtName = document.createTextNode(floorContent.getFloorName());
            name.appendChild(txtName);
            root.appendChild(name);

            // Dimension
            Element width = document.createElement(DOMConstants.WIDTH);
            Text txtWidth = document.createTextNode(String.valueOf(floorContent.getX()));
            width.appendChild(txtWidth);
            root.appendChild(width);
            Element height = document.createElement(DOMConstants.HEIGHT);
            Text txtHeight = document.createTextNode(String.valueOf(floorContent.getY()));
            height.appendChild(txtHeight);
            root.appendChild(height);

            // Image
            if (floorContent.getImageFile() != null) {
                File imgFile = floorContent.getImageFile();
                BufferedImage img = ImageIO.read(imgFile);
                String imgName = floorContent.getImageFile().getName();

                Element image = document.createElement(DOMConstants.IMAGE);
                Element imageName = document.createElement(DOMConstants.NAME);
                image.appendChild(imageName);
                Text txtImage = document.createTextNode(imgName);
                imageName.appendChild(txtImage);

                // Encode file into a string of bytes
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                ImageIO.write(img, getExtension(imgFile), stream);
                stream.flush();
                Base64 b64 = new Base64();
                String encodedImage = b64.encodeToString(stream.toByteArray());
                stream.close();

                Element imageData = document.createElement(DOMConstants.DATA);
                image.appendChild(imageData);
                Text txtImageData = document.createTextNode(encodedImage);
                imageData.appendChild(txtImageData);
                root.appendChild(image);
            }

            // NamedPolygons
            Element namedPolygons = document.createElement(DOMConstants.NAMEDPOLYGONS);
            root.appendChild(namedPolygons);

            Element namedPolygon;
            Element namedPolygonName;
            Element namedPolygonPoints;
            Element namedPolygonPoint;
            Element namedPolygonPointX;
            Text txtPointX;
            Element namedPolygonPointY;            
            Text txtPointY;
            for (NamedPolygon polygon: floorContent.getNamedPolygons()) {
                namedPolygon = document.createElement(DOMConstants.NAMEDPOLYGON);
                namedPolygons.appendChild(namedPolygon);

                // Name
                namedPolygonName = document.createElement(DOMConstants.NAME);
                namedPolygon.appendChild(namedPolygonName);
                txtName = document.createTextNode(polygon.getName());
                namedPolygonName.appendChild(txtName);

                // Points
                namedPolygonPoints = document.createElement(DOMConstants.POINTS);
                namedPolygon.appendChild(namedPolygonPoints);

                for (Point point: polygon.getPoints()) {
                    namedPolygonPoint = document.createElement(DOMConstants.POINT);
                    namedPolygonPoints.appendChild(namedPolygonPoint);

                    namedPolygonPointX = document.createElement(DOMConstants.X);
                    namedPolygonPoint.appendChild(namedPolygonPointX);
                    txtPointX = document.createTextNode(((Integer)point.x).toString());
                    namedPolygonPointX.appendChild(txtPointX);

                    namedPolygonPointY = document.createElement(DOMConstants.Y);
                    namedPolygonPoint.appendChild(namedPolygonPointY);
                    txtPointY = document.createTextNode(((Integer)point.y).toString());
                    namedPolygonPointY.appendChild(txtPointY);
                }
            }

            // Cameras
            Element cameras = document.createElement(DOMConstants.CAMERAS);
            root.appendChild(cameras);

            Element camera;
            Element cameraPoint;
            Element cameraPointX;
            Element cameraPointY;
            for (Camera cam: floorContent.getCameras()) {
                camera = document.createElement(DOMConstants.CAMERA);
                cameras.appendChild(camera);

                // Point
                cameraPoint = document.createElement(DOMConstants.POINT);
                camera.appendChild(cameraPoint);

                cameraPointX = document.createElement(DOMConstants.X);
                cameraPoint.appendChild(cameraPointX);
                txtPointX = document.createTextNode(((Integer)cam.x).toString());
                cameraPointX.appendChild(txtPointX);

                cameraPointY = document.createElement(DOMConstants.Y);
                cameraPoint.appendChild(cameraPointY);
                txtPointY = document.createTextNode(((Integer)cam.y).toString());
                cameraPointY.appendChild(txtPointY);
            }

            // FireExtinguishers
            Element fireExtinguishers = document.createElement(DOMConstants.FIREEXTINGUISHERS);
            root.appendChild(fireExtinguishers);

            Element fireExtinguisher;
            Element fireExtinguisherPoint;
            Element fireExtinguisherPointX;
            Element fireExtinguisherPointY;
            for (FireExtinguisher fe: floorContent.getFireExtinguishers()) {
                fireExtinguisher = document.createElement(DOMConstants.FIREEXTINGUISHER);
                fireExtinguishers.appendChild(fireExtinguisher);

                // Point
                fireExtinguisherPoint = document.createElement(DOMConstants.POINT);
                fireExtinguisher.appendChild(fireExtinguisherPoint);

                fireExtinguisherPointX = document.createElement(DOMConstants.X);
                fireExtinguisherPoint.appendChild(fireExtinguisherPointX);
                txtPointX = document.createTextNode(((Integer)fe.x).toString());
                fireExtinguisherPointX.appendChild(txtPointX);

                fireExtinguisherPointY = document.createElement(DOMConstants.Y);
                fireExtinguisherPoint.appendChild(fireExtinguisherPointY);
                txtPointY = document.createTextNode(((Integer)fe.y).toString());
                fireExtinguisherPointY.appendChild(txtPointY);
            }

            // EmergencyExits
            Element emergencyExits = document.createElement(DOMConstants.EMERGENCYEXITS);
            root.appendChild(emergencyExits);

            Element emergencyExit;
            Element emergencyExitPoint;
            Element emergencyExitPointX;
            Element emergencyExitPointY;
            for (EmergencyExit ee: floorContent.getEmergencyExits()) {
                emergencyExit = document.createElement(DOMConstants.EMERGENCYEXIT);
                emergencyExits.appendChild(emergencyExit);

                // Point
                emergencyExitPoint = document.createElement(DOMConstants.POINT);
                emergencyExit.appendChild(emergencyExitPoint);

                emergencyExitPointX = document.createElement(DOMConstants.X);
                emergencyExitPoint.appendChild(emergencyExitPointX);
                txtPointX = document.createTextNode(((Integer)ee.x).toString());
                emergencyExitPointX.appendChild(txtPointX);

                emergencyExitPointY = document.createElement(DOMConstants.Y);
                emergencyExitPoint.appendChild(emergencyExitPointY);
                txtPointY = document.createTextNode(((Integer)ee.y).toString());
                emergencyExitPointY.appendChild(txtPointY);
            }
           
        } catch (Exception ex) {
			Logger.logger.error("Exception in parse in DOMParser " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
        }
        write();
    }

    // Currently saved on disk. TODO: save in database.
    private void write() {
        try {
            // XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            File file = new File("floor.xml");
            StreamResult streamResult = new StreamResult(file);
            DOMSource source = new DOMSource(document);
            transformer.transform(source, streamResult);

			Logger.logger.info("invoice xml file saved to: "+file.getAbsolutePath()); //TODO 100 put in database instead of on harddrive

            // Image
            
        } catch (Exception ex) {
			Logger.logger.error("Exception in write in DOMParser " + ex.getMessage());
			Logger.logger.debug("StackTrace: ", ex);
        }
    }

    private String getExtension(File file) {
        String ext = null;
        String s = file.getName();
        int i = s.lastIndexOf('.');
        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

}
