package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;

public class starDestroyerEvent implements Event {
    private int serial;

    public starDestroyerEvent(){    }
    @Override
    public void setSerial(int value) {
        this.serial=value;
    }

    @Override
    public Integer getSerial() {
        return serial;
    }
}
