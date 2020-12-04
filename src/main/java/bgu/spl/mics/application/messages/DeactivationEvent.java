package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;

public class DeactivationEvent implements Event {
    private int serial;


    public DeactivationEvent(){

    }
    @Override
    public void setSerial(int value) {
        this.serial=value;
    }

    @Override
    public Integer getSerial() {
        return serial;
    }


}
