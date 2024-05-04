package program.interfaces;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class IDHandler {
    protected String ElementID = null;

    void generateID(String id){}

    public String getID(){
        return ElementID;
    }
}
