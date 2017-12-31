package com.amirafwan.student_organizer_v1.data.model;



public class Audio {

    public static final String TAG = Audio.class.getSimpleName();
    public static final String TABLE = "Audio";

    //label Table Columns names
    public static final String KEY_AudioID = "AudioId";
    public static final String KEY_Name = "Name";
    public static final String KEY_Path = "Path";
    public static final String KEY_SubjectId = "SubjectId";

    private String audioId;  //Autoincrement ID
    private String name;
    private String path;
    private String subjectId;

    public String getAudioId() {
        return audioId;
    }

    public void setAudioId(String audioId) {
        this.audioId = audioId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }
}
