package program.classes;

import program.interfaces.IDHandler;

import java.util.ArrayList;

public class Lane extends IDHandler {
    final Road road;

    LaneType laneType;

    public Lane(Road road){
        this.road = road;
        //TODO
        generateID();
    }
    void generateID(){
        ElementID = road.getID()+"Lane"+atomicInteger.incrementAndGet();
    }

    //TODO
}
