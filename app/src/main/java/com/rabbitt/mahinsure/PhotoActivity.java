package com.rabbitt.mahinsure;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.rabbitt.mahinsure.adapter.GridAdapter;
import com.rabbitt.mahinsure.misc.GridSpacingItemDecoration;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class PhotoActivity extends AppCompatActivity {

    private static final String TAG = "malu";

    private Uri imageUri;
//    private ImageView image;
    ArrayList personNames = new ArrayList<>(Arrays.asList("Person 1", "Person 2", "Person 3", "Person 4", "Person 5", "Person 6", "Person 7","Person 8", "Person 9", "Person 10", "Person 11", "Person 12", "Person 13", "Person 14"));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

//        image = findViewById(R.id.imageView);

        int spanCount = 3; // 3 columns
        int spacing = 10; // 50px
        // get the reference of RecyclerView
        RecyclerView recyclerView = findViewById(R.id.grid_recycler);
        // set a GridLayoutManager with 3 number of columns , horizontal gravity and false value for reverseLayout to show the items from start to end
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, true));
        GridAdapter customAdapter = new GridAdapter(PhotoActivity.this, personNames);
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
    }

    public void photo(View view) {
        Pix.start(this, Options.init().setRequestCode(100));
//        startActivity(new Intent(getApplicationContext(), PhotoActivity.class));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        Log.i(TAG, "onActivityResult: Requestcode: "+requestCode+" "+resultCode);
//
//        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
//            ArrayList<String> returnValue = null;
//            if (data != null) {
//                returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
//            }
//            Log.i(TAG, "onActivityResult: " + returnValue);
//            if (returnValue != null) {
//                imageUri = Uri.fromFile(new File(returnValue.get(0)));
//
//                CropImage.activity(imageUri)
//                        .start(this);
//            }
//        }
//
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//
//            try
//            {
//                if (resultCode == RESULT_OK) {
//                    Uri resultUri = result.getUri();
//                    Glide.with(this)
//                            .load(resultUri)
//                            .into(image);
//                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                    Exception error = result.getError();
//                }
//            }
//            catch(NullPointerException e)
//            {
//                Log.i(TAG, "Exception: "+e.getMessage());
//            }
//
//        }
//
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == 0)
//        {
//            Pix.start(this, Options.init().setRequestCode(100));
//        }
    }
}
