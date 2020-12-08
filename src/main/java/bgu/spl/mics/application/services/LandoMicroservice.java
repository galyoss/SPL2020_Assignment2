package bgu.spl.mics.application.services;
import bgu.spl.mics.application.messages.*;
import bgu.spl.mics.application.messages.broadcasts.*;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.passiveObjects.*;


/**
 * LandoMicroservice
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class LandoMicroservice  extends MicroService {//intialize while constructing
    long duration;
    public LandoMicroservice(long duration) {
        super("Lando");
        this.duration = duration;

    }

    @Override
    protected void initialize() {
        super.register(this);
        subscribeEvent(starDestroyerEvent.class, c -> {
            try {
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendBroadcast(new starBombedBC());
        });
        subscribeBroadcast(starBombedBC.class, c -> {
            Diary.getDiary().setLandoTerminate(System.currentTimeMillis());
            terminate();
        });
    }
}
