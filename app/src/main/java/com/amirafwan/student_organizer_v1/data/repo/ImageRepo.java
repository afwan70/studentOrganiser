package com.amirafwan.student_organizer_v1.data.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.amirafwan.student_organizer_v1.data.DatabaseManager;
import com.amirafwan.student_organizer_v1.data.model.Image;
import com.amirafwan.student_organizer_v1.data.model.Subject;
import com.amirafwan.student_organizer_v1.viewModel.ImageList;

import java.util.ArrayList;
import java.util.List;


//THE REPO CLASSES ARE FOR THE QUERY SECTION...baru nmpk manage skit !!!

public class ImageRepo {

    private final String TAG = ImageRepo.class.getSimpleName().toString();

    //call the model first
    private Image image;

    public ImageRepo(){
        image = new Image();
    }

    //create table queries
    public static String createTable(){
        return "CREATE TABLE " + Image.TABLE  + "("
                + Image.KEY_ImageID  + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Image.KEY_Name + " TEXT, "
                + Image.KEY_Data + " BLOB, "
                + Image.KEY_SubjectId  + " TEXT )";
    }

    //insert into table queries
    public void insert(Image image) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Image.KEY_ImageID, image.getImageId());
        values.put(Image.KEY_Name, image.getName());
        values.put(Image.KEY_Data, image.getData());
        values.put(Image.KEY_SubjectId, image.getSubjectId());

        // Inserting Row
        db.insert(Image.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }

    //delete the whole table queries
    public void delete() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Image.TABLE, null,null);
        DatabaseManager.getInstance().closeDatabase();
    }


    //delete by subjectId
    public void deleteBySubjectId(String id) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Image.TABLE, Image.KEY_SubjectId + "=?", new String[]{id});
        DatabaseManager.getInstance().closeDatabase();
    }


    //Queries to get list of Image based on subjects
    public List<ImageList> getImagesBasedOnSubject(String subjectID) {
        ImageList imageList = new ImageList(); //from the viewModel package!!!
        List<ImageList> imageLists = new ArrayList<ImageList>();

        //inner join between image dengan subjects
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT Image." + Image.KEY_ImageID
                + ", Image." + Image.KEY_Name
                + ", Image." + Image.KEY_Data
                + ", Image." + Image.KEY_SubjectId
                + " FROM " + Image.TABLE
                + " WHERE Image." + Image.KEY_SubjectId + "=?"
                ;



        /*
        //put the selection queries into Cursor !
        Log.d(TAG, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, new String[] {String.valueOf(subjectID)});
        //looping through all rows and adding to list
        if (cursor.moveToFirst()){
            do {
                imageList = new ImageList();
                imageList.setImageId(cursor.getString(cursor.getColumnIndex(Image.KEY_ImageID)));
                imageList.setName(cursor.getString(cursor.getColumnIndex(Image.KEY_Name)));
                imageList.setData(cursor.getBlob(cursor.getColumnIndex(Image.KEY_Data))); //get the byte of the image first..then we output stream
                imageList.setSubjectId(cursor.getString(cursor.getColumnIndex(Subject.KEY_SubjectId)));

                imageLists.add(imageList);
            } while (cursor.moveToNext());
        }
        */

        //.............testing....yg atas ni yg default.....
        Cursor cursor = db.rawQuery(selectQuery, new String[] {String.valueOf(subjectID)});
        //looping through all rows and adding to list
        if(cursor !=null && cursor.getCount() > 0){
            if (cursor.moveToFirst()){
                do {
                    imageList = new ImageList();
                    imageList.setImageId(cursor.getString(cursor.getColumnIndex(Image.KEY_ImageID)));
                    imageList.setName(cursor.getString(cursor.getColumnIndex(Image.KEY_Name)));
                    imageList.setData(cursor.getBlob(cursor.getColumnIndex(Image.KEY_Data))); //get the byte of the image first..then we output stream
                    imageList.setSubjectId(cursor.getString(cursor.getColumnIndex(Subject.KEY_SubjectId)));

                    imageLists.add(imageList);
                } while (cursor.moveToNext());
            }
        }
        //...........................................................


        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return imageLists;
    }

}
