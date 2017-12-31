package com.amirafwan.student_organizer_v1.appView.subjectItem.image;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.amirafwan.student_organizer_v1.data.model.Image;
import com.amirafwan.student_organizer_v1.data.repo.ImageRepo;
import com.amirafwan.student_organizer_v1.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddImageActivity extends AppCompatActivity {

    EditText etImageName;
    ImageView imageView;
    Button btnAdd, btnPick;

    private static final String TAG = "ActivityAddImageTAG";
    private String passedSubjectId;

    final int REQUEST_CODE_GALLERY = 999; //dunno for what function...tp utk dalam manifest kt bwh ni


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image);

        //Call the intent to get the passedSubjectId from intent extras!!
        Intent intent = getIntent();
        passedSubjectId = intent.getExtras().getString("selectedSubjectId");
        Log.d(TAG, "The passed id now is : " + passedSubjectId);


        etImageName = (EditText) findViewById(R.id.etImageName);
        imageView = (ImageView) findViewById(R.id.imageView);
        btnPick = (Button) findViewById(R.id.btnPickImage);
        btnAdd = (Button) findViewById(R.id.btnAddImage);

        //call the custom action bar
        initCustomActionBar();

        btnPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //tgk balik...maybe kite boleh setting direct dalam Android Manifest je kot, kot laa!!
                ActivityCompat.requestPermissions(
                        AddImageActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get byte data of image...turn image to byte...
                byte[] data = imageViewToByte(imageView);
                Log.d(TAG, "Data byte : " + data);

                //Inserting to database...
                ImageRepo imageRepo = new ImageRepo();
                Image image = new Image();

                image.setName(etImageName.getText().toString().trim());
                image.setData(data);
                image.setSubjectId(passedSubjectId);

                imageRepo.insert(image);

                Log.d(TAG, "Successfully inserted image to database!");

                //Intent i = new Intent(AddImageActivity.this, ImageActivity.class);
                //i.putExtra("selectedSubjectId", passedSubjectId);
                finish();
                //startActivity(i);

            }
        });

    }

    //init custom action bar
    private void initCustomActionBar(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Add Image");
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



    //turn image to byte...
    private byte[] imageViewToByte(ImageView image){
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }


    //override function result for btnPick!!!
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(getApplicationContext(), "You dont have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return; //dunno why...
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
