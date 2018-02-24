package ru.igorsharov.uberconvertprice;

/**
 * Created by IgorSE on 14.02.2018.
 */

public class StateBox {
    private boolean chBoxBeznalState;
    private boolean chBoxGarantpikState;
    private boolean chBoxOblastState;
    private float distState;
    private float timeState;
    private float ratioState;



    /* чекбоксы */
    void setChBoxBeznalState(boolean chBoxBeznalState) {
        this.chBoxBeznalState = chBoxBeznalState;
    }

    void setChBoxGarantpikState(boolean chBoxGarantpikState) {
        this.chBoxGarantpikState = chBoxGarantpikState;
    }

    void setChBoxOblastState(boolean chBoxOblastState) {
        this.chBoxOblastState = chBoxOblastState;
    }

    public boolean getChBoxBeznalState() {
        return chBoxBeznalState;
    }

    public boolean getChBoxGarantpikState() {
        return chBoxGarantpikState;
    }

    public boolean getChBoxOverState() {
        return chBoxOblastState;
    }

    /* вводимые данные */
    void setData(float dist, float time, float ratio) {
        distState = dist;
        timeState = time;
        ratioState = ratio;
    }

    public float getDist() {
        return distState;
    }

    public float getTime() {
        return timeState;
    }

    public float getRatio() {
        return ratioState;
    }
}
