package bgu.spl.mics.application.passiveObjects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Passive data-object representing a Diary - in which the flow of the battle is recorded.
 * We are going to compare your recordings with the expected recordings, and make sure that your output makes sense.
 * <p>
 * Do not add to this class nothing but a single constructor, getters and setters.
 */
public class Diary { //needs to be initialized as singleton
    AtomicInteger totalAttacks;
    long HanSoloFinish;
    long C3POFinish;
    long R2D2Deactivate;
    long LeiaTerminate;
    long HanSoloTerminate;


    public void setTotalAttacks() {
        this.totalAttacks = new AtomicInteger(0);
    }

    public AtomicInteger getTotalAttacks() {
        return totalAttacks;
    }

    public long getHanSoloFinish() {
        return HanSoloFinish;
    }

    public long getC3POFinish() {
        return C3POFinish;
    }

    public long getR2D2Deactivate() {
        return R2D2Deactivate;
    }

    public long getLeiaTerminate() {
        return LeiaTerminate;
    }

    public long getHanSoloTerminate() {
        return HanSoloTerminate;
    }

    public long getC3POTerminate() {
        return C3POTerminate;
    }

    public long getR2D2Terminate() {
        return R2D2Terminate;
    }

    public long getLandoTerminate() {
        return LandoTerminate;
    }

    long C3POTerminate;
    long R2D2Terminate;
    long LandoTerminate;


    private static class diaryHolder{
        private static Diary instance = new Diary();
    }

    private Diary() {
        totalAttacks = new AtomicInteger(0);
    }

    public static Diary getDiary() {
        return diaryHolder.instance;
    }

    public void increaseAttack() {
        this.totalAttacks.incrementAndGet();
    }

    public void setHanSoloFinish(long hanSoloFinish) {
        HanSoloFinish = hanSoloFinish;
    }

    public void setC3POFinish(long c3POFinish) {
        C3POFinish = c3POFinish;
    }

    public void setR2D2Deactivate(long r2D2Deactivate) {
        R2D2Deactivate = r2D2Deactivate;
    }

    public void setLeiaTerminate(long leiaTerminate) {
        LeiaTerminate = leiaTerminate;
    }

    public void setHanSoloTerminate(long hanSoloTerminate) {
        HanSoloTerminate = hanSoloTerminate;
    }

    public void setC3POTerminate(long c3POTerminate) {
        C3POTerminate = c3POTerminate;
    }


    public void setR2D2Terminate(long r2D2Terminate) {
        R2D2Terminate = r2D2Terminate;
    }

    public void setLandoTerminate(long landoTerminate) {
        LandoTerminate = landoTerminate;
    }


}
