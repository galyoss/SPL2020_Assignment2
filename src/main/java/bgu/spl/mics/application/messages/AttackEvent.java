package bgu.spl.mics.application.messages;
import bgu.spl.mics.Event;

import java.util.List;

public class AttackEvent implements Event<Boolean> {
	private int serial;
	private int[] ewoksNeeded;
	long duration;

	public AttackEvent(List<Integer> ewoks, long duration){
		ewoksNeeded = ewoks.toArray(ewoks);

	}

	@Override
	public void setSerial(int vaule){
	    serial=vaule;
    }

	@Override
	public Integer getSerial() {
		return serial;
	}
}
