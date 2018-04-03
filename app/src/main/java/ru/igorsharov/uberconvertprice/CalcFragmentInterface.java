package ru.igorsharov.uberconvertprice;

public interface CalcFragmentInterface {
    void clsView();
    void printSnackbar(String msg, int textColor, int backgroundColor);
    void setVisibilityChBoxOblast(boolean visibility);
    void setResults(float costOne, float profitOne, float costTwo, float profitTwo);
    void setVisibilityBtnCls(boolean flag);
    void setDefaultPartnerCommission(String partnerCommission);
}
