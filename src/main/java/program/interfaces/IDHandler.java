package program.interfaces;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class IDHandler {
    protected String ElementID = null;
    protected AtomicInteger atomicInteger = new AtomicInteger(0);

    void generateID(){}

    public String getID(){
        return ElementID;
    }
}
