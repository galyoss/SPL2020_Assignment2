package bgu.spl.mics.application.messages;
import bgu.spl.mics.Event;

public class AttackEvent implements Event<Boolean> {
	private int serial;


	public void setSerial(int vaule){
	    serial=vaule;
    }
}
