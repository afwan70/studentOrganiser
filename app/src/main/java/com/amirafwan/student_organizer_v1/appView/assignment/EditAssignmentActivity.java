package com.amirafwan.student_organizer_v1.appView.assignment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.amirafwan.student_organizer_v1.data.model.Assignment;
import com.amirafwan.student_organizer_v1.data.repo.AssignmentRepo;
import com.amirafwan.student_organizer_v1.viewModel.AssignmentList;
import com.amirafwan.student_organizer_v1.R;

import java.util.Calendar;

public class EditAssignmentActivity extends AppCompatActivity {

    private String passedAssignmentId;
    private static final String TAG = "EditAssignmentActivity";

    private EditText etAssignmentName, etAssignmentDescription, etAssignmentDateDue;
    private ImageView mIcon;
    private Button btnUpdate;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assignment);

        Intent intent = getIntent();
        passedAssignmentId = intent.getExtras().getString("assignmentId");
        Log.d(TAG, "The passed id is : " + passedAssignmentId);

        etAssignmentName = (EditText) findViewById(R.id.etAssignmentName);
        etAssignmentDescription = (EditText) findViewById(R.id.etAssignmentDescription);
        etAssignmentDateDue = (EditText) findViewById(R.id.etAssignmentDateDue);
        mIcon = (ImageView) findViewById(R.id.ivEditAssignmentDatePicker);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);

        //setting up the calendar picker dialog
        setupCalendarPicker();

        fillTheForm(passedAssignmentId);

        initCustomActionBar();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inName = etAssignmentName.getText().toString().trim();
                String inDescription = etAssignmentDescription.getText().toString().trim();
                String inDateDue = etAssignmentDateDue.getText().toString().trim();
                callUpdateAssignment(passedAssignmentId, inName, inDescription, inDateDue);
            }
        });

    }


    //init custom action bar
    private void initCustomActionBar(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Edit Assignment");
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


    private void callUpdateAssignment(String id, String name, String description, String dateDue) {

        AssignmentRepo assignmentRepo = new AssignmentRepo();
        Assignment assignment = new Assignment();

        assignment.setName(name);
        assignment.setDescription(description);
        assignment.setDateDue(dateDue);

        assignmentRepo.updateById(id, name, description, dateDue);

        //Intent
        //Intent i = new Intent(EditAssignmentActivity.this, AssignmentActivity.class);
        finish();
        //startActivity(i);

    }


    private void setupCalendarPicker(){
        mIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        EditAssignmentActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Log.d(TAG, "New Date : " + year + "/" + (month + 1) + "/" + day);
                String date = (month + 1) + "/" + day + "/" + year; //x tau sbb ape month kene tmbh 1..sbb klu x tmbh die akn start with 0!
                etAssignmentDateDue.setText(date);
            }
        };
    }


    public void fillTheForm(String id){
        AssignmentRepo assignmentRepo = new AssignmentRepo();
        AssignmentList assignmentList = assignmentRepo.getAssignmentById(id);

        etAssignmentName.setText(assignmentList.getName());
        etAssignmentDescription.setText(assignmentList.getDescription());
        etAssignmentDateDue.setText(assignmentList.getDateDue());
    }
}
