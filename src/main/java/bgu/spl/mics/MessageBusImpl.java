package bgu.spl.mics;

import jdk.internal.net.http.common.Pair;

import java.util.*;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {

    private Dictionary<Integer, Future> future_event;
    private Dictionary<MicroService, Queue<Message>> name_messagesQueue;
    private LinkedList<Pair<Class<? extends Message>, LinkedList<MicroService>>> event_subsList;
    private AtomicInteger event_counter;
    private static MessageBusImpl instance = null;


    private MessageBusImpl() {
        future_event = new Hashtable<>();
        name_messagesQueue = new Hashtable<>();
        event_subsList = new LinkedList<>();
        event_counter = new AtomicInteger(0);
    }


    public static synchronized MessageBusImpl getMessageBus() {
        if (instance == null)
            instance = new MessageBusImpl();
        return instance;
    }

    private void subscribeMessage(Class<? extends Message> type, MicroService m) {
        //if e exists, add to subs list, else- create new pair with e and subslist
        synchronized (event_subsList) {
            LinkedList<MicroService> subsList = findSubsList(type);
            if (subsList != null) { //MB knows this event type yet, simply add m to list
                synchronized (subsList) {// if two thread want to sub to the same type, we manage them by the list's monitor
                    subsList.add(m);
                    return;
                }
            }
            // if reached here, then MB doesnt know this event type, create new pair
            LinkedList<MicroService> newSubList = new LinkedList<MicroService>();
            newSubList.add(m);
            event_subsList.add(new Pair<>(type, newSubList));
        }//sync events_subs
    }

    @Override
    public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
        subscribeMessage(type, m);
    }

    private <T> LinkedList<MicroService> findSubsList(Class<? extends Message> type) {

        for (int i = 0; i < event_subsList.size(); i++) {

            Pair<Class<? extends Message>, LinkedList<MicroService>> curr = event_subsList.get(i);
            if (curr.first == type)
                return curr.second;

        }
        return null;
    }

    @Override
    public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
        subscribeMessage(type, m);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> void complete(Event<T> e, T result) {
        int serial = e.getSerial();
        future_event.get(serial).resolve(result);
    }

    @Override
    public void sendBroadcast(Broadcast b) {//TODO get answer from forum regarding w8 or drop
        LinkedList<MicroService> subsList = findSubsList(b.getClass());

        if (subsList != null) {
            for (MicroService m : subsList) {
                Queue<Message> Q = name_messagesQueue.get(m);
                synchronized (Q) {
                    Q.add(b);
                    notifyAll();
                }
            }
        }
    }


    // in this function we are returning the microservice which will have the event added to it's Q
    private <T> MicroService findSub(Event<T> e) { //TODO iterator over instead of for
//        for (int i = 0; i < event_subsList.size(); i++) {
//
//            Pair<Message, LinkedList<MicroService>> curr = event_subsList.get(i);
//            if (curr.first.getClass() == e.getClass()) { //found subscribers lists for this event

        LinkedList<MicroService> subsList = findSubsList(e.getClass());
        if (subsList != null) {
            while (!subsList.isEmpty()) {
                synchronized (subsList) { //making sure list is thread-safe
                    MicroService m = subsList.remove(); //removing also unregistered MS
                    if (name_messagesQueue.get(m) != null) { //found active MS that subscribes to this message
                        subsList.add(m); //for round-robin assignment
                        return m;
                    }
                }

            }
        }

        return null; //no active MS subscriber found
    }

    @Override
    public <T> Future<T> sendEvent(Event<T> e) { //COMPLETED!! not tested tho

        MicroService m = findSub(e);
        if (m != null) { //there is a MS that can take the event
            Future<T> future = new Future<>();
            e.setSerial(event_counter.getAndIncrement()); //serilizing the event and its future
            future_event.put(e.getSerial(), future);
            synchronized (name_messagesQueue.get(m.getName())) { //message Q needs to be ThreadSafe
                name_messagesQueue.get(m.getName()).add(e); //adding event to the MS's Q.
            }
            return future;

        }
        // no one took the event
        return null;
    }

    @Override
    public void register(MicroService m) {
        name_messagesQueue.put(m, new LinkedList<>());

    }

    @Override
    public void unregister(MicroService m) {//TODO CHECK ABOUT UNLISTING ALL RELEVANT CRAP
        synchronized (name_messagesQueue) {
            if (name_messagesQueue.get(m) != null)
                name_messagesQueue.remove(m);
        }

    }

    @Override
    public Message awaitMessage(MicroService m) throws InterruptedException {//TODO get answers from forum regarding awaitMessage b4 registration
        // assuming initialized b4 await message
        Queue<Message> currQ = name_messagesQueue.get(m);
        if (currQ==null)
            throw new IllegalArgumentException("MS is not registered, tried to fetch message");
        synchronized (currQ) {
            while (currQ.isEmpty()) {
                try {
                    wait();
                }
                catch (Exception e){}
            }
            return currQ.remove();

        }
    }
}
