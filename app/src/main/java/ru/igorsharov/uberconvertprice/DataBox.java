package ru.igorsharov.uberconvertprice;

class DataBox {

    private static float time;
    private static float timeWaiting;
    private static float way;
    private static float boost;

    private int timePrice;
    private int wayPrice;
    private int wayOver25price;
    private int supplyCarPrice;
    private float uberCommission = 0.2f;
    private float partnerCommission = 0.05f;
    private boolean way25 = false;
    private boolean partnerCommissionStat = false;


    DataBox(int timePrice, int wayPrice, int supplyCarPrice) {
        this.timePrice = timePrice;
        this.wayPrice = wayPrice;
        this.supplyCarPrice = supplyCarPrice;
    }

    DataBox(int timePrice, int wayPrice, int wayOver25price, int supplyCarPrice) {
        this(timePrice, wayPrice, supplyCarPrice);
        this.wayOver25price = wayOver25price;
    }


    static void setTime(float time) {
        if (time >= 0) {
            DataBox.time = time;
        }
    }

    static void setTimeWaiting(float timeWaiting) {
        if (timeWaiting >= 0) {
            DataBox.timeWaiting = timeWaiting;
        }
    }

    static void setWay(float way) {
        if (way >= 0) {
            DataBox.way = way;
        }
    }

    static void setBoost(float boost) {
        DataBox.boost = boost;
    }

    void setPartnerCommissionStatus(boolean partnerCommissionStat) {
        this.partnerCommissionStat = partnerCommissionStat;
    }

    void setWay25(boolean way25) {
        this.way25 = way25;
    }

    private float base() {
        return ((timePrice * (time - timeWaiting) + wayPrice * (way25 ? 25 : way) + wayOver25price * (way25 ? way - 25 : 0) + supplyCarPrice));
    }

    float getCalculateWithoutCommission() {
        return base() * boost;
    }

    float getCalculateWithCommission() {
        return (base() * boost * (1 - uberCommission)) * ((partnerCommissionStat ? (1 - partnerCommission) : 1));
    }

    float getCalculateGBoostWithCommission() {
        return base() * (boost - uberCommission) * ((partnerCommissionStat ? (1 - partnerCommission) : 1));
    }
}