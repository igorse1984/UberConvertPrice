package ru.igorsharov.uberconvertprice;

class DataBox {
    private int timePrice, wayPrice, wayOver25price, supplyCarPrice;
    private static float time, way, boost;
    private float uberCommission = 0.2f, pathnerCommission = 0.05f;

    DataBox(int timePrice, int wayPrice, int supplyCarPrice) {
        this.timePrice = timePrice;
        this.wayPrice = wayPrice;
        this.supplyCarPrice = supplyCarPrice;
    }

    DataBox(int timePrice, int wayPrice, int supplyCarPrice, int wayOver25price) {
        this(timePrice, wayPrice, supplyCarPrice);
        this.wayOver25price = wayOver25price;
    }

    void setUberCommission(float u) {
        if (u > 0 && u < 1) {
            uberCommission = u;
        }
    }

    void setParthnerCommission(float p) {
        if (p > 0 && p < 1) {
            pathnerCommission = p;
        }
    }

    static void setTime(float time) {
        DataBox.time = time;
    }

    static void setWay(float way) {
        DataBox.way = way;
    }

    static void setBoost(float boost) {
        DataBox.boost = boost;
    }

    static float getTime() {
        return time;
    }

    static float getWay() {
        return way;
    }

    static float getBoost() {
        return boost;
    }

    int getTimePrice() {
        return timePrice;
    }

    int getWayPrice() {
        return wayPrice;
    }

    int getWayOver25price() {
        return wayOver25price;
    }

    int getSupplyCarPrice() {
        return supplyCarPrice;
    }

    float getUberCommission() {
        return uberCommission;
    }

    float getPathnerCommission() {
        return pathnerCommission;
    }
}