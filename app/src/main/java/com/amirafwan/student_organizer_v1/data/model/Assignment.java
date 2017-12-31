package com.amirafwan.student_organizer_v1.data.model;


public class Assignment {

    public static final String TAG = Assignment.class.getSimpleName();
    public static final String TABLE = "Assignment";

    //label table column names
    public static final String KEY_AssignmentId = "AssignmentId";
    public static final String KEY_Name = "Name";
    public static final String KEY_Description = "Description";
    public static final String KEY_DateDue = "DateDue";

    private String assignmentId;  //Autoincrement ID
    private String name;
    private String description;
    private String dateDue;



    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateDue() {
        return dateDue;
    }

    public void setDateDue(String dateDue) {
        this.dateDue = dateDue;
    }
}
