import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.NotActiveException;

public class Main {

    public static String problemFileHeader(){
        return """
%Metamodel
class Map{
    contains MapElement[] elements opposite map
}
abstract class MapElement{
    container Map map opposite elements
}
class Road extends MapElement{
    MapElement successor
    MapElement predecessor
    %contains Lane [] lanes opposite road
    contains LaneSection[] lanesections opposite road
}
class Junction extends MapElement{
    contains Connection[] connections opposite junction
}
class JunctionGroup extends MapElement{
    contains Junction[] junctions
}
class LaneSection{
    container Road road opposite lanesections
    contains Lane[] leftlanes
    contains Lane centerlane
    contains Lane[] rightlanes
}
class Lane{
    Lane laneSuccessor
    Lane lanePredecessor
    LaneType type
}
class Connection{
    container Junction junction opposite connections
    Road incomingRoad
    Road connectingRoad
    contains LaneLink[] links opposite connection
}
class LaneLink{
    container Connection connection opposite links
    Lane from
    Lane to
}
enum LaneType {border, sidewalk, driving, restricted, none}

%Scope
scope node = 10..5000, Map += 0, Junction += 0, Connection += 0, LaneLink += 0, Road += 0, Lane += 0.

%Instace model
Map(map).
""";
    }

    public static String elementsSetter(String s){
        return "elements(map, "+s+").\n";
    }

    static String FILEPATH = "model.problem";

    public static void main(String[] args) {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        if (args.length != 1) {
            System.out.println("Invalid arguments!\n" +
                    "Give only one argument: the path to the .xodr file.");
        }

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(new File(args[0]));

            Element rootElement = document.getDocumentElement();

            NodeList roads = rootElement.getElementsByTagName("road");
            NodeList junctions = rootElement.getElementsByTagName("junction");
            NodeList junctiongroups = rootElement.getElementsByTagName("junctionGroup");

            int LaneLinkCounter = 0; // LaneLink is missing ID so we generate for it.

            File output = new File(FILEPATH);

            FileWriter fileWriter = new FileWriter(output);

            fileWriter.write(problemFileHeader());

            for (int i = 0; i < roads.getLength(); i++) {
                   Node road = roads.item(i);

                   if(road.getNodeType() == Node.ELEMENT_NODE){
                       Element eroad = (Element) road;

                       fileWriter.write(elementsSetter("Road"+eroad.getAttribute("id")));

                       NodeList links = eroad.getElementsByTagName("link");

                       NodeList lanesections = eroad.getElementsByTagName("laneSection");

                       System.out.println(lanesections.getLength());

                       System.out.println(links.getLength());
                   }
            }
            
            

            fileWriter.close();
            
            System.out.println("Everything went fine you can find the output here: "+output.getAbsolutePath());

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