package com.rabbitt.mahinsure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "maluk";
    ImageView imageView;
    TextView txt;
    Animation topAnim, scaleanim, txtAnim;
    ViewGroup view;

    private boolean isAnimated = false;

    Handler handler = new Handler();

    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            imageView.animate().translationX(0f).translationY(-400f).scaleXBy(-0.2f).scaleYBy(-0.2f).setDuration(2000);
            txt.animate().translationX(0f).translationY(-400f).setDuration(2000);

            scaleanim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.scale);
            view.setVisibility(View.VISIBLE);
            view.startAnimation(scaleanim);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Enable full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // View Initialization
        imageView = findViewById(R.id.image);
        txt = findViewById(R.id.textView);

        view = findViewById(R.id.collector);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!isAnimated)
        {
            //Animate the falling logo
            topAnim = AnimationUtils.loadAnimation(this, R.anim.fadein);
            txtAnim = AnimationUtils.loadAnimation(this, R.anim.fadeup);


            //loading the animation
            view.setAnimation(txtAnim);
            imageView.setAnimation(topAnim);


            //delayed the transition
            handler.postDelayed(runnable, 3000);
            isAnimated = true;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "Main onPause: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "Main onResume: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Main onDestroy: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "Main onStop: ");
        this.finish();
    }

    public void trans(View view) {
            //region Shared animation

            // Shared screen transition
            Pair[] pairs = new Pair[2];
            pairs[0] = Pair.create((View) imageView, "imagetrans"); // #imagetrans transition name
            pairs[1] = Pair.create((View) txt, "texttrans"); // #texttrans transition name

            // Making transition
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, pairs);

            // Open the next activity
            Intent intent = new Intent(MainActivity.this, Nextpage.class);
            startActivity(intent, options.toBundle());

            //endregion
    }
}
