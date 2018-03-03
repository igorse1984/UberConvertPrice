package ru.igorsharov.uberconvertprice;

/**
 * Created by IgorSE on 14.02.2018.
 */

public class StateBox {
    private boolean chBoxGarantpikState;
    private boolean chBoxOblastState;
    private float distState;
    private float timeState;
    private float ratioState;
    private float pathnerComission;



    /* чекбоксы */
    void setChBoxGarantpikState(boolean chBoxGarantpikState) {
        this.chBoxGarantpikState = chBoxGarantpikState;
    }

    void setChBoxOblastState(boolean chBoxOblastState) {
        this.chBoxOblastState = chBoxOblastState;
    }

    public boolean getChBoxGarantpikState() {
        return chBoxGarantpikState;
    }

    public boolean getChBoxOverState() {
        return chBoxOblastState;
    }

    /* вводимые данные */
    void setData(float dist, float time, float ratio, float pathnerComission) {
        distState = dist;
        timeState = time;
        ratioState = ratio;
        this.pathnerComission = pathnerComission;
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

    public float getPathnerComission() {
        return pathnerComission;
    }
}
