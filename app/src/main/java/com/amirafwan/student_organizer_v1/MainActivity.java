package com.amirafwan.student_organizer_v1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.amirafwan.student_organizer_v1.appView.assignment.AssignmentActivity;
import com.amirafwan.student_organizer_v1.appView.subject.SubjectActivity;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUIViews();
        initCustomActionBar();
        setupListView();

    }

    private void setupUIViews(){
        listView = (ListView) findViewById(R.id.list_Main);
    }


    //init custom action bar
    private void initCustomActionBar(){
        setTitle("Student Course App");
        //getActionBar().setIcon(R.drawable.book);
    }



    private void setupListView() {
        String[] name = getResources().getStringArray(R.array.MainChoose_Name);
        String[] description = getResources().getStringArray(R.array.MainChoose_Description);

        //call the inner class
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, name, description);
        listView.setAdapter(simpleAdapter);
    }



    //inner class
    public class SimpleAdapter extends BaseAdapter {

        private Context mContext;
        private LayoutInflater layoutInflater;
        private TextView tvName, tvDescription;
        private String[] nameArray;
        private String[] descriptionArray;
        private ImageView imageView;

        public SimpleAdapter(Context context, String[] name, String[] description) {
            mContext = context;
            nameArray = name;
            descriptionArray = description;
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return nameArray.length;
        }

        @Override
        public Object getItem(int position) {
            return nameArray[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null){
                convertView = layoutInflater.inflate(R.layout.main_activity_list, null);
            }

            tvName = (TextView)convertView.findViewById(R.id.tvMainName);
            tvDescription = (TextView)convertView.findViewById(R.id.tvMainDescription);
            imageView = (ImageView) convertView.findViewById(R.id.ivMain);

            tvName.setText(nameArray[position]);
            tvDescription.setText(descriptionArray[position]);

            if(nameArray[position].equalsIgnoreCase("Assignment")){
                imageView.setImageResource(R.drawable.calendar);

                //set to go
                tvName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(MainActivity.this, AssignmentActivity.class);
                        //finish();
                        startActivity(i);
                    }
                });
            } else if(nameArray[position].equalsIgnoreCase("Subject")){
                imageView.setImageResource(R.drawable.book);

                //set to go
                tvName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Intent i = new Intent(MainActivity.this, SubjectActivity.class);
                        Intent i = new Intent(getApplicationContext(), SubjectActivity.class);
                        //finish();
                        startActivity(i);
                    }
                });
            }

            return convertView;
        }
    }
}
