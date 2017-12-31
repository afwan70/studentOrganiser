package com.amirafwan.student_organizer_v1.data.model;



public class Subject {

    public static final String TAG = Subject.class.getSimpleName();
    public static final String TABLE = "Subject";

    //label table column names
    public static final String KEY_SubjectId = "SubjectId";  //not autoincrement id...nanti subject ade Unique id die sendiri !!!
    public static final String KEY_Name = "Name";

    private String subjectId;
    private String name;

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
