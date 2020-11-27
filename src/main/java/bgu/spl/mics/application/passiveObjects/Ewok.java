package bgu.spl.mics.application.passiveObjects;

/**
 * Passive data-object representing a forest creature summoned when HanSolo and C3PO receive AttackEvents.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You may add fields and methods to this class as you see fit (including public methods).
 */
public class Ewok {
	int serialNumber;
	boolean available;
	
  
    /**
     * Acquires an Ewok
     */

    public Ewok(int serialNumber){
        if (serialNumber<1)
            throw new IllegalArgumentException("serial must be non-negative number");
        this.serialNumber=serialNumber;
        available=true;
    }

    //@precon: available = true
    public void acquire() {
        available=false;
    }

    /**
     * release an Ewok
     */
    //precon: available = false
    public void release() {
    	available = true;
    }
}
