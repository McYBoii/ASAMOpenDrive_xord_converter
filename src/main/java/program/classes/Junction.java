package program.classes;

import java.util.ArrayList;

public class Junction extends MapElement{
    ArrayList<Connection> connections = new ArrayList<>();

    public Junction(Map map){
        super(map);
        //TODO
        generateID();
    }
    void generateID(){
        ElementID = map.getID()+"Junction"+atomicInteger.incrementAndGet();
    }

    //TODO
}
