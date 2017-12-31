package com.amirafwan.student_organizer_v1.data.model;



public class Image {

    public static final String TAG = Image.class.getSimpleName();
    public static final String TABLE = "Image";

    //label Table Columns names
    public static final String KEY_ImageID = "ImageId";
    public static final String KEY_Name = "Name";
    public static final String KEY_Data = "Data";
    public static final String KEY_SubjectId = "SubjectId";

    private String imageId;  //Autoincrement ID
    private String name;
    private byte[] data; //Image data type byte and in database it would be type of 'Blob'
    private String subjectId;

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }
}
