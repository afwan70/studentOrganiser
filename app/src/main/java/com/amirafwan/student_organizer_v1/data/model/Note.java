package com.amirafwan.student_organizer_v1.data.model;



public class Note {

    public static final String TAG = Note.class.getSimpleName();
    public static final String TABLE = "Note";

    //label Table Columns names
    public static final String KEY_NoteID = "NoteId";
    public static final String KEY_Name = "Name";
    public static final String KEY_Description = "Description";
    public static final String KEY_SubjectId = "SubjectId";

    private String noteId;  //Autoincrement ID
    private String name;
    private String description;
    private String subjectId;

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
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

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }
}
