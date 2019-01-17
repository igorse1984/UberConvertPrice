package ru.igorsharov.uberconvertprice.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by IgorSE on 13.02.2018.
 */

abstract public class UberCalculator {
    private final String signAdd = "+";
    private final String signReduce = "-";
    String title; // название тарифа
    private float dist; // километраж поездки
    private float time; // время поездки
    private float ratio; // коэффициент пикового спроса
    boolean over; // выключатель использования подсчета превышения км

    private float preCost; // предварительная посчитанная стоимость
    private float resultCostClient; // посчитанная стоимость
    private float profit; // посчитанный доход от поездки
    private float retainedUberCommission; // удержанная Уберовская комиссия
    private float retainedPartnerCommission; // удержанная партнерская комиссия
    private float warrantyPeakSurcharge; // размер доплаты за гарантированный пик
    private float highPrice; // размер надбавки повышенного спроса
    float percDiffRide; // процентная разница в сравнении с каршерингом

    /* основные переменные прайса, инициализируются в наследниках */
    float distPrice; // за 1 км
    float kmOver; // километраж свыше которого будет изменена стоимость км
    float distPriceOver; // за 1 км (свыше кол-ва км)
    float timePrice; // за 1 мин
    float supplyCarPrice; // за подачу автомобиля
    private float uberCommission = 0.2f; // комиссия убера
    private float partnerCommission = 5f; // комиссия партнера, используется для установки дефолтного значения

    private float carSharingPriceByTime = 7f;

    // коробка с входными значениями для расчета
    StateBox stateBox;


    UberCalculator(StateBox stateBox) {
        this.stateBox = stateBox;
    }


    // сбор данных из stateBox перед расчетом
    protected void getDataOfStateBox() {
        // дефолтим коэффициенты для исключения ошибок в рассчетах
        ratio = 1;
        // достаем данные из коробки для расчета
        dist = stateBox.getDist();
        time = stateBox.getTime();
        ratio = stateBox.getRatio();
    }

    // вычисления
    private void calculate() {
        getDataOfStateBox();
        boolean warrantyPeakState = stateBox.getChBoxWarrantyPeakState();

        preCost = ((over ? kmOver * distPrice + (dist - kmOver) * distPriceOver : dist * distPrice)
                + time * timePrice + supplyCarPrice);

        highPrice = 0;
        warrantyPeakSurcharge = 0;


        if (ratio > 1) {
            float peak = preCost * (ratio - 1);
            if (warrantyPeakState) warrantyPeakSurcharge = peak;
            else highPrice = peak;
        }

        resultCostClient = preCost + highPrice;
        retainedUberCommission = resultCostClient * uberCommission;

        float costOfGarantPeakSurcharge = resultCostClient + warrantyPeakSurcharge - retainedUberCommission;
        retainedPartnerCommission = resultCostClient * stateBox.getPatnerCommission() / 100;
        profit = costOfGarantPeakSurcharge - retainedPartnerCommission;

        // внедрение расчета сравнения с каршерингом
        float carSharingPrice = time * carSharingPriceByTime;
        // вычисление процентной разницы текущей поездки с предполагаемой каршеринговой
        percDiffRide = Math.abs((resultCostClient - carSharingPrice) / ((resultCostClient + carSharingPrice) / 2)) * 100;
    }

    private String toStr(float inString) {
        NumberFormat nf = new DecimalFormat("#.##");
        return nf.format(inString);
    }

    // вычисление стоимости поездки без учета пиков
    private String getPreCost() {
        return toStr(preCost);
    }

    // вычисление стоимости поездки с учетом естественных пиков
    private String getResultCostClient() {
        return toStr(resultCostClient);
    }

    // вычисление размера надбавки за повышенный спрос
    private String getHighPrice() {
        return signAdd.concat(toStr(highPrice));
    }

    // возвращает Уберовскую удерживаемую комиссию
    private String getMasterRetainedCommission() {
        return signReduce.concat(toStr(retainedUberCommission));
    }


    private String getWarrantyPeakSurcharge() {
        return signAdd.concat(toStr(warrantyPeakSurcharge));
    }

    // возвращает партнерскую удерживаемую комиссию
    private String getAdditionalRetainedCommission() {
        return signReduce.concat(toStr(retainedPartnerCommission));
    }

    // вычисление дохода от поездки
    private String getProfit() {
        return toStr(profit);
    }

    public String getTitle() {
        return title;
    }

    public float getPartnerCommission() {
        return partnerCommission;
    }

    public float getKmOver() {
        return kmOver;

    }

    public String[] getCalculate() {
        calculate();
        String[] result = new String[7];
        result[0] = getPreCost();
        result[1] = getHighPrice();
        result[2] = getResultCostClient();
        result[3] = getMasterRetainedCommission();
        result[4] = getWarrantyPeakSurcharge();
        result[5] = getAdditionalRetainedCommission();
        result[6] = getProfit();
        return result;
    }

    public float getPercDiffRide() {
        return percDiffRide;
    }
}
