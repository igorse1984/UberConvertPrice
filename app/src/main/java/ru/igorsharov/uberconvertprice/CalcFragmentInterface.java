package ru.igorsharov.uberconvertprice;

import android.view.View;

public interface CalcFragmentInterface {
    void clsView();
    void printSnackbar(View view, String msg, int textColor, int backgroundColor);
    void setVisibilityChBoxOblast(boolean visibility);
    void setResults(float costOne, float profitOne, float costTwo, float profitTwo);
}
