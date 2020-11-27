package bgu.spl.mics.application.passiveObjects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EwokTest {

    Ewok tester;

    @BeforeEach
    void setup(){// new ewok b4 every test
        tester= new Ewok(4);
    }

    @Test
    void illegalSerialConstructorTest()// trying to create ewok with illegal number (>=0) (invariant - serial num>0
    {
        try {
            tester = new Ewok(-1);
            fail();
        }
        catch (Exception e) {}
    }

    @Test
    void acquire() { //default ewok is free (precon), after acquire it is not free (postcon)
        assertEquals(true, tester.available);
        tester.acquire();
        assertEquals(false, tester.available);
    }

    @Test
    void release() {// ewok is being used (precon), after release should be free (postcon)
        tester.acquire();
        assertEquals(false, tester.available);
        tester.release();
        assertEquals(true, tester.available);
    }
}