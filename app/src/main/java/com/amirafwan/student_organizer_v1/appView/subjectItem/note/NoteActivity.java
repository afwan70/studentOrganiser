package com.amirafwan.student_organizer_v1.appView.subjectItem.note;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.amirafwan.student_organizer_v1.data.repo.NoteRepo;
import com.amirafwan.student_organizer_v1.viewModel.NoteList;
import com.amirafwan.student_organizer_v1.R;

import java.util.List;

public class NoteActivity extends AppCompatActivity {

    private static final String TAG = "ActivityNoteTAG";
    private String passedSubjectId;

    private ListView mNoteListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        Intent intent = getIntent();
        passedSubjectId = intent.getExtras().getString("selectedSubjectId");
        Log.d(TAG, "The passed id now is : " + passedSubjectId);

        //call the custom action bar
        initCustomActionBar();

        mNoteListView = (ListView) findViewById(R.id.list_note);

        setupListView();

    }

    //init custom action bar
    private void initCustomActionBar(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("List of Notes");
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
                Log.d(TAG, "Add a new notes");

                Intent i = new Intent(NoteActivity.this, AddNoteActivity.class);
                i.putExtra("selectedSubjectId", passedSubjectId);
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

    private void setupListView() {

        NoteRepo noteRepo = new NoteRepo();
        List<NoteList> noteLists = noteRepo.getNotesBasedOnSubject(passedSubjectId);

        //call the SimpleAdapter inner class
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, noteLists);
        mNoteListView.setAdapter(simpleAdapter);

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
        private TextView tvId, tvName, tvClickDelete;
        private List<NoteList> mNoteLists;

        public SimpleAdapter(Context context, List<NoteList> noteLists){
            mContext = context;
            mNoteLists = noteLists;
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mNoteLists.size();
        }

        @Override
        public Object getItem(int position) {
            return mNoteLists.get(position).getName();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if(convertView == null){
                convertView = layoutInflater.inflate(R.layout.note_list_all, null);
            }

            tvId = (TextView)convertView.findViewById(R.id.tvNoteId);
            tvName = (TextView)convertView.findViewById(R.id.tvNoteName);
            tvClickDelete = (TextView)convertView.findViewById(R.id.tvClickDelete);

            tvId.setText(mNoteLists.get(position).getNoteId());
            tvName.setText(mNoteLists.get(position).getName());

            //then...do something...
            tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String _id = mNoteLists.get(position).getNoteId();

                    Intent i = new Intent(NoteActivity.this, EditNoteActivity.class);
                    i.putExtra("passedNoteId", _id);
                    startActivity(i);
                }
            });


            //Delete selected item
            tvClickDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    View mParent = (View) view.getParent();
                    //TextView txtId = (TextView) mParent.findViewById(R.id.tvId);
                    //String _id = String.valueOf(txtId.getText());
                    String _id = mNoteLists.get(position).getNoteId();
                    Log.d(TAG, "This object with this id will be deleted : "+ _id);

                    //call the dtbs
                    NoteRepo noteRepo = new NoteRepo();
                    noteRepo.deleteById(_id);

                    setupListView();

                }
            });

            return convertView;
        }
    }

}
