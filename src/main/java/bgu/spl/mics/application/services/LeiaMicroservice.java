package bgu.spl.mics.application.services;
import bgu.spl.mics.Future;
import bgu.spl.mics.application.messages.*;
import bgu.spl.mics.application.messages.broadcasts.*;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.passiveObjects.*;
import bgu.spl.mics.MicroService;


/**
 * LeiaMicroservices Initialized with Attack objects, and sends them as  {@link AttackEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvent}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class LeiaMicroservice extends MicroService {
	private Attack[] attacks;
	private AttackEvent[] attackEvents;
	private int attCounter;

	
    public LeiaMicroservice(Attack[] attacks) {
        super("Leia");
        attCounter=0;
		this.attacks = attacks;
		attackEvents = new AttackEvent[attacks.length];
        for (int i = 0; i <attacks.length ; i++) { //converting all attacks to attackevents
            attackEvents[i] = convertAttEvent(attacks[i]);

        }

    }

    private AttackEvent convertAttEvent(Attack attack) { // single attack conversion
        int[] ewoks = new int[attack.getSerials().size()];
        for (int i = 0; i <ewoks.length ; i++) { //toArray
            ewoks[i] = attack.getSerials().get(i);
        }
        return new AttackEvent(ewoks, attack.getDuration());
    }



    @Override
    protected void initialize() {
        register(this);
        subscribeBroadcast(starBombedBC.class, c -> {
            Diary.getDiary().setLeiaTerminate(System.currentTimeMillis());
            terminate();

        });
        subscribeBroadcast(attackDoneBC.class, c -> {
            attCounter++;
            if (attCounter==attackEvents.length){
                Future shieldGen = LeiaMicroservice.super.sendEvent(new allAttacksDone());
                shieldGen.get();
                sendEvent(new starDestroyerEvent());
            }
        });

        for(int i=0;i<attackEvents.length;i++) {
            super.sendEvent(attackEvents[i]);
        }
    }
}
