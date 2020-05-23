package com.rabbitt.mahinsure;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.rabbitt.simplyvolley.VolleyAdapter;

public class DetailActivity extends AppCompatActivity {

    VolleyAdapter volleyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);



    }

    public void newPhoto(View view) {
        startActivity(new Intent(getApplicationContext(), PhotoActivity.class));
    }
}
