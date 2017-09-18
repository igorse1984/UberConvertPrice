package ru.igorsharov.uberconvertprice;

class DataBox {

    private int timePrice, wayPrice, wayOver25price, supplyCarPrice;
    private static float time, timeWaiting, way, boost;
    private float uberCommission = 0.2f, partnerCommission = 0.05f;
    private Calculate calculate = new Calculate();
    private boolean way25 = false;
    private boolean partnerCommissionStat = false;


    class Calculate {

        private float base() {
            return ((timePrice * (time - timeWaiting) + wayPrice * (way25 ? 25 : way) + wayOver25price * (way25 ? way - 25 : 0) + supplyCarPrice));
        }

        float baseWithoutCommission() {
            return base() * boost;
        }

        float baseWithCommission() {
            return (base() * boost * (1 - uberCommission)) * ((partnerCommissionStat ? (1 - partnerCommission) : 1));
        }

        float gBoostWithCommission() {
            return base() * (boost - uberCommission) * ((partnerCommissionStat ? (1 - partnerCommission) : 1));
        }
    }


    DataBox(int timePrice, int wayPrice, int supplyCarPrice) {
        this.timePrice = timePrice;
        this.wayPrice = wayPrice;
        this.supplyCarPrice = supplyCarPrice;
    }

    DataBox(int timePrice, int wayPrice, int wayOver25price, int supplyCarPrice) {
        this(timePrice, wayPrice, supplyCarPrice);
        this.wayOver25price = wayOver25price;
    }

    void setUberCommission(float u) {
        if (u > 0 && u < 1) {
            uberCommission = u;
        }
    }

    void setPartnerCommission(float p) {
        if (p > 0 && p < 1) {
            partnerCommission = p;
        }
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

    Calculate getCalculate() {
        return calculate;
    }

    void setPartnerCommissionStatus(boolean partnerCommissionStat) {
        this.partnerCommissionStat = partnerCommissionStat;
    }

    void setWay25(boolean way25) {
        this.way25 = way25;
    }


}