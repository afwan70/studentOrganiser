package com.amirafwan.student_organizer_v1.appView.subjectItem.image;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.amirafwan.student_organizer_v1.data.repo.ImageRepo;
import com.amirafwan.student_organizer_v1.viewModel.ImageList;
import com.amirafwan.student_organizer_v1.R;

import java.util.List;

public class ImageActivity extends AppCompatActivity {

    private static final String TAG = "ActivityImageTAG";
    private String passedSubjectId;

    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);


        //Call the intent to get the passedSubjectId from intent extras!!
        Intent intent = getIntent();
        passedSubjectId = intent.getExtras().getString("selectedSubjectId");
        Log.d(TAG, "The passed id now is : " + passedSubjectId);

        //call the custom action bar
        initCustomActionBar();

        gridView = (GridView) findViewById(R.id.gridView);

        setupGridView();

    }

    //init custom action bar
    private void initCustomActionBar(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("List of Images");
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
                Log.d(TAG, "Add a new image!");

                Intent i = new Intent(ImageActivity.this, AddImageActivity.class);
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


    private void setupGridView(){
        ImageRepo imageRepo = new ImageRepo();
        List<ImageList> imageLists = imageRepo.getImagesBasedOnSubject(passedSubjectId);

        //call the SimpleAdapter inner class
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, imageLists);
        gridView.setAdapter(simpleAdapter);
    }


    //resume when the user finish activity on the next activity!
    //then, reload the setupListView()
    @Override
    protected void onResume() {
        super.onResume();
        setupGridView();
    }



    public class SimpleAdapter extends BaseAdapter {

        private Context mContext;
        private LayoutInflater layoutInflater;
        private TextView tvImageName;
        private ImageView imageView;
        private List<ImageList> mImageLists;

        public SimpleAdapter(Context context, List<ImageList> imageLists){
            mContext = context;
            mImageLists = imageLists;
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mImageLists.size();
        }

        @Override
        public Object getItem(int position) {
            return mImageLists.get(position).getName();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null){
                convertView = layoutInflater.inflate(R.layout.image_list_all, null);
            }

            tvImageName = (TextView)convertView.findViewById(R.id.tvImageName);
            imageView = (ImageView)convertView.findViewById(R.id.imageViewItem);

            tvImageName.setText(mImageLists.get(position).getName());
            byte[] imageData = mImageLists.get(position).getData();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
            imageView.setImageBitmap(bitmap);

            return convertView;
        }
    }
}
