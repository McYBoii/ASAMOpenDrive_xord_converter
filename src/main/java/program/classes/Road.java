package program.classes;

import program.interfaces.IDHandler;

import java.util.ArrayList;

public class Road extends MapElement{
    MapElement predecessor;
    MapElement successor;
    ArrayList<Lane> leftLanes = new ArrayList<>();
    Lane centerLane;
    ArrayList<Lane> rightLanes = new ArrayList<>();

    public Road(Map map){
        super(map);
        //TODO
        generateID();
    }
    void generateID(){
        ElementID = map.getID()+"Road"+atomicInteger.incrementAndGet();
    }

    //TODO
}
