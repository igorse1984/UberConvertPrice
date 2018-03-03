package ru.igorsharov.uberconvertprice.calculation;

import ru.igorsharov.uberconvertprice.StateBox;

/**
 * Created by IgorSE on 13.02.2018.
 */

abstract public class Calculator {
    String title; // название тарифа
    private float dist; // планируемый километраж поездки
    private float time; // планируемое время поездки
    private float ratio; // коэффициент естественного пикового спроса
    boolean over; // выключатель использования подсчета превышения км

    /* основные переменные прайса, инициализируются в наследниках */
    float distPrice; // за 1 км
    float kmOver; // километраж свыше которого будет изменена стоимость км
    float distPriceOver; // за 1 км (свыше кол-ва км)
    float timePrice; // за 1 мин
    float supplyCarPrice; // за подачу автомобиля
    private float uberComission = 0.8f; // комиссия убера
    private float beznalComission = 0.95f; // комиссия партнера

    // коробка с входными значениями для расчета
    StateBox stateBox;


    Calculator(StateBox stateBox) {
        this.stateBox = stateBox;
    }


    // сбор данных из stateBox перед расчетом
    void getData() {
        // дефолтим коэффициенты для исключения ошибок в рассчетах
        ratio = 1;
        // достаем данные для расчета из коробки
        dist = stateBox.getDist();
        time = stateBox.getTime();
        ratio = stateBox.getRatio();
    }

    // вычисление стоимости поездки
    public float getCost() {
        getData();
        return ((over ? kmOver * distPrice + (dist - kmOver) * distPriceOver : dist * distPrice)
                + time * timePrice + supplyCarPrice)
                * (stateBox.getChBoxGarantpikState() ? 1 : ratio);
    }

    // вычисление дохода от поездки
    public float getProfit() {
        return getCost()
                * uberComission
                * (stateBox.getChBoxGarantpikState() ? ratio : 1)
                * (stateBox.getChBoxBeznalState() ? beznalComission : 1);
    }

    public String getTitle() {
        return title;
    }

    public float getRatio() {
        return ratio;
    }

    public float getKmOver() {
        return kmOver;
    }
}
