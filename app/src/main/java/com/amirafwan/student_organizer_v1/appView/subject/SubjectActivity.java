package com.amirafwan.student_organizer_v1.appView.subject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.amirafwan.student_organizer_v1.data.model.Subject;
import com.amirafwan.student_organizer_v1.data.repo.AudioRepo;
import com.amirafwan.student_organizer_v1.data.repo.ImageRepo;
import com.amirafwan.student_organizer_v1.data.repo.NoteRepo;
import com.amirafwan.student_organizer_v1.data.repo.SubjectRepo;
import com.amirafwan.student_organizer_v1.viewModel.SubjectList;
import com.amirafwan.student_organizer_v1.R;

import java.util.List;

public class SubjectActivity extends AppCompatActivity {

    LayoutInflater inflater;
    private static final String TAG = "SubjectActivity";
    private ListView mSubjectListView;

    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        mSubjectListView = (ListView) findViewById(R.id.list_subject);

        //inflater
        inflater = SubjectActivity.this.getLayoutInflater();

        initCustomActionBar();
        //update the list
        setupListView();
    }

    //init custom action bar
    private void initCustomActionBar(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("List of Subjects");
        //getActionBar().setIcon(R.drawable.book);

    }

    private void setupListView(){

        SubjectRepo subjectRepo = new SubjectRepo();
        List<SubjectList> subjectLists = subjectRepo.getAllSubjects();

        //call the SimpleAdapter inner class
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, subjectLists);
        mSubjectListView.setAdapter(simpleAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Log.d(TAG, "Add a new subject");

                View view = inflater.inflate(R.layout.subject_add_dialog, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(SubjectActivity.this);

                //Initiate EditText here
                final EditText subjectCode = (EditText)view.findViewById(R.id.etSubjectCode);
                final EditText subjectName = (EditText)view.findViewById(R.id.etSubjectName);
                final Button btnAddSubject = (Button)view.findViewById(R.id.btnAddSubject);

                builder.setView(view);
                final AlertDialog alertDialog = builder.create();

                //Action for btn onclick listener
                btnAddSubject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Log.d(TAG, "Test for subjectCode input = " + subjectCode.getText());

                        //insert into Table Subject here
                        SubjectRepo subjectRepo = new SubjectRepo();
                        Subject subject = new Subject();

                        subject.setSubjectId(subjectCode.getText().toString());
                        subject.setName(subjectName.getText().toString());
                        //Log.d(TAG, "Test for subjectCode input = " + subject.getSubjectId());
                        //Log.d(TAG, "Test for subjectName input = " + subject.getName());

                        subjectRepo.insert(subject);

                        //dismiss the alertDialog
                        alertDialog.dismiss();

                        //update the list
                        setupListView();
                    }
                });

                alertDialog.show();

                return true;

            case android.R.id.home:
                //Intent i = new Intent(SubjectActivity.this, MainActivity.class);
                finish();
                //startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public class SimpleAdapter extends BaseAdapter {

        private Context mContext;
        private LayoutInflater layoutInflater;
        private TextView tvSubjectCode, tvSubjectName, tvClickEdit, tvClickDelete;
        private List<SubjectList> mSubjectLists;

        public SimpleAdapter(Context context, List<SubjectList> assignmentLists){
            mContext = context;
            mSubjectLists = assignmentLists;
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mSubjectLists.size();
        }

        @Override
        public Object getItem(int position) {
            return mSubjectLists.get(position).getName();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null) {
                convertView = layoutInflater.inflate(R.layout.subject_list_all, null);
            }

            tvSubjectCode = (TextView)convertView.findViewById(R.id.tvSubjectCode);
            tvSubjectName = (TextView)convertView.findViewById(R.id.tvSubjectName);
            tvClickEdit = (TextView)convertView.findViewById(R.id.tvClickEditSubject);
            tvClickDelete = (TextView)convertView.findViewById(R.id.tvClickDeleteSubject);

            tvSubjectCode.setText((mSubjectLists.get(position).getSubjectId()).toUpperCase());
            tvSubjectName.setText(Character.toUpperCase(mSubjectLists.get(position).getName().charAt(0)) + mSubjectLists.get(position).getName().substring(1));
            //just want to make it look customized!!!


            //Edit selected item
            //Using Custom Dialog
            tvClickEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //grab the id first...then send it!
                    View mParent = (View) view.getParent();
                    TextView txtId = (TextView) mParent.findViewById(R.id.tvSubjectCode);
                    String _id = String.valueOf(txtId.getText());
                    Log.d(TAG, "The id : "+ _id);

                    view = inflater.inflate(R.layout.subject_edit_dialog, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(SubjectActivity.this);

                    //Initiate EditText here
                    final TextView subjectCode = (TextView)view.findViewById(R.id.etSubjectCode);
                    final EditText subjectName = (EditText)view.findViewById(R.id.etSubjectName);
                    final Button btnEditSubject = (Button)view.findViewById(R.id.btnEditSubject);


                    //fill the form first...
                    SubjectRepo subjectRepo1 = new SubjectRepo();
                    SubjectList subjectList = subjectRepo1.getSubjectById(_id);
                    subjectCode.setText(subjectList.getSubjectId());
                    subjectName.setText(subjectList.getName());

                    builder.setView(view);
                    final AlertDialog alertDialog = builder.create();

                    //Action for edit btn onclick listener
                    btnEditSubject.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Log.d(TAG, "Test for subjectCode input = " + subjectCode.getText());

                            //update into Table Subject here
                            SubjectRepo subjectRepo2 = new SubjectRepo();
                            subjectRepo2.updateById(subjectCode.getText().toString(), subjectName.getText().toString());

                            //dismiss the alertDialog
                            alertDialog.dismiss();

                            //update the list
                            setupListView();
                        }
                    });

                    alertDialog.show();
                }
            });


            //Delete selected item
            tvClickDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    View mParent = (View) view.getParent();
                    TextView txtId = (TextView) mParent.findViewById(R.id.tvSubjectCode);
                    final String _id = String.valueOf(txtId.getText());
                    Log.d(TAG, "The id : "+ _id);


                    AlertDialog dialog = new AlertDialog.Builder(SubjectActivity.this)
                            .setTitle("Subject " + _id + " will be deleted!")
                            .setMessage("Please note that all the notes, images and audios in subject of "+ _id + " will permanently be deleted. ")
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    //Delete data of every tables that belongs to Subject Table
                                    NoteRepo noteRepo = new NoteRepo();
                                    ImageRepo imageRepo = new ImageRepo();
                                    AudioRepo audioRepo = new AudioRepo();
                                    noteRepo.deleteBySubjectId(_id);
                                    Log.d(TAG, "Note of Subjectid "+ _id + " is successfully deleted! ");
                                    imageRepo.deleteBySubjectId(_id);
                                    Log.d(TAG, "Image of Subjectid "+ _id + " is successfully deleted! ");
                                    audioRepo.deleteBySubjectId(_id);
                                    Log.d(TAG, "Audio of Subjectid "+ _id + " is successfully deleted! ");

                                    //Then delete the data of subject table
                                    SubjectRepo subjectRepo = new SubjectRepo();
                                    subjectRepo.deleteById(_id);
                                    Log.d(TAG, "SubjectId "+ _id + " is successfully deleted! ");

                                    setupListView();

                                }

                            })
                            .setNegativeButton("Cancel", null)
                            .create();

                    dialog.show();
                }
            });

            tvSubjectCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    View mParent = (View) view.getParent();
                    TextView txtId = (TextView) mParent.findViewById(R.id.tvSubjectCode);
                    String _id = String.valueOf(txtId.getText());
                    Log.d(TAG, "The id : "+ _id);

                    Intent i = new Intent(SubjectActivity.this, SubjectItemChooseActivity.class);
                    i.putExtra("selectedSubjectId", _id);
                    startActivity(i);
                }
            });

            return convertView;
        }
    }

}
