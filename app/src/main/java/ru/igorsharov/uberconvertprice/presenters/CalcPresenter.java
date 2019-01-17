package ru.igorsharov.uberconvertprice.presenters;

import android.app.Activity;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.igorsharov.uberconvertprice.database.CalcDb;
import ru.igorsharov.uberconvertprice.model.StateBox;
import ru.igorsharov.uberconvertprice.model.TariffOne;
import ru.igorsharov.uberconvertprice.model.TariffTwo;
import ru.igorsharov.uberconvertprice.model.UberCalculator;
import ru.igorsharov.uberconvertprice.view.CalcFragmentInterface;

@InjectViewState
public class CalcPresenter extends MvpPresenter<CalcFragmentInterface> {

    private StateBox stateBox;
    private TariffOne tariffOne;
    private TariffTwo tariffTwo;


    final public String BUTTON_CALC = "button_calc";
    final public String BUTTON_CLC = "button_clc";

    public CalcPresenter() {
        // хранилище состояний чекбоксов и вводимых данных из активити
        stateBox = new StateBox();

        // инициализируем тарифы
        tariffOne = new TariffOne(stateBox);
        tariffTwo = new TariffTwo(stateBox);

    }

    public void buttonClick(String buttonId) {
        UberCalculator currentTariff;

        currentTariff = tariffTwo;
        boolean warrantyPeakState = stateBox.getChBoxWarrantyPeakState();
        float ratio = stateBox.getRatio();

        switch (buttonId) {

            case BUTTON_CALC:
                getViewState().getRideData();
                getViewState().getChBoxRegionState();
                getViewState().getChBoxWarrantyPeakState();

                // изменение видимости View
                visibilityView(warrantyPeakState, ratio);

//                getViewState().setTextInTextView(DESC_HIGH_PRICE, "повышенный спрос (x" + ratio + ")");

                // рассчет и передача на отображение
                getViewState().displayResults(currentTariff.getCalculate());
                break;

            case BUTTON_CLC:
                visibilityView(warrantyPeakState, ratio);
                getViewState().clsView();
                break;
        }
    }

    // устанавливает видимость View участвующих в рассчете в зависимости от имеющихся параметров повышенного спроса
    private void visibilityView(boolean warrantyPeakState, float ratio) {
        boolean ratioState = !warrantyPeakState && ratio > 1f;
//        getViewState().setVisibilityDesc(DESC_HIGH_PRICE, ratioState);
//        getViewState().setVisibilityResult(RESULT_HIGH_PRICE, ratioState);
//
//        getViewState().setVisibilityResult(RESULT_CLIENT_COST, ratioState);
//        getViewState().setVisibilityDesc(DESC_LINE_CLIENT_COST, ratioState);
//
//        getViewState().setVisibilityDesc(DESC_GARANT_PEAK_SURCHARGE, warrantyPeakState);
//        getViewState().setVisibilityResult(RESULT_GARANT_PEAK_SURCHARGE, warrantyPeakState);

    }

    private void calcAttentionCarSharing(UberCalculator currentTariff) {
        if (currentTariff.getPercDiffRide() < 10){
//getViewState().se
            return;
        }
    }

    // передача вводимых данных в StateBox
    public void setRideDataOfView(float... data) {
        stateBox.setData(data);
    }

    public void setChBoxRegionState(boolean b) {
        stateBox.setChBoxOblastState(b);
    }

    public void setChBoxWarrantyPeakState(boolean b) {
        stateBox.setChBoxGarantpikState(b);
    }


    // добавление поездки в список поездок
    public void addToDb(Activity activity) {
        CalcDb.get(activity).addCalc(tariffTwo);
    }

    // меняет видимость чекбокса превышения километража по тарифу
    public void changeChBoxOblastVisibility(float kilometers) {
        if (kilometers > tariffTwo.getKmOver()) {
            getViewState().setVisibilityChBoxOblast(true);
        } else
            getViewState().setVisibilityChBoxOblast(false);
    }

    public void printSnackBar(String msg, int textColor, int backgroundColor) {
        getViewState().printSnackbar(msg, textColor, backgroundColor);
    }

    public void checkEmptyView(String str1, String str2) {
        if (str1.equals("") && str2.equals(""))
            getViewState().setVisibilityBtnCls(true);
        else
            getViewState().setVisibilityBtnCls(false);
    }

    public void setDefaultPatnerCommission() {
        getViewState().setDefaultPartnerCommission(String.valueOf(tariffTwo.getPartnerCommission()));
    }
}
