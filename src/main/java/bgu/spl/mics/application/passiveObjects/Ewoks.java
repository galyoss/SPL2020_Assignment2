package bgu.spl.mics.application.passiveObjects;


import java.util.Arrays;

/**
 * Passive object representing the resource manager.
 * <p>
 * This class must be implemented as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private methods and fields to this class.
 */
public class Ewoks { //needs to be initialized as singleton, while parsing jason
    private Ewok[] soldiers;
    private static Ewoks instance = null;

    public Ewoks(int numOfEwoks) {
        soldiers = new Ewok[numOfEwoks + 1]; //for convenience, Ewok in cell i has serial num i.
        soldiers[0] = null;
        for (int i = 1; i <= numOfEwoks; i++) {
            soldiers[i] = new Ewok(i);
        }
        instance=this;
    }

    public static Ewoks getInstance() {
        return instance;
    }

    public void acquireEwoks(int[] need) {
        Arrays.sort(need);
        for (int i=0;i<need.length;i++){
            synchronized (soldiers[need[i]]) {
                while (!soldiers[need[i]].available) {
                    try {
                        soldiers[need[i]].wait();
                    } catch (Exception e) {
                    }
                }
                soldiers[need[i]].acquire();
            }
        }
    }
            //TODO MAKE SURE THIS CRAP WORKS
    public void releaseEwoks(int[] need) {
        Arrays.sort(need);
        for (int i=0;i<need.length;i++) {
            synchronized (soldiers[need[i]]) {
                soldiers[need[i]].release();
                soldiers[need[i]].notifyAll();
            }
        }
    }
}

