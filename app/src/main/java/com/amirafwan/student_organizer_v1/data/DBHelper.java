package com.amirafwan.student_organizer_v1.data;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.amirafwan.student_organizer_v1.app.App;
import com.amirafwan.student_organizer_v1.data.model.Assignment;
import com.amirafwan.student_organizer_v1.data.model.Audio;
import com.amirafwan.student_organizer_v1.data.model.Image;
import com.amirafwan.student_organizer_v1.data.model.Note;
import com.amirafwan.student_organizer_v1.data.model.Subject;
import com.amirafwan.student_organizer_v1.data.repo.AssignmentRepo;
import com.amirafwan.student_organizer_v1.data.repo.AudioRepo;
import com.amirafwan.student_organizer_v1.data.repo.ImageRepo;
import com.amirafwan.student_organizer_v1.data.repo.NoteRepo;
import com.amirafwan.student_organizer_v1.data.repo.SubjectRepo;



//this class is for handling the database Table creation & upgrade !
public class DBHelper extends SQLiteOpenHelper {

    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 8;

    //Our Database Name
    private static final String DATABASE_NAME = "amircourseapp_v1.db";
    private static final String TAG = DBHelper.class.getSimpleName().toString();

    public DBHelper() {
        super(App.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here
        db.execSQL(AssignmentRepo.createTable());
        db.execSQL(SubjectRepo.createTable());
        db.execSQL(NoteRepo.createTable());
        db.execSQL(ImageRepo.createTable());
        db.execSQL(AudioRepo.createTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, String.format("SQLiteDatabase.onUpgrade(%d -> %d)", oldVersion, newVersion));

        //Drop table if existed, all data will be gone!!
        //and upgrade the new one !
        db.execSQL("DROP TABLE IF EXISTS " + Assignment.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Subject.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Note.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Image.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Audio.TABLE);
        onCreate(db);
    }
}