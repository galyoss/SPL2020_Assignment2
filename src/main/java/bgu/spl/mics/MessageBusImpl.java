package bgu.spl.mics;
import jdk.internal.net.http.common.Pair;

import  java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {

	Dictionary<Integer,Future<?>> future_event;
	Dictionary<String,Queue<Event<?>>> name_message;
	LinkedList<Pair<Message,LinkedList<MicroService>>> event_subsList;
	AtomicInteger event_counter;

	private MessageBusImpl(){
		future_event  = new Hashtable<>();
		name_message = new Hashtable<>();
		event_subsList = new LinkedList<Pair<Message, LinkedList<MicroService>>>();
		event_counter = new AtomicInteger(0);
	}

	public static MessageBusImpl getMessageBus(){	return new MessageBusImpl(); } //TODO
	
	
	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		
	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		
    }

	@Override @SuppressWarnings("unchecked")
	public <T> void complete(Event<T> e, T result) {
		
	}

	@Override
	public void sendBroadcast(Broadcast b) {
		
	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		
        return null;
	}

	@Override
	public void register(MicroService m) {
		
	}

	@Override
	public void unregister(MicroService m) {
		
	}

	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		
		return null;
	}
}
