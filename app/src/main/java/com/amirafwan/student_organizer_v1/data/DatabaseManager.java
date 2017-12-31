package com.amirafwan.student_organizer_v1.data;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



//this class is for handling the database connection
public class DatabaseManager {

    private Integer mOpenCounter = 0;

    private static DatabaseManager instance;
    private static SQLiteOpenHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;

    public static synchronized void initializeInstance(SQLiteOpenHelper helper) {
        if (instance == null){
            instance = new DatabaseManager();
            mDatabaseHelper = helper;
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null){
            throw new IllegalStateException(DatabaseManager.class.getSimpleName() +
                    "is not initialized, call initilizeInstance(..) method first.");
        }
        return instance;
    }

    //Opening the database
    public synchronized SQLiteDatabase openDatabase(){
        mOpenCounter += 1;
        if(mOpenCounter == 1) {
            //Opening new database
            mDatabase = mDatabaseHelper.getWritableDatabase();
        }
        return mDatabase;
    }

    //Closing the database
    public synchronized void closeDatabase(){
        mOpenCounter -=1;
        if(mOpenCounter == 0){
            //closing the database
            mDatabase.close();
        }
    }

}