package ru.igorsharov.uberconvertprice.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.igorsharov.uberconvertprice.database.CalcDbSchema.CalcTable;

/**
 * Created by IgorSE on 15.02.2018.
 */

public class CalcDbHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "calcBase.db";

    public CalcDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + CalcTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                CalcTable.Columns.PRICE_NAME + ", " +
                CalcTable.Columns.COST + ", " +
                CalcTable.Columns.PROFIT + ", " +
                CalcTable.Columns.RATIO +
                ")"
        );
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
