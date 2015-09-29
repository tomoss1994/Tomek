package com.example.tomasz.tomek;

/**
 * Created by Tomasz on 2015-09-27.
 */
public final class BatteryDatabase {
    // To prevent someone from accidentally instantiating the contract class,
    // Labels table name
    public static final String TABLE = "SETTINGS";

    // Labels Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_FAHRENHEIT = "fahrenheit";
    public static final String KEY_CHARGE = "charge";

    // property help us to keep data
    public int settings_ID;
    public int fahrenheit;
    public int charge;
}

