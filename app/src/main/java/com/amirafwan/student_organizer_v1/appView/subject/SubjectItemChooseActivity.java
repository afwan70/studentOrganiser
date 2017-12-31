package com.amirafwan.student_organizer_v1.appView.subject;

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

import com.amirafwan.student_organizer_v1.appView.subjectItem.audio.AudioActivity;
import com.amirafwan.student_organizer_v1.appView.subjectItem.image.ImageActivity;
import com.amirafwan.student_organizer_v1.appView.subjectItem.note.NoteActivity;
import com.amirafwan.student_organizer_v1.R;

public class SubjectItemChooseActivity extends AppCompatActivity {

    private String passedSubjectId;
    private static final String TAG = "SubjectItemChooseTAG";
    private TextView tvSelectedSubjectCode;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_item_choose);

        Intent intent = getIntent();
        passedSubjectId = intent.getExtras().getString("selectedSubjectId");
        Log.d(TAG, "The passed id is : " + passedSubjectId);

        listView = (ListView) findViewById(R.id.list_item_choose);
        tvSelectedSubjectCode = (TextView) findViewById(R.id.tvSelectedSubjectCode);
        tvSelectedSubjectCode.setText("Subject : " + passedSubjectId);


        //call the customactionbar
        initCustomActionBar();

        //call the string arrays of itemchoose
        String[] itemList = getResources().getStringArray(R.array.ItemChoose);
        //then call into inner class for listview inflater
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, itemList);
        listView.setAdapter(simpleAdapter);
    }

    //init custom action bar
    private void initCustomActionBar(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Subject Items");
        //getActionBar().setIcon(R.drawable.book);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Intent i = new Intent(AddAssignmentActivity.this, AssignmentActivity.class);
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

    public class SimpleAdapter extends BaseAdapter {

        private Context mContext;
        private LayoutInflater layoutInflater;
        private TextView tvItemChoose;
        private String[] itemList;

        public SimpleAdapter(Context context, String[] list){
            mContext = context;
            itemList = list;
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return itemList.length;
        }

        @Override
        public Object getItem(int position) {

            return itemList[position];
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView ==null){
                convertView = layoutInflater.inflate(R.layout.subject_item_choose_list, null);
            }

            tvItemChoose = (TextView)convertView.findViewById(R.id.tvItemChoose);
            tvItemChoose.setText(itemList[position]);

            //then do something...click2 ker
            if(itemList[position].equalsIgnoreCase("Notes")) {
                tvItemChoose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(SubjectItemChooseActivity.this, NoteActivity.class);
                        i.putExtra("selectedSubjectId", passedSubjectId);
                        startActivity(i);
                    }
                });
            } else if (itemList[position].equalsIgnoreCase("Images")) {
                tvItemChoose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(SubjectItemChooseActivity.this, ImageActivity.class);
                        i.putExtra("selectedSubjectId", passedSubjectId);
                        startActivity(i);
                    }
                });
            } else if (itemList[position].equalsIgnoreCase("Audios")) {
                tvItemChoose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(SubjectItemChooseActivity.this, AudioActivity.class);
                        i.putExtra("selectedSubjectId", passedSubjectId);
                        startActivity(i);
                    }
                });
            }
            //if(itemList[position].equalsIgnoreCase("Images"))
            //if(itemList[position].equalsIgnoreCase("Audios"))

            return convertView;
        }
    }
}
