package program.classes;

import program.interfaces.IDHandler;

import java.util.ArrayList;

public class Connection extends IDHandler {
    final Junction junction;
    Road incomingRoad;
    Road connectingRoad;

    ArrayList<Link> links = new ArrayList<>();
    public Connection(Junction junction){
        this.junction = junction;
        //TODO
        generateID();
    }
    void generateID(){
        ElementID = junction.getID()+"Connection"+atomicInteger.incrementAndGet();
    }


    //TODO
}
