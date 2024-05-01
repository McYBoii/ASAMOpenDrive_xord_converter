package program.classes;

import program.interfaces.IDHandler;

public abstract class MapElement extends IDHandler {
    protected final Map map;

    public MapElement(Map map){
        this.map = map;
    }

    public Map getMap(){
        return map;
    }
}
