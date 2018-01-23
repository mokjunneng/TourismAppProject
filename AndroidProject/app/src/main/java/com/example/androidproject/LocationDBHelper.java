package com.example.androidproject;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class LocationDBHelper extends SQLiteOpenHelper{

    private final Context context;
    //database version
    public static final int DATABASE_VERSION = 1;
    //database name
    public static final String DATABASE_NAME = "ItineraryManager";
    //table name
    public static final String TABLE_NAME = "locationDetails";
    //table column names
    public static final String KEY_ID = "id";
    public static final String KEY_FROM = "from";
    public static final String KEY_TO = "to";
    public static final String KEY_PTMONEY = "ptmoney";
    public static final String KEY_PTTIME = "pttime";
    public static final String KEY_TAXIMONEY = "taximoney";
    public static final String KEY_TAXITIME = "taxitime";
    public static final String KEY_WALKTIME = "walktime";

    private SQLiteDatabase sqLiteDatabase;

    LocationDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //build command string to create table
        final String SQL_CREATE_TABLE = "CREATE TABE" + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FROM +
                " TEXT," + KEY_TO + " TEXT," + KEY_PTMONEY + " INTEGER," + KEY_PTTIME + " INTEGER," + KEY_TAXIMONEY + " INTEGER," +
                KEY_TAXITIME + " INTEGER," + KEY_WALKTIME + " INTEGER" + ")";

        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addLocationEdge(LocationEdge locationEdge){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FROM,locationEdge.getFrom());
        values.put(KEY_TO,locationEdge.getTo());
        values.put(KEY_PTMONEY,locationEdge.getPtmoney());
        values.put(KEY_PTTIME,locationEdge.getPttime());
        values.put(KEY_TAXIMONEY,locationEdge.getTaximoney());
        values.put(KEY_TAXITIME,locationEdge.getTaxitime());
        values.put(KEY_WALKTIME,locationEdge.getWalktime());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

//    public LocationEdge getLocationEdge(int id){
//        SQLiteDatabase db = this.getReadableDatabase();
//        //Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_FROM, KEY_TO,})
//    }


}
