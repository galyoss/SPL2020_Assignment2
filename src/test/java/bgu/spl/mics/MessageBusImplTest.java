package bgu.spl.mics;

import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.services.HanSoloMicroservice;
import bgu.spl.mics.application.services.LeiaMicroservice;
import bgu.spl.mics.application.services.R2D2Microservice;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageBusImplTest {

    MessageBusImpl m;

    @BeforeEach
    void setUp() {
        m = MessageBusImpl.getMessageBus();
        HanSoloMicroservice hansolo = new HanSoloMicroservice();
        R2D2Microservice R2D2 = new R2D2Microservice(100);
        // now we have m contains Q for each microservice
    }


    @Test
    //precons: exists event s.t. it has a future object matching it.
    // the event was sent to a MS(microservice), and the MS finished the job.
    void complete() {

        AttackEvent att = new AttackEvent(); //let us assume hansolo took the event
        Future<Boolean> future = m.sendEvent(att);
        m.complete(att, true);
        assertTrue(future.isDone());
        assertEquals(true, future.get());
    }

    @Test
    void sendBroadcast() {
    }

    @Test
    void sendEvent() {
    }

    @Test
    void awaitMessage() {
    }
}
  /*  @Test //not needed according to forum
    void register() {
    }*/

  /*  @Test
    void unregister() {
    }*/

    /*@Test
    void subscribeEvent() {
    }

    @Test
    void subscribeBroadcast() {
    }*/