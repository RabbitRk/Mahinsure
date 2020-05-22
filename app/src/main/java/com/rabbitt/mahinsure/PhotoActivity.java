package com.rabbitt.mahinsure;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.rabbitt.mahinsure.adapter.GridDataAdpater;
import com.rabbitt.mahinsure.misc.GridSpacingItemDecoration;
import com.rabbitt.mahinsure.model.grid;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PhotoActivity extends AppCompatActivity implements GridAdapter.OnRecyleItemListener {

    private static final String TAG = "malu";

    private Uri imageUri;
    //    private ImageView image;
    GridAdapter customAdapter;
    List<grid> events;
    int position = 0;
    RecyclerView recyclerView;
//    ArrayList personNames = new ArrayList<>(Arrays.asList("Chassis", "Front number plate", "Rear image", "Front Bumper", "Rear Bumper", "Front left corner", "Front right corner", "Left side", "Right side", "Left QTR panel", "Right QTR panel", "Engine", "Chassis plate", "Dicky open", "Under chassis", "Dashboard", "Odometer", "CNG LPG kit", "RC copy", "Pre-insurance", "Dents & Scratches", "Dents & Scratches", "Dents & Scratches"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

//        image = findViewById(R.id.imageView);

        int spanCount = 3; // 3 columns
        int spacing = 10; // 50px
        // get the reference of RecyclerView
        recyclerView = findViewById(R.id.grid_recycler);
        // set a GridLayoutManager with 3 number of columns , horizontal gravity and false value for reverseLayout to show the items from start to end
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 3, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, true));

        //region under testing
//        List<String> eventName = new ArrayList<>();

        GridDataAdpater recyclerData = new GridDataAdpater(this);
        events = recyclerData.getEventList();

        //endregion

        customAdapter = new GridAdapter(PhotoActivity.this, events, this);
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i(TAG, "onActivityResult: Requestcode: " + requestCode + " " + resultCode);

        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            ArrayList<String> returnValue = null;
            if (data != null) {
                returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
            }
            Log.i(TAG, "onActivityResult: " + returnValue);
            if (returnValue != null) {
                imageUri = Uri.fromFile(new File(returnValue.get(0)));
                CropImage.activity(imageUri)
                        .start(this);
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            try
            {
                if (resultCode == RESULT_OK) {
                    Uri resultUri = result.getUri();

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);

                    Log.i(TAG, "onActivityResult: "+resultUri+" "+bitmap.toString());
                    grid model = events.get(position);
                    model.setImage(bitmap);

                    Log.i(TAG, "onActivityResult: "+position+"   "+model.getEvent_name());

                    for (grid model_ : events)
                    {
                        Log.i(TAG, ">>>>>>>>>Testing: "+model_.getEvent_name()+"  "+model_.getImage());
                    }


                    customAdapter.notifyDataSetChanged();


                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
            }
            catch (IOException | NullPointerException ex)
            {
                Log.i(TAG, "Exception: "+ex.getMessage());
            }

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == 0)
        {
            Pix.start(this, Options.init().setRequestCode(100));
        }
    }

    @Override
    public void OnItemClick(int position) {
        Log.i(TAG, "OnItemClick: "+position);

        this.position = position;

//        grid model = events.get(position);
////        String data = model.getDays();
//        model.setImage(imageUri);
////        events.set(position, model.setImage(imageUri));
//        customAdapter.notifyItemChanged(position);

//        customAdapter
//        customAdapter.notifyItemChanged(position);
        Pix.start(this, Options.init().setRequestCode(100));
    }
}
