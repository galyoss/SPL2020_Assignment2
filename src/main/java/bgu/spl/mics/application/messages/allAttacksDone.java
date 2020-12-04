package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;

public class allAttacksDone implements Event<Boolean> {
    private int serial;


    public allAttacksDone(){
    }
    @Override
    public void setSerial(int value) {
        this.serial = value;

    }

    @Override
    public Integer getSerial() {
        return serial;
    }
}
