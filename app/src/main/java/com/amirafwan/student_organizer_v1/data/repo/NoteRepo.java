package com.amirafwan.student_organizer_v1.data.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.amirafwan.student_organizer_v1.data.DatabaseManager;
import com.amirafwan.student_organizer_v1.data.model.Note;
import com.amirafwan.student_organizer_v1.data.model.Subject;
import com.amirafwan.student_organizer_v1.viewModel.NoteList;

import java.util.ArrayList;
import java.util.List;


//THE REPO CLASSES ARE FOR THE QUERY SECTION...baru nmpk manage skit !!!

public class NoteRepo {

    private final String TAG = NoteRepo.class.getSimpleName().toString();

    //call the model first
    private Note note;

    public NoteRepo(){
         note = new Note();
    }

    //create table queries
    public static String createTable(){
        return "CREATE TABLE " + Note.TABLE  + "("
                + Note.KEY_NoteID  + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Note.KEY_Name + " TEXT, "
                + Note.KEY_Description + " TEXT, "
                + Note.KEY_SubjectId  + " TEXT )";
    }

    //insert into table queries
    public void insert(Note note) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Note.KEY_NoteID, note.getNoteId());
        values.put(Note.KEY_Name, note.getName());
        values.put(Note.KEY_Description, note.getDescription());
        values.put(Note.KEY_SubjectId, note.getSubjectId());

        // Inserting Row
        db.insert(Note.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }

    //delete the whole table queries
    public void delete() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Note.TABLE, null,null);
        DatabaseManager.getInstance().closeDatabase();
    }


    //delete by subjectId
    public void deleteBySubjectId(String id) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Note.TABLE, Note.KEY_SubjectId + "=?", new String[]{id});
        DatabaseManager.getInstance().closeDatabase();
    }


    //delete only selected based on id
    public void deleteById(String id){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Note.TABLE, Note.KEY_NoteID + "=?", new String[]{id});
        DatabaseManager.getInstance().closeDatabase();
    }


    //update based on id
    public void updateById(String id, String name, String description, String subjectId){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        ContentValues values = new ContentValues();
        values.put(Note.KEY_NoteID, id);
        values.put(Note.KEY_Name, name);
        values.put(Note.KEY_Description, description);
        values.put(Note.KEY_SubjectId, subjectId);

        db.update(Note.TABLE, values, Note.KEY_NoteID + "=?", new String[]{id});

        DatabaseManager.getInstance().closeDatabase();
    }


    //selection based on id
    public NoteList getNoteById(String id){

        NoteList noteList = new NoteList(); //from the viewModel package!!!

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT Note." + Note.KEY_NoteID
                + ", Note." + Note.KEY_Name
                + ", Note." + Note.KEY_Description
                + ", Note." + Note.KEY_SubjectId
                + " FROM " + Note.TABLE
                + " WHERE Note." + Note.KEY_NoteID
                + "=?"
                ;

        //put the selection queries into Cursor !
        Log.d(TAG, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, new String[]{ String.valueOf(id) } );
        cursor.moveToFirst();

        noteList.setNoteId(cursor.getString(cursor.getColumnIndex(Note.KEY_NoteID)));
        noteList.setName(cursor.getString(cursor.getColumnIndex(Note.KEY_Name)));
        noteList.setDescription(cursor.getString(cursor.getColumnIndex(Note.KEY_Description)));
        noteList.setSubjectId(cursor.getString(cursor.getColumnIndex(Note.KEY_SubjectId)));

        //Log.d(TAG, "Test assignment Name = " + assignmentList.getName());

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return noteList;

    }



    //Queries to get list of Note based on subjects
    public List<NoteList> getNotesBasedOnSubject(String subjectID) {
        NoteList noteList = new NoteList(); //from the viewModel package!!!
        List<NoteList> noteLists = new ArrayList<NoteList>();

        //inner join between note dengan subjects
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT Note." + Note.KEY_NoteID
                + ", Note." + Note.KEY_Name
                + ", Note." + Note.KEY_Description
                + ", Note." + Note.KEY_SubjectId
                + " FROM " + Note.TABLE
                + " WHERE Note." + Note.KEY_SubjectId + "=?"
                ;

        //put the selection queries into Cursor !
        Log.d(TAG, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, new String[] {String.valueOf(subjectID)});
        //looping through all rows and adding to list
        if (cursor.moveToFirst()){
            do {
                noteList = new NoteList();
                noteList.setNoteId(cursor.getString(cursor.getColumnIndex(Note.KEY_NoteID)));
                noteList.setName(cursor.getString(cursor.getColumnIndex(Note.KEY_Name)));
                noteList.setDescription(cursor.getString(cursor.getColumnIndex(Note.KEY_Description)));
                noteList.setSubjectId(cursor.getString(cursor.getColumnIndex(Subject.KEY_SubjectId)));

                noteLists.add(noteList);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return noteLists;
    }

}
