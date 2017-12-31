package com.amirafwan.student_organizer_v1.app;

import android.app.Application;
import android.content.Context;

import com.amirafwan.student_organizer_v1.data.DBHelper;
import com.amirafwan.student_organizer_v1.data.DatabaseManager;



//this class will be the first file to be execute when the application running and it’s only execute once
//the purpose of this file is to keep variable that we need to share across all the packages
//and these variables are mean to create one time and only has 1 instance in entire application life cycle, and we call this – Singleton

public class App extends Application {

    private static Context context;
    private static DBHelper dbHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
        dbHelper = new DBHelper();
        DatabaseManager.initializeInstance(dbHelper);
    }

    public static Context getContext(){
        return context;
    }
}
