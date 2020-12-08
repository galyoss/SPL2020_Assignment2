package bgu.spl.mics.application.passiveObjects;

import bgu.spl.mics.application.jsonInput;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

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



    private static class ewoksHolder{

        private static Ewoks instance = new Ewoks(jsonInput.getInstance().getEwoks());
   }
    private Ewoks(int numOfEwoks) {

        soldiers = new Ewok[numOfEwoks + 1]; //for convenience, Ewok in cell i has serial num i.
        soldiers[0] = null;
        for (int i = 1; i <= numOfEwoks; i++) {
            soldiers[i] = new Ewok(i);
        }

    }


    public static Ewoks getInstance() {
               return ewoksHolder.instance;
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

