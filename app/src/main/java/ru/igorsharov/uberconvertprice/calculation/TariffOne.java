package ru.igorsharov.uberconvertprice.calculation;

import ru.igorsharov.uberconvertprice.StateBox;

/**
 * Created by IgorSE on 13.02.2018.
 */

public class TariffOne extends Calculator {

    public TariffOne(StateBox stateBox) {
        super(stateBox);
        /* Прайс по тарифу (руб.) */
        title = "7 7";
        distPrice = 7; // за 1 км
        timePrice = 7; // за 1 мин
        supplyCarPrice = 49; // за подачу автомобиля
    }
}
