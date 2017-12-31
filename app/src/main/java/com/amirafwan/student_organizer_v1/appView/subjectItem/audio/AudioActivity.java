package com.amirafwan.student_organizer_v1.appView.subjectItem.audio;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.amirafwan.student_organizer_v1.data.repo.AudioRepo;
import com.amirafwan.student_organizer_v1.viewModel.AudioList;
import com.amirafwan.student_organizer_v1.R;

import java.io.IOException;
import java.util.List;

public class AudioActivity extends AppCompatActivity {

    private static final String TAG = AudioActivity.class.getSimpleName().toString();
    private String passedSubjectId;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        //Call the intent to get the passedSubjectId from intent extras!!
        Intent intent = getIntent();
        passedSubjectId = intent.getExtras().getString("selectedSubjectId");
        Log.d(TAG, "The passed id now is : " + passedSubjectId);

        //call the custom action bar
        initCustomActionBar();

        listView = (ListView) findViewById(R.id.list_audio);

        //setupListView()
        setupListView();
    }

    //init custom action bar
    private void initCustomActionBar(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("List of Audios");
        //getActionBar().setIcon(R.drawable.book);
    }

    //render the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add:
                Log.d(TAG, "Add a new audio!");

                Intent i = new Intent(AudioActivity.this, AddAudioActivity.class);
                i.putExtra("selectedSubjectId", passedSubjectId);
                //finish();
                startActivity(i);

                return true;

            case android.R.id.home:
                //Intent ii = new Intent(AssignmentActivity.this, MainActivity.class);
                finish();
                //startActivity(ii);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setupListView() {
        AudioRepo audioRepo = new AudioRepo();
        List<AudioList> audioLists = audioRepo.getAudiosBasedOnSubject(passedSubjectId);

        //call the SimpleAdapter inner class
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, audioLists);
        listView.setAdapter(simpleAdapter);
    }


    //resume when the user finish activity on the next activity!
    //then, reload the setupListView()
    @Override
    protected void onResume() {
        super.onResume();
        setupListView();
    }


    public class SimpleAdapter extends BaseAdapter {

        private Context mContext;
        private LayoutInflater layoutInflater;
        private TextView tvAudioName, tvClickDelete;
        private Button btnPlayAudio;
        //nanti ade kene letok seekbar
        private List<AudioList> mAudioLists;

        private MediaPlayer mediaPlayer;

        public SimpleAdapter(Context context, List<AudioList> audioLists){
            mContext = context;
            mAudioLists = audioLists;
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mAudioLists.size();
        }

        @Override
        public Object getItem(int position) {
            return mAudioLists.get(position).getName();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if(convertView == null) {
                convertView = layoutInflater.inflate(R.layout.audio_list_all, null);
            }

            tvAudioName = (TextView)convertView.findViewById(R.id.tvAudioName);
            tvClickDelete = (TextView)convertView.findViewById(R.id.tvClickDeleteAudio);
            btnPlayAudio = (Button)convertView.findViewById(R.id.btnPlayAudio);

            Log.d(TAG, "Audio Name : "+ mAudioLists.get(position).getName());
            tvAudioName.setText(mAudioLists.get(position).getName());

            mediaPlayer = new MediaPlayer();
            final String audio_path = mAudioLists.get(position).getPath();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            final Uri uri = Uri.parse(audio_path);


            btnPlayAudio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        mediaPlayer.setDataSource(getApplicationContext(), uri);
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                        Log.d(TAG, "Playback started");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });

            tvClickDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AudioRepo audioRepo = new AudioRepo();
                    String _id = mAudioLists.get(position).getAudioId();

                    audioRepo.deleteById(_id);

                    //setupListView()
                    setupListView();
                }
            });

            return convertView;
        }
    }
}
