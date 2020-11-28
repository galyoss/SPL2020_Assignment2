package bgu.spl.mics;
import jdk.internal.net.http.common.Pair;

import  java.util.*;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {

	private Dictionary<Integer,Future<?>> future_event;
	private Dictionary<String,Queue<Event<?>>> name_messagesQueue;
	private LinkedList<Pair<Message,LinkedList<MicroService>>> event_subsList;
	private AtomicInteger event_counter;
	private static MessageBusImpl instance = null;


	private MessageBusImpl(){
		future_event  = new Hashtable<>();
		name_messagesQueue = new Hashtable<>();
		event_subsList = new LinkedList<Pair<Message, LinkedList<MicroService>>>();
		event_counter = new AtomicInteger(0);
	}

	public static MessageBusImpl getMessageBus(){//TODO synchro problems
		if (instance ==null)
			instance = new MessageBusImpl();
		return instance;

	}


	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {

	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {

    }

	@Override @SuppressWarnings("unchecked")
	public <T> void complete(Event<T> e, T result) {
        int serial = e.getSerial();
        future_event.get(serial).resolve(result); //TODO why doesnt it accept result as T
	}

	@Override
	public void sendBroadcast(Broadcast b) {
    }
    // in this function we are returning the microservice which will have the event added to it's Q
	private  <T> MicroService findSub(Event<T> e){ //TODO iterator over instead of for
		for (int i =0; i<event_subsList.size();i++){

			Pair<Message,LinkedList<MicroService>> curr = event_subsList.get(i);
			if (curr.first.getClass()==e.getClass()){ //found subscribers lists for this event
				MicroService m = curr.second.remove();
				curr.second.add(m);
				return m;
			}
		}
		return null;
	}

	@Override
	public <T> Future<T> sendEvent(Event<T> e) { //COMPLETED!!!!!!!!!!! not tested tho

	    MicroService m = findSub(e);

	    if (m!=null) { //there is a MS that can take the event


            Future<T> future = new Future<>();
            e.setSerial(event_counter.getAndIncrement()); //serilizing the event and its future
            future_event.put(e.getSerial(), future);
            name_messagesQueue.get(m.getName()).add(e); //adding event to the MS's Q.
            return future;
        }
	    // no one took the event
        return null;
	}

	@Override
	public void register(MicroService m) {
		name_messagesQueue.put(m.getName(), new LinkedList<>());

	}

	@Override
	public void unregister(MicroService m) { //TODO

	}

	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {

		return null;
	}
}
