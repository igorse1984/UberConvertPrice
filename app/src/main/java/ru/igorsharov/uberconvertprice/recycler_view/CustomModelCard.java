package ru.igorsharov.uberconvertprice.recycler_view;

/**
 * Created by Игорь on 14.03.2018.
 */

public class CustomModelCard {
    private String mTitle;
    private String mDescr;
    private int mImageID;

    public CustomModelCard(String title, String descr, int img) {
        mTitle = title;
        mDescr = descr;
        mImageID = img;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescr() {
        return mDescr;
    }

    public int getImgId() {
        return mImageID;
    }
}

