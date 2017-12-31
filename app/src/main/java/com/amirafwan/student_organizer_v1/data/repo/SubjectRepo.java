package com.amirafwan.student_organizer_v1.data.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.amirafwan.student_organizer_v1.data.DatabaseManager;
import com.amirafwan.student_organizer_v1.data.model.Subject;
import com.amirafwan.student_organizer_v1.viewModel.SubjectList;

import java.util.ArrayList;
import java.util.List;



//THE REPO CLASSES ARE FOR THE QUERY SECTION...baru nmpk manage skit !!!

public class SubjectRepo {

    private final String TAG = SubjectRepo.class.getSimpleName().toString();

    //call the model first
    private Subject subject;

    public SubjectRepo(){
        subject = new Subject();
    }

    //create table queries
    public static String createTable(){
        return "CREATE TABLE " + Subject.TABLE + "("
                + Subject.KEY_SubjectId + " TEXT PRIMARY KEY, "
                + Subject.KEY_Name + " TEXT )";
    }


    //insert into table queries
    public int insert(Subject subject){
        int subjectId;

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Subject.KEY_SubjectId, subject.getSubjectId());
        values.put(Subject.KEY_Name, subject.getName());

        //Inserting Row
        subjectId = (int)db.insert(Subject.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
        return subjectId;
    }



    //delete the whole table queries
    public void delete(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Subject.TABLE, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }


    //delete only selected based on id
    public void deleteById(String id){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Subject.TABLE, Subject.KEY_SubjectId + "=?", new String[]{id});
    }


    //update based on id
    public void updateById(String id, String name){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        ContentValues values = new ContentValues();
        values.put(Subject.KEY_SubjectId, id);
        values.put(Subject.KEY_Name, name);

        db.update(Subject.TABLE, values, "SubjectId = ?", new String[]{id});

        DatabaseManager.getInstance().closeDatabase();
    }


    //selection based on id
    public SubjectList getSubjectById(String id){

        SubjectList subjectList = new SubjectList(); //from the viewModel package!!!

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT Subject." + Subject.KEY_SubjectId
                + ", Subject." + Subject.KEY_Name
                + " FROM " + Subject.TABLE
                + " WHERE Subject." + Subject.KEY_SubjectId
                + "=?"
                ;

        //put the selection queries into Cursor !
        Log.d(TAG, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, new String[]{ String.valueOf(id) } );
        cursor.moveToFirst();

        subjectList.setSubjectId(cursor.getString(cursor.getColumnIndex(Subject.KEY_SubjectId)));
        subjectList.setName(cursor.getString(cursor.getColumnIndex(Subject.KEY_Name)));

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return subjectList;

    }




    //Queries to get list of Subject table and Data
    public List<SubjectList> getAllSubjects() {
        SubjectList subjectList = new SubjectList(); //from the viewModel package!!!
        List<SubjectList> subjectLists = new ArrayList<SubjectList>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT Subject." + Subject.KEY_SubjectId
                + ", Subject." + Subject.KEY_Name
                + " FROM " + Subject.TABLE
                ;

        //put the selection queries into Cursor !
        Log.d(TAG, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);
        //looping through all rows and adding to list
        if (cursor.moveToFirst()){
            do {
                subjectList = new SubjectList();
                subjectList.setSubjectId(cursor.getString(cursor.getColumnIndex(Subject.KEY_SubjectId)));
                subjectList.setName(cursor.getString(cursor.getColumnIndex(Subject.KEY_Name)));

                subjectLists.add(subjectList);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return subjectLists;
    }

}
