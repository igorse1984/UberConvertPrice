package ru.igorsharov.uberconvertprice.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface CalcFragmentInterface extends MvpView {

    void clsView();

    void printSnackbar(String msg, int textColor, int backgroundColor);

    void setVisibilityChBoxOblast(boolean visibility);

//    void displayResults(String[] calculationData, boolean[] visibilityData);
    void displayResults(String[] calculationData);

    void setVisibilityBtnCls(boolean flag);

    void setDefaultPartnerCommission(String partnerCommission);

//    void setVisibilityDesc(int indexDescTv, boolean flag);
//
//    void setVisibilityResult(int indexResultTv, boolean flag);

    void setTextInTextView(int tvName, String str);

    void getRideData();

    void getChBoxWarrantyPeakState();

    void getChBoxRegionState();
}
