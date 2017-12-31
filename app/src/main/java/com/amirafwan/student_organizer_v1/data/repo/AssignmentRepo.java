package com.amirafwan.student_organizer_v1.data.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.amirafwan.student_organizer_v1.data.DatabaseManager;
import com.amirafwan.student_organizer_v1.data.model.Assignment;
import com.amirafwan.student_organizer_v1.viewModel.AssignmentList;

import java.util.ArrayList;
import java.util.List;


//THE REPO CLASSES ARE FOR THE QUERY SECTION

public class AssignmentRepo {

    private final String TAG = AssignmentRepo.class.getSimpleName().toString();

    //call the model first
    private Assignment assignment;

    public AssignmentRepo(){
        assignment = new Assignment();
    }

    //create table queries
    public static String createTable(){
        return "CREATE TABLE " + Assignment.TABLE + "("
                + Assignment.KEY_AssignmentId + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Assignment.KEY_Name + " TEXT, "
                + Assignment.KEY_Description + " TEXT, "
                + Assignment.KEY_DateDue + " TEXT )";
    }


    //insert into table queries
    public int insert(Assignment assignment){
        int assignmentId;
        //DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Assignment.KEY_AssignmentId, assignment.getAssignmentId());
        values.put(Assignment.KEY_Name, assignment.getName());
        values.put(Assignment.KEY_Description, assignment.getDescription());
        values.put(Assignment.KEY_DateDue, assignment.getDateDue());
        //values.put(Assignment.KEY_DateDue, df.format(assignment.getDateDue()));

        //Inserting Row
        assignmentId = (int)db.insert(Assignment.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
        return assignmentId;
    }


    //delete the whole table queries
    public void delete(){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Assignment.TABLE, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }

    //delete only selected based on id
    public void deleteById(String id){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Assignment.TABLE, Assignment.KEY_AssignmentId + "=?", new String[]{id});
    }


    //update based on id
    public void updateById(String id, String name, String description, String dateDue){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        ContentValues values = new ContentValues();
        values.put(Assignment.KEY_AssignmentId, id);
        values.put(Assignment.KEY_Name, name);
        values.put(Assignment.KEY_Description, description);
        values.put(Assignment.KEY_DateDue, dateDue);

        db.update(Assignment.TABLE, values, "AssignmentId = ?", new String[]{id});

        DatabaseManager.getInstance().closeDatabase();
    }


    //selection based on id
    public AssignmentList getAssignmentById(String id){

        AssignmentList assignmentList = new AssignmentList(); //from the viewModel package!!!

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT Assignment." + Assignment.KEY_AssignmentId
                + ", Assignment." + Assignment.KEY_Name
                + ", Assignment." + Assignment.KEY_Description
                + ", Assignment." + Assignment.KEY_DateDue
                + " FROM " + Assignment.TABLE
                + " WHERE Assignment." + Assignment.KEY_AssignmentId
                + "=?"
                ;

        //put the selection queries into Cursor !
        Log.d(TAG, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, new String[]{ String.valueOf(id) } );
        cursor.moveToFirst();

        assignmentList.setAssignmentId(cursor.getString(cursor.getColumnIndex(Assignment.KEY_AssignmentId)));
        assignmentList.setName(cursor.getString(cursor.getColumnIndex(Assignment.KEY_Name)));
        assignmentList.setDescription(cursor.getString(cursor.getColumnIndex(Assignment.KEY_Description)));
        assignmentList.setDateDue(cursor.getString(cursor.getColumnIndex(Assignment.KEY_DateDue)));

        //Log.d(TAG, "Test assignment Name = " + assignmentList.getName());

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return assignmentList;

    }



    //Queries to get list of Assignment table and Data
    public List<AssignmentList> getAllAssignments() {
        AssignmentList assignmentList = new AssignmentList(); //from the viewModel package!!!
        List<AssignmentList> assignmentLists = new ArrayList<AssignmentList>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT Assignment." + Assignment.KEY_AssignmentId
                + ", Assignment." + Assignment.KEY_Name
                + ", Assignment." + Assignment.KEY_Description
                + ", Assignment." + Assignment.KEY_DateDue
                + " FROM " + Assignment.TABLE
                ;

        //put the selection queries into Cursor !
        Log.d(TAG, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);
        //looping through all rows and adding to list
        if (cursor.moveToFirst()){
            do {
                assignmentList = new AssignmentList();
                assignmentList.setAssignmentId(cursor.getString(cursor.getColumnIndex(Assignment.KEY_AssignmentId)));
                assignmentList.setName(cursor.getString(cursor.getColumnIndex(Assignment.KEY_Name)));
                assignmentList.setDescription(cursor.getString(cursor.getColumnIndex(Assignment.KEY_Description)));
                assignmentList.setDateDue(cursor.getString(cursor.getColumnIndex(Assignment.KEY_DateDue)));

                assignmentLists.add(assignmentList);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return assignmentLists;
    }






    //............................NIE TESTING JER...................................
    //ONLY NAME....NO ID LAAA !!!!!!!!!!....tgk2 smula !!!
    public ArrayList<String> getOnlyAssignmentName() {
        ArrayList<String> nameList = new ArrayList<>();

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT Assignment." + Assignment.KEY_Name
                + " FROM " + Assignment.TABLE
                ;

        //put the selection queries into Cursor !
        Log.d(TAG, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);
        //looping through all rows and adding to list
        if (cursor.moveToFirst()){
            do {

                nameList.add(cursor.getString(cursor.getColumnIndex(Assignment.KEY_Name)));

            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return nameList;
    }

}
