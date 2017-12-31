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

import com.amirafwan.student_organizer_v1.data.model.Note;
import com.amirafwan.student_organizer_v1.data.repo.NoteRepo;
import com.amirafwan.student_organizer_v1.R;

public class AddNoteActivity extends AppCompatActivity {

    private EditText etNoteName, etNoteDescription;
    private Button btnAdd;

    private static final String TAG = "ActivityAddNoteTAG";
    private String passedSubjectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        Intent intent = getIntent();
        passedSubjectId = intent.getExtras().getString("selectedSubjectId");
        Log.d(TAG, "The passed id from add note is : " + passedSubjectId);

        etNoteName = (EditText) findViewById(R.id.etNoteName);
        etNoteDescription = (EditText) findViewById(R.id.etNoteDescription);
        btnAdd = (Button) findViewById(R.id.btnAddNote);


        //call the custom action bar
        initCustomActionBar();


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inName = etNoteName.getText().toString().trim();
                String inDescription = etNoteDescription.getText().toString().trim();
                callInsertNote(passedSubjectId, inName, inDescription);
            }
        });
    }



    //init custom action bar
    private void initCustomActionBar(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Add Note");
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



    private void callInsertNote(String subjectId, String name, String description) {

        NoteRepo noteRepo = new NoteRepo();
        Note note = new Note();

        note.setName(name);
        note.setDescription(description);
        note.setSubjectId(subjectId);

        noteRepo.insert(note);

        Log.d(TAG, "DONE INSERTING NEW NOTE");

        //Intent
        //Intent i = new Intent(AddNoteActivity.this, NoteActivity.class);
        //i.putExtra("selectedSubjectId", passedSubjectId);
        finish();
        //startActivity(i);

    }
}
