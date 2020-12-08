package bgu.spl.mics.application.services;


import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.broadcasts.*;
import bgu.spl.mics.application.passiveObjects.*;


/**
 * HanSoloMicroservices is in charge of the handling {@link AttackEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvent}.
 * <p>
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class HanSoloMicroservice extends MicroService {
    private long lastAtt;
    private Ewoks ewoks;

    public HanSoloMicroservice() {
        super("Han");
        ewoks = Ewoks.getInstance();
    }


    @Override
    protected void initialize() {
        register(this);
        subscribeEvent(AttackEvent.class, c -> {
            // aquire ewoks, sleep for duration, release ewoks, send done, complete, update timestamp
            ewoks.acquireEwoks(c.getEwoksNeeded());
            try {
                Thread.sleep(c.getDuration());
            } catch (InterruptedException e) {
            }
            lastAtt = System.currentTimeMillis();
            ewoks.releaseEwoks(c.getEwoksNeeded());
            complete(c, true);
            sendBroadcast(new attackDoneBC());



        });
        subscribeBroadcast(starBombedBC.class, c -> {
            Diary.getDiary().setHanSoloFinish(lastAtt);
            Diary.getDiary().setHanSoloTerminate(System.currentTimeMillis());
            terminate();
        });
        subscribeBroadcast(attackDoneBC.class, c -> Diary.getDiary().increaseAttack());
    }
}
