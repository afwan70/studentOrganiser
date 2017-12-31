package com.amirafwan.student_organizer_v1.data.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.amirafwan.student_organizer_v1.data.DatabaseManager;
import com.amirafwan.student_organizer_v1.data.model.Audio;
import com.amirafwan.student_organizer_v1.viewModel.AudioList;

import java.util.ArrayList;
import java.util.List;


//THE REPO CLASSES ARE FOR THE QUERY SECTION...baru nmpk manage skit !!!

public class AudioRepo {

    private final String TAG = AudioRepo.class.getSimpleName().toString();

    //call the model first
    private Audio audio;

    public AudioRepo(){
        audio = new Audio();
    }

    //create table queries
    public static String createTable(){
        return "CREATE TABLE " + Audio.TABLE  + "("
                + Audio.KEY_AudioID  + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Audio.KEY_Name + " TEXT, "
                + Audio.KEY_Path + " TEXT, "
                + Audio.KEY_SubjectId  + " TEXT )";
    }

    //insert into table queries
    public void insert(Audio audio) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Audio.KEY_AudioID, audio.getAudioId());
        values.put(Audio.KEY_Name, audio.getName());
        values.put(Audio.KEY_Path, audio.getPath());
        values.put(Audio.KEY_SubjectId, audio.getSubjectId());

        // Inserting Row
        db.insert(Audio.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }

    //delete the whole table queries
    public void delete() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Audio.TABLE, null,null);
        DatabaseManager.getInstance().closeDatabase();
    }


    //delete by subjectId
    public void deleteBySubjectId(String id) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Audio.TABLE, Audio.KEY_SubjectId + "=?", new String[]{id});
        DatabaseManager.getInstance().closeDatabase();
    }

    //delete only selected based on id
    public void deleteById(String id){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Audio.TABLE, Audio.KEY_AudioID + "=?", new String[]{id});
        DatabaseManager.getInstance().closeDatabase();
    }


    //Queries to get list of Audio based on subjects
    public List<AudioList> getAudiosBasedOnSubject(String subjectID) {
        AudioList audioList = new AudioList(); //from the viewModel package!!!
        List<AudioList> audioLists = new ArrayList<AudioList>();

        //inner join between audio dengan subjects
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT Audio." + Audio.KEY_AudioID
                + ", Audio." + Audio.KEY_Name
                + ", Audio." + Audio.KEY_Path
                + ", Audio." + Audio.KEY_SubjectId
                + " FROM " + Audio.TABLE
                + " WHERE Audio." + Audio.KEY_SubjectId + "=?"
                ;

        //put the selection queries into Cursor !
        Log.d(TAG, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, new String[] {String.valueOf(subjectID)});
        //looping through all rows and adding to list
        if (cursor.moveToFirst()){
            do {
                audioList = new AudioList();
                audioList.setAudioId(cursor.getString(cursor.getColumnIndex(Audio.KEY_AudioID)));
                audioList.setName(cursor.getString(cursor.getColumnIndex(Audio.KEY_Name)));
                audioList.setPath(cursor.getString(cursor.getColumnIndex(Audio.KEY_Path)));
                audioList.setSubjectId(cursor.getString(cursor.getColumnIndex(Audio.KEY_SubjectId)));

                audioLists.add(audioList);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return audioLists;
    }

}
