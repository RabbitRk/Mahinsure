package com.rabbitt.mahinsure;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.rabbitt.mahinsure.model.inspection;
import com.rabbitt.simplyvolley.VolleyAdapter;

import io.realm.Realm;
import io.realm.RealmResults;

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

    public void deleterealm(View view) {
//        Realm realm;
//        realm = Realm.getDefaultInstance();
//        final RealmResults<inspection> results = realm.where(inspection.class).findAll();
//
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                results.deleteAllFromRealm();
//            }
//        });
        startActivity(new Intent(getApplicationContext(), PhotoActivity.class));

    }
}
