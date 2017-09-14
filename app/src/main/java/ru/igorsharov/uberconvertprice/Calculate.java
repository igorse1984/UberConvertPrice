package ru.igorsharov.uberconvertprice;

import android.widget.CheckBox;

import java.util.HashMap;


class Calculate {

    private DataBox dataBox;
    private float time = DataBox.getTime();
    private float way = DataBox.getWay();
    private float boost = DataBox.getBoost();
    private int timePrice, wayPrice, wayOver25price, supplyCarPrice;
    private float uberCommission, pathnerCommission;
    private float resultPrice;

    Calculate(DataBox dataBox, HashMap<String, CheckBox> checkBox) {
        this.dataBox = dataBox;
        this.timePrice = dataBox.getTimePrice();
        this.wayPrice = dataBox.getWayPrice();
        this.wayOver25price = dataBox.getWayOver25price();
        this.supplyCarPrice = dataBox.getSupplyCarPrice();
        this.uberCommission = dataBox.getUberCommission();
        this.pathnerCommission = dataBox.getPathnerCommission();
    }


    float getResultPrice() {
        base().boost();
        return resultPrice;
    }

    float getResultPriceAfterCommission() {
        base().boost().uCommission();
        return resultPrice;
    }

    Calculate base() {
        resultPrice = time * timePrice + way * wayPrice + supplyCarPrice;
        return this;
    }

    Calculate wayOver25() {
        resultPrice += wayOver25price;
        return this;
    }

    Calculate boost() {
        resultPrice *= boost;
        return this;
    }

    Calculate uCommission() {
        resultPrice *= (1 - uberCommission);
        return this;
    }

    Calculate pCommission() {
        resultPrice *= pathnerCommission;
        return this;
    }
}
