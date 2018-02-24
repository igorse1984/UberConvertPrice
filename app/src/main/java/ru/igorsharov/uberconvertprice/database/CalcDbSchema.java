package ru.igorsharov.uberconvertprice.database;

/**
 * Created by IgorSE on 15.02.2018.
 */

public class CalcDbSchema {
    public static final class CalcTable {
        public static final String NAME = "calculations";

        public static final class Columns {
//            public static final String UUID = "uuid";
            public static final String PRICE_NAME = "price_name"; // тариф, по которому производился расчет
//            public static final String DATE = "date";
            public static final String COST = "cost"; // стоимость поездки
            public static final String PROFIT = "profit"; // доход от поездки
            public static final String RATIO = "ratio"; // применяемый коэффициент

        }
    }
}
