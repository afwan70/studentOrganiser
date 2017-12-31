package com.amirafwan.student_organizer_v1.appView.subjectItem.audio;

import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amirafwan.student_organizer_v1.data.model.Audio;
import com.amirafwan.student_organizer_v1.data.repo.AudioRepo;
import com.amirafwan.student_organizer_v1.R;

import java.io.File;
import java.io.IOException;

public class AddAudioActivity extends AppCompatActivity {

    private static final String TAG = AddAudioActivity.class.getSimpleName().toString();
    private String passedSubjectId;

    private Button btnAddAudio;
    private TextView tvRecordLabel;
    private EditText etAudioName;

    private MediaRecorder mRecorder;
    private String parentDirectory = "AmirGroupApp"; //untuk skrg kite bg name ni..soon maybe name packagename ke n so on!
    private String mFileName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_audio);

        //Call the intent to get the passedSubjectId from intent extras!!
        Intent intent = getIntent();
        passedSubjectId = intent.getExtras().getString("selectedSubjectId");
        Log.d(TAG, "The passed id now is : " + passedSubjectId);

        tvRecordLabel = (TextView) findViewById(R.id.tvRecordLabel);
        etAudioName = (EditText) findViewById(R.id.etAudioName);
        btnAddAudio = (Button) findViewById(R.id.btnRecordAddAudio);

        //call the custom action bar
        initCustomActionBar();


        //Untuk mase skrg ni kite wak custom parent@packganame directory..nanti pndai2 laa uboh nk letok ape2!
        File theDir = new File(Environment.getExternalStorageDirectory().getPath().toString() + "/" + parentDirectory + "/Audio");
        //if the directory is not yet available..then create one.
        if(!theDir.isDirectory()){
            theDir = new File(Environment.getExternalStorageDirectory().getPath().toString() + "/" + parentDirectory);
            theDir.mkdir();
            theDir = new File(Environment.getExternalStorageDirectory().getPath().toString() + "/" + parentDirectory + "/Audio");
            theDir.mkdir();
        }


        //btn Listener
        btnAddAudio.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                    //path kite maybe = /externalstorage/packagename/{image/audio}/{subjectName_fileName}.{png/mp3}
                    mFileName = Environment.getExternalStorageDirectory().getPath() + "/" + parentDirectory + "/Audio/" + passedSubjectId + "_" + etAudioName.getText().toString().trim() + ".3gp";
                    //mFileName = Environment.getExternalStorageDirectory().getPath() + "/" + parentDirectory + "/Audio/" + etAudioName.getText().toString().trim() + ".3gp";
                    Log.d(TAG, "The file name path : " + mFileName);
                    //Log.d(TAG, "The package path : " + getPackageName());


                    //save the all data to database now..since we just store the path of the data
                    AudioRepo audioRepo = new AudioRepo();
                    Audio audio = new Audio();

                    audio.setName(etAudioName.getText().toString().trim());
                    audio.setPath(mFileName);
                    audio.setSubjectId(passedSubjectId);

                    audioRepo.insert(audio);
                    Log.d(TAG, "Successfully inserted path of data to database!!!");


                    //start recording whenever the user press and hold the button
                    startRecording();
                    tvRecordLabel.setText("Recording your audio now ....!!");


                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

                    //stop recording whenever the user unhold the button
                    stopRecording();
                    tvRecordLabel.setText("Recording has stopped!");
                    etAudioName.setText("");
                }

                return false;
            }
        });

    }

    //init custom action bar
    private void initCustomActionBar(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Add Audio");
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

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP); //boleh tukor2 ke format lain cam mp3 ke etc...or juz set DEFAULT...tgk r gane2 !!!
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }


}
