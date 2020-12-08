package bgu.spl.mics.application;
import bgu.spl.mics.application.passiveObjects.Attack;

public  class jsonInput {
    private Attack[] attacks;
    long R2D2,Lando;
    int Ewoks;
    private static jsonInput instance;

    public static jsonInput getInstance(){
        if (instance==null)
            instance=new jsonInput();
        return instance;
    }
    public jsonInput(){
        instance=this;

    }

    public int getEwoks() {
        return Ewoks;
    }
    public void setEwoks(int ewoks) {
        Ewoks = ewoks;
    }
    public long getLando() {
        return Lando;
    }
    public void setLando(int lando) {
        Lando = lando;
    }
    public long getR2D2() {
        return R2D2;
    }
    public void setR2D2(int r2d2) {
        R2D2 = r2d2;
    }
    public Attack[] getAttacks() {
        return attacks;
    }
    public void setAttacks(Attack[] attacks) {
        this.attacks = attacks;
    }
}

