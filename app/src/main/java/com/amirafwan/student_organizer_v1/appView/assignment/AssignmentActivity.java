package com.amirafwan.student_organizer_v1.appView.assignment;

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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.amirafwan.student_organizer_v1.data.repo.AssignmentRepo;
import com.amirafwan.student_organizer_v1.viewModel.AssignmentList;
import com.amirafwan.student_organizer_v1.R;

import java.util.List;

public class AssignmentActivity extends AppCompatActivity {

    private static final String TAG = "AssignmentActivity";
    private ListView mAssignmentListView;

    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

        mAssignmentListView = (ListView) findViewById(R.id.list_assignment);

        //call the custom action bar
        initCustomActionBar();
        //update the list
        setupListView();

    }

    //init custom action bar
    private void initCustomActionBar(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("List of Assignments");
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
                Log.d(TAG, "Add a new assignment");

                Intent i = new Intent(AssignmentActivity.this, AddAssignmentActivity.class);
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


    private void setupListView(){

        AssignmentRepo assignmentRepo = new AssignmentRepo();
        List<AssignmentList> assignmentLists = assignmentRepo.getAllAssignments();

        //call the SimpleAdapter inner class
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, assignmentLists);
        mAssignmentListView.setAdapter(simpleAdapter);
    }

    //resume when the user finish activity on the next activity!
    //then, reload the setupListView()
    @Override
    protected void onResume() {
        super.onResume();
        setupListView();
    }

    //inner class
    public class SimpleAdapter extends BaseAdapter {

        private Context mContext;
        private LayoutInflater layoutInflater;
        private TextView tvId, tvName, tvDateDue, tvClickDelete;
        private List<AssignmentList> mAssignmentLists;

        public SimpleAdapter(Context context, List<AssignmentList> assignmentLists){
            mContext = context;
            mAssignmentLists = assignmentLists;
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mAssignmentLists.size();
        }

        @Override
        public Object getItem(int position) {
            return mAssignmentLists.get(position).getName();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null){
                convertView = layoutInflater.inflate(R.layout.assignment_list_all, null);
            }

            tvId = (TextView) convertView.findViewById(R.id.tvId);  //we set visibility as GONE...bru x napok ID orrr !!!
            tvName = (TextView) convertView.findViewById(R.id.tvName);
            tvDateDue = (TextView) convertView.findViewById(R.id.tvDateDue);
            tvClickDelete = (TextView) convertView.findViewById(R.id.tvClickDelete);

            tvId.setText(mAssignmentLists.get(position).getAssignmentId());
            tvName.setText(mAssignmentLists.get(position).getName());
            tvDateDue.setText(mAssignmentLists.get(position).getDateDue());

            //click the tvName to go to update
            tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //grab the id first...then send it!
                    View mParent = (View) view.getParent();
                    TextView txtId = (TextView) mParent.findViewById(R.id.tvId);
                    String _id = String.valueOf(txtId.getText());
                    Log.d(TAG, "The id : "+ _id);

                    Intent i = new Intent(AssignmentActivity.this, EditAssignmentActivity.class);
                    i.putExtra("assignmentId", _id);
                    startActivity(i);
                }
            });

            //Delete selected item
            tvClickDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    View mParent = (View) view.getParent();
                    TextView txtId = (TextView) mParent.findViewById(R.id.tvId);
                    String _id = String.valueOf(txtId.getText());
                    Log.d(TAG, "The id : "+ _id);

                    //call the dtbs
                    AssignmentRepo assignmentRepo = new AssignmentRepo();
                    assignmentRepo.deleteById(_id);

                    setupListView();

                }
            });

            return convertView;
        }
    }


}
