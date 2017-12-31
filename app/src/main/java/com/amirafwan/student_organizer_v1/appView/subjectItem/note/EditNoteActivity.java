package com.amirafwan.student_organizer_v1.appView.subjectItem.note;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amirafwan.student_organizer_v1.data.repo.NoteRepo;
import com.amirafwan.student_organizer_v1.viewModel.NoteList;
import com.amirafwan.student_organizer_v1.R;

public class EditNoteActivity extends AppCompatActivity {

    private EditText etNoteName, etNoteDescription;
    private Button btnUpdate;

    private static final String TAG = EditNoteActivity.class.getSimpleName().toString();
    private String passedNoteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        Intent intent = getIntent();
        passedNoteId = intent.getExtras().getString("passedNoteId");
        Log.d(TAG, "The passed id from note is : " + passedNoteId);

        etNoteName = (EditText) findViewById(R.id.etNoteName);
        etNoteDescription = (EditText) findViewById(R.id.etNoteDescription);
        btnUpdate = (Button) findViewById(R.id.btnUpdateNote);


        //fill the form first!!!
        fillTheForm(passedNoteId);

        //call the custom action bar
        initCustomActionBar();


        //testing...
        Log.d(TAG, "Subject Id is : " + getSubjectId(passedNoteId));

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inName = etNoteName.getText().toString().trim();
                String inDescription = etNoteDescription.getText().toString().trim();
                callUpdateNote(passedNoteId, inName, inDescription, getSubjectId(passedNoteId));
            }
        });
    }



    //init custom action bar
    private void initCustomActionBar(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Update Note");
        //getActionBar().setIcon(R.drawable.book);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Intent i = new Intent(AddAudioActivity.this, AudioActivity.class);
                //i.putExtra("selectedSubjectId", passedSubjectId);
                finish();
                //startActivity(i);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }



    private void callUpdateNote(String noteId, String name, String description, String subjectId) {

        NoteRepo noteRepo = new NoteRepo();

        noteRepo.updateById(noteId, name, description, subjectId);

        Log.d(TAG, "DONE UPDATING NOTE");

        //Intent
        //Intent i = new Intent(AddNoteActivity.this, NoteActivity.class);
        //i.putExtra("selectedSubjectId", passedSubjectId);
        finish();
        //startActivity(i);

    }


    public void fillTheForm(String id){
        NoteRepo noteRepo = new NoteRepo();
        NoteList noteList = noteRepo.getNoteById(id);

        etNoteName.setText(noteList.getName());
        etNoteDescription.setText(noteList.getDescription());
    }

    public String getSubjectId(String id){
        NoteRepo noteRepo = new NoteRepo();
        NoteList noteList = noteRepo.getNoteById(id);
        return noteList.getSubjectId();
    }
}
