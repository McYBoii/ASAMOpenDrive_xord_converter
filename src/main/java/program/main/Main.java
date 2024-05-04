package program.main;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import program.classes.Junction;
import program.classes.Map;
import program.classes.Road;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        Map map = Map.getINSTANCE();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        if(args.length != 1){
            System.out.println("Invalid arguments!\n" +
                    "Give only one argument: the path to the .xodr file.");
        }

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document =  builder.parse(new File(args[0]));

            Element rootElement = document.getDocumentElement();

            NodeList roads = rootElement.getElementsByTagName("road");

            for (int i = 0; i < roads.getLength(); i++) {
                Node roadnode = roads.item(i);

                if(roadnode.getNodeType() == Node.ELEMENT_NODE){
                    Element road = (Element) roadnode;
                    Road newroad = new Road(map, road.getAttribute("id"));



                }
            }
            NodeList junctions = rootElement.getElementsByTagName("junction");

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("File not found.\n Exiting...");
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }
}