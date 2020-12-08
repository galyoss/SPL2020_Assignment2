package bgu.spl.mics.application.services;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.broadcasts.attackDoneBC;
import bgu.spl.mics.application.messages.broadcasts.starBombedBC;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Ewoks;


/**
 * C3POMicroservices is in charge of the handling {@link AttackEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class C3POMicroservice extends MicroService {//intialize while constructing
    private long lastAtt;
    private Ewoks ewoks;

    public C3POMicroservice() {
        super("C3PO");
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
                System.out.println("starbombed C3PO");
                Diary.getDiary().setC3POFinish(lastAtt);
                Diary.getDiary().setC3POTerminate(System.currentTimeMillis());
                terminate();
            }
        });

    }
}
