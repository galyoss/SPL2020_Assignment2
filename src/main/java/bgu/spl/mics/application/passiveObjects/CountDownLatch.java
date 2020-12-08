package bgu.spl.mics.application.passiveObjects;

import java.util.concurrent.atomic.AtomicInteger;

public class CountDownLatch {

    final private AtomicInteger counter;
    private static CountDownLatch instance;

    public CountDownLatch(int val){
        counter = new AtomicInteger(val);
        instance=this;
    }

    public void decrease(){
        counter.decrementAndGet();
        synchronized (counter){
            counter.notifyAll();
        }


    }
    public static CountDownLatch getInstance(){
        return instance;
    }
    public AtomicInteger getAtomic(){
        return counter;
    }

    public int getValue(){
        return counter.get();

    }

}
