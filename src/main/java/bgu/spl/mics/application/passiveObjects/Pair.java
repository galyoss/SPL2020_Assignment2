package bgu.spl.mics.application.passiveObjects;


public class Pair<K, V> {

    public final K first;
    public final V second;

    public static <K, V> Pair<K, V> createPair(K element0, V element1) {
        return new Pair<K, V>(element0, element1);
    }

    public Pair(K element0, V element1) {
        this.first = element0;
        this.second = element1;
    }
}