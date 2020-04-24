package com.rabbitt.mahinsure;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;

public class Nextpage extends AppCompatActivity {

    private static final String TAG = "malu";
    SkeletonScreen skeletonScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nextpage);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Log.i(TAG, "onCreate: ");

        ViewGroup viewGroup = findViewById(R.id.skim);

        skeletonScreen = Skeleton.bind(viewGroup)
                .load(R.layout.info_skim)
                .color(R.color.white)
                .show();

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Log.i(TAG, "onBackPressed: ");
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }

    public void photo(View view) {
        skeletonScreen.hide();
//        startActivity(new Intent(this, Upload.class));
    }
}
