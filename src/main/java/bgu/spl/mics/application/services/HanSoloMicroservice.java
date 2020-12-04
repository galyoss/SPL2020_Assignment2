package bgu.spl.mics.application.services;


import bgu.spl.mics.Callback;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.*;
import bgu.spl.mics.application.messages.broadcasts.*;
import bgu.spl.mics.application.passiveObjects.*;

import java.sql.Timestamp;

/**
 * HanSoloMicroservices is in charge of the handling {@link AttackEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvent}.
 * <p>
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class HanSoloMicroservice extends MicroService {
    private int attCounter;
    private long lastAtt;
    private Ewoks ewoks;

    public HanSoloMicroservice() {
        super("Han");
        ewoks = Ewoks.getInstance();
    }


    @Override
    protected void initialize() {
        register(this);
        subscribeEvent(AttackEvent.class, new Callback<AttackEvent>() {
            @Override
            public void call(AttackEvent c) {
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



            }
        });
        subscribeBroadcast(starBombedBC.class, new Callback<starBombedBC>() {
            @Override
            public void call(starBombedBC c) {
                System.out.println("starbombed HANSO"); //TODO
                Diary.getDiary().setHanSoloFinish(lastAtt);
                Diary.getDiary().setHanSoloTerminate(System.currentTimeMillis());
                HanSoloMicroservice.super.terminate();
            }
        });
        subscribeBroadcast(attackDoneBC.class, new Callback<attackDoneBC>() {

            @Override
            public void call(attackDoneBC c) {
                Diary.getDiary().increaseAttack();
            }
        });
    }
}
