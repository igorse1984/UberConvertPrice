package ru.igorsharov.uberconvertprice.model;

/**
 * Created by IgorSE on 14.02.2018.
 */

public class StateBox {
    private boolean chBoxGarantpikState;
    private boolean chBoxOblastState;
    private float distance;
    private float time;
    private float ratio;
    private float patnerCommission;


    /* чекбоксы */
    public void setChBoxGarantpikState(boolean chBoxGarantpikState) {
        this.chBoxGarantpikState = chBoxGarantpikState;
    }

    public void setChBoxOblastState(boolean chBoxOblastState) {
        this.chBoxOblastState = chBoxOblastState;
    }

    public boolean getChBoxWarrantyPeakState() {
        return chBoxGarantpikState;
    }

    public boolean getChBoxOverState() {
        return chBoxOblastState;
    }

    /* вводимые данные */
    public void setData(float data[]) {
        distance = data[0];
        time = data[1];
        ratio = data[2];
        patnerCommission = data[3];
    }

    public float getDist() {
        return distance;
    }

    public float getTime() {
        return time;
    }

    public float getRatio() {
        return ratio;
    }

    public float getPatnerCommission() {
        return patnerCommission;
    }
}
