package program.classes;

import program.interfaces.IDHandler;
import java.util.ArrayList;

public class Map extends IDHandler {
    ArrayList<MapElement> mapElements = new ArrayList<>();

    public Map(){
        generateID();
    }
    void generateID(){
        ElementID = "Map"+atomicInteger.incrementAndGet();
    }

    public void addMapElement(MapElement mapElement){
        mapElements.add(mapElement);
    }
}
