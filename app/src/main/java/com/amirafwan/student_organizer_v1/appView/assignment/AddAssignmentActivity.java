package com.amirafwan.student_organizer_v1.appView.assignment;

import android.app.DatePickerDialog;
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
import com.amirafwan.student_organizer_v1.R;

import java.util.Calendar;

public class AddAssignmentActivity extends AppCompatActivity {

    private String TAG = AddAssignmentActivity.class.getSimpleName().toString();
    private EditText etAssignmentName, etAssignmentDescription, etAssignmentDateDue;
    private ImageView mIcon;
    private Button btnAdd;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);

        etAssignmentName = (EditText) findViewById(R.id.etAssignmentName);
        etAssignmentDescription = (EditText) findViewById(R.id.etAssignmentDescription);
        etAssignmentDateDue = (EditText) findViewById(R.id.etAssignmentDateDue);
        mIcon = (ImageView) findViewById(R.id.ivAddAssignmentDatePicker);
        btnAdd = (Button) findViewById(R.id.btnAdd);

        //setting up the calendar picker dialog
        setupCalendarPicker();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inName = etAssignmentName.getText().toString().trim();
                String inDescription = etAssignmentDescription.getText().toString().trim();
                String inDateDue = etAssignmentDateDue.getText().toString().trim();
                callInsertAssignment(inName, inDescription, inDateDue);
            }
        });

        initCustomActionBar();

    }

    //init custom action bar
    private void initCustomActionBar(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Add Assignment");
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

    private void callInsertAssignment(String name, String description, String dateDue) {

        AssignmentRepo assignmentRepo = new AssignmentRepo();
        Assignment assignment = new Assignment();

        assignment.setName(name);
        assignment.setDescription(description);
        assignment.setDateDue(dateDue);

        assignmentRepo.insert(assignment);

        //Intent
        //Intent i = new Intent(AddAssignmentActivity.this, AssignmentActivity.class);
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
                        AddAssignmentActivity.this,
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
}
