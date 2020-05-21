package com.rabbitt.mahinsure;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.ArrayList;

public class PhotoActivity extends AppCompatActivity {

    private static final String TAG = "malu";

    private Uri imageUri;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        image = findViewById(R.id.imageView);
    }

    public void photo(View view) {
        Pix.start(this, Options.init().setRequestCode(100));
//        startActivity(new Intent(getApplicationContext(), PhotoActivity.class));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i(TAG, "onActivityResult: Requestcode: "+requestCode+" "+resultCode);

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
//                Log.i(TAG, "onActivityResult: 2" + imageUri);
//                Glide.with(this)
//                        .load(imageUri)
//                        .transition(GenericTransitionOptions
//                                .with(android.R.anim.slide_in_left))
//                        .into(imageView);
//                prefsManager.setProfileUrl(String.valueOf(imageUri));
//
//                uploadImage_fire();
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Glide.with(this)
                        .load(resultUri)
                        .into(image);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == 0)
        {
            Pix.start(this, Options.init().setRequestCode(100));
        }
    }
}
