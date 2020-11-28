package bgu.spl.mics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;

import org.junit.jupiter.api.Test;

import javax.tools.JavaCompiler;
import java.util.concurrent.TimeUnit;


import static org.junit.jupiter.api.Assertions.*;


public class FutureTest {

    private Future<String> future;

    @BeforeEach
    public void setUp(){
        future = new Future<String>();
    }

    @Test
    //testing immediately after construction, isDone = false, T = null
    public void testConstructor(){
        assertEquals(false, future.isDone());
        assertEquals(null, future.get(1000, TimeUnit.MILLISECONDS));
    }

    @Test
    public void testResolve(){
        String str = "someResult";
        future.resolve(str);
        assertTrue(future.isDone());
        assertEquals(str, future.get());
    }

    @Test
    public void testGet(){
        //scenario1: future is already resovled
        future.resolve("resolved");
        String ans = future.get();
        assertEquals("resolved", ans);


    }

    @Test
    public void testisDone(){
        assertEquals(false, future.isDone());
        String str = "someResult";
        future.resolve(str);
        assertTrue(future.isDone());
    }

    @Test
    public void testGetWithTimeOut() throws InterruptedException
    {
        assertEquals(false, future.isDone());
        future.get(2,TimeUnit.SECONDS);
        assertEquals(false, future.isDone());
        future.resolve("SOLUTION");
        assertEquals(future.get(100,TimeUnit.MILLISECONDS),"SOLUTION");
    }



}
