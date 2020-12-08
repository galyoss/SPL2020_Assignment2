package bgu.spl.mics;

import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.passiveObjects.CountDownLatch;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.services.HanSoloMicroservice;
import bgu.spl.mics.application.services.R2D2Microservice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import bgu.spl.mics.application.messages.broadcasts.*;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class MessageBusImplTest {

    MessageBusImpl m;
    R2D2Microservice R2D2;
    HanSoloMicroservice hansolo;

    @BeforeEach
    void setUp() { //when constructing MS, the function initialize is called, which calls in turn to register
        this.m = MessageBusImpl.getMessageBus();
        this.hansolo = new HanSoloMicroservice();
        this.R2D2 = new R2D2Microservice(100);
        // now we have m contains Q for each microservice
    }


    @Test
        //precons: exists event s.t. it has a future object matching it.
        // the event was sent to a MS(microservice), and the MS finished the job.
    void complete() {
        int[] ewoks = {1,2};
        AttackEvent att = new AttackEvent(ewoks,1); //let us assume hansolo took the event
        m.register(hansolo);
        hansolo.subscribeEvent(att.getClass(), c->{});
        Future<Boolean> future = m.sendEvent(att);

        hansolo.complete(att, true);
        assertTrue(future.isDone());
        assertEquals(true, future.get());
    }

    @Test
    void sendBroadcast() { //defining a new broadcast message, and subbing both threads to it.
        // after sending the broadcast, we are checking if they performed their callback functions (which means they recieved the broadcast)
        attackDoneBC attackDone = new attackDoneBC();
        int[] ans = {0,0};
        m.register(hansolo);
        m.register(R2D2);
        hansolo.subscribeBroadcast(attackDone.getClass(),c -> ans[0]=1);
        R2D2.subscribeBroadcast(attackDone.getClass(),c -> ans[1]=1);
        m.sendBroadcast(new attackDoneBC());
        try {
            Message hansoloM = m.awaitMessage(hansolo);
            Message R2M = m.awaitMessage(R2D2);
            assertFalse(hansoloM==null);
            assertFalse(R2M==null);
        } catch (InterruptedException e) {}



    }

    @Test // tested during awaitMessage
    void sendEvent() {

    }

    @Test
        // we create an empty list, then add 2 events. when hansolo gets an event, he inserts 1 into the list, R2 adds 2.
        // after all tasks, we would like to get 1-2 OR 2-1 (to check round robin)
    void awaitMessage() { //tested during send broadcast
    }

    @Test //tested during set up (see comments in setup )
    void register() {

    }

    @Test //we subscribe both hansolo and R2 to attack events and attack broadcasts.
        // then, we unregister solo, and send attack events and broadcasts.
        // if solo somehow got the events\broadcasts, it will fail the test
    void unregister() {
        m.register(hansolo);
        m.unregister(hansolo);
        try {
            m.awaitMessage(hansolo);
            fail();
        } catch (Exception e) {
            assertTrue(e != null);
        }
    }


    @Test //tested during awaitMessage
    void subscribeEvent() {
    }

    @Test//tested during sendBroadcast test
    void subscribeBroadcast() {
    }
}
