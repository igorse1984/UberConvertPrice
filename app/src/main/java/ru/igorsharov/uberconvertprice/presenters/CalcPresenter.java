package ru.igorsharov.uberconvertprice.presenters;

import android.app.Activity;

import ru.igorsharov.uberconvertprice.calculate.TariffOne;
import ru.igorsharov.uberconvertprice.calculate.TariffTwo;
import ru.igorsharov.uberconvertprice.database.CalcDb;
import ru.igorsharov.uberconvertprice.fragments.CalcFragment;

public class CalcPresenter {

    CalcFragment calcFragment;
    StateBox stateBox;
    TariffOne tariffOne;
    TariffTwo tariffTwo;

    final public String BUTTON_CALC = "button_calc";
    final public String BUTTON_CLC = "button_clc";

    public CalcPresenter(CalcFragment calcFragment) {
        this.calcFragment = calcFragment;

        // хранилище состояний чекбоксов и вводимых данных из активити
        stateBox = new StateBox();

        // инициализируем тарифы
        tariffOne = new TariffOne(stateBox);
        tariffTwo = new TariffTwo(stateBox);
    }

    public void buttonClick(String buttonName) {
        switch (buttonName) {
            case BUTTON_CALC:
                calcFragment.setResults(
                        tariffOne.getCost(),
                        tariffOne.getProfit(),
                        tariffTwo.getCost(),
                        tariffTwo.getProfit());
                break;
            case BUTTON_CLC:
                calcFragment.clsView();
                break;
        }
    }

    public StateBox getStateBox() {
        return stateBox;
    }

    // добавление поездки в список поездок
    public void addToDb(Activity activity) {
        CalcDb.get(activity).addCalc(tariffTwo);
    }

    // меняет видимость чекбокса превышения километража по тарифу
    public void changeChBoxOblastVisibility(float kilometers) {
        if (kilometers > tariffTwo.getKmOver()) {
            calcFragment.setVisibilityChBoxOblast(true);
        } else
            calcFragment.setVisibilityChBoxOblast(false);
    }

    public void printSnackBar(String msg, int textColor, int backgroundColor) {
        calcFragment.printSnackbar(msg, textColor, backgroundColor);
    }

    public void checkEmptyView(String str1, String str2){
        if (str1.equals("") && str2.equals(""))
            calcFragment.setVisibilityBtnCls(true);
        else
           calcFragment.setVisibilityBtnCls(false);
    }

    public void setDefaultPatnerCommission(){
        calcFragment.setDefaultPartnerCommission(String.valueOf(tariffTwo.getPartnerComission()));
    }
}
