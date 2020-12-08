package bgu.spl.mics.application.messages;
import bgu.spl.mics.Event;


public class AttackEvent implements Event<Boolean> {
	private int serial;
	private int[] ewoksNeeded;
	int duration;

	public int[] getEwoksNeeded() {
		return ewoksNeeded;
	}



	public AttackEvent(int[] ewoks, int duration){
		ewoksNeeded = ewoks;
		this.duration = duration;

	}

	@Override
	public void setSerial(int vaule){
	    serial=vaule;
    }

	@Override
	public Integer getSerial() {
		return serial;
	}

	public int getDuration(){
		return duration;
	}
}
