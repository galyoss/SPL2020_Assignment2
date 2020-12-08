package bgu.spl.mics.application.services;
import bgu.spl.mics.application.messages.*;
import bgu.spl.mics.application.messages.broadcasts.*;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.DeactivationEvent;
import bgu.spl.mics.application.passiveObjects.*;


/**
 * R2D2Microservices is in charge of the handling {@link DeactivationEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link DeactivationEvent}.
 * <p>
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class R2D2Microservice extends MicroService {
    private long duration;
    public R2D2Microservice(long duration) {
        super("R2D2");
        this.duration=duration;
    }

    @Override
    protected void initialize() {
        register(this);
        subscribeEvent(allAttacksDone.class, c -> {
            try {
                Thread.sleep(duration);
            } catch (InterruptedException e) {}
            Diary.getDiary().setR2D2Deactivate(System.currentTimeMillis());
            R2D2Microservice.super.complete(c,true);

        });
        subscribeBroadcast(starBombedBC.class, c -> {
            Diary.getDiary().setR2D2Terminate(System.currentTimeMillis());
            terminate();
        });
    }
}
