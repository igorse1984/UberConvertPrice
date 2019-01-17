package ru.igorsharov.uberconvertprice.model;

/**
 * Created by IgorSE on 13.02.2018.
 */

public class TariffTwo extends UberCalculator {

    public TariffTwo(StateBox stateBox) {
        super(stateBox);
        /* Прайс по тарифу (руб.) */
        title = "3 12";
        distPrice = 12; // за 1 км
        kmOver = 25; // превышение км
        distPriceOver = 18; // за 1 км, после превышения км
        timePrice = 3; // за 1 мин
        supplyCarPrice = 39; // за подачу автомобиля
    }

    // дополняем метод
    @Override
    protected void getDataOfStateBox() {
        super.getDataOfStateBox();
        // задаем переменной значение чекбокса т.к. в тарифе используется подсчет с превышением
        over = stateBox.getChBoxOverState();
    }
}
