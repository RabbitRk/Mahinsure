package com.rabbitt.mahinsure;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rabbitt.mahinsure.adapter.PendingAdapter;
import com.rabbitt.mahinsure.model.insp_loop;
import com.rabbitt.mahinsure.model.inspection;
import com.rabbitt.mahinsure.prefs.DarkModePrefManager;
import com.rabbitt.mahinsure.prefs.PrefsManager;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class HomePage extends AppCompatActivity implements PendingAdapter.OnRecyleItemListener {

    private static final String TAG = "malu";

    RecyclerView recyclerView;

    PendingAdapter recycleadapter;
    private List<inspection> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nextpage);

        PrefsManager prefsManager = new PrefsManager(this);
        prefsManager.setFirstTimeLaunch(true);

//        DarkModePrefManager darkModePrefManager = new DarkModePrefManager(this);
//        darkModePrefManager.setDarkMode(!darkModePrefManager.isNightMode());
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        recreate();

        recyclerView = findViewById(R.id.pending_recycler);

        Realm realm = Realm.getDefaultInstance();
        RealmResults<inspection> entries;
        entries = realm.where(inspection.class).findAll();

        for (inspection ins : entries) {
            inspection model = new inspection();
            model.setRef_no(ins.getRef_no());
            model.setV_no(ins.getV_no());
            model.setCus_name(ins.getCus_name());
            model.setDate(ins.getDate());
            model.setYear(ins.getYear());
            model.setMonth(ins.getMonth());
            model.setColor(ins.getColor());
            data.add(model);
        }

        entries.addChangeListener(new RealmChangeListener<RealmResults<inspection>>() {
            @Override
            public void onChange(RealmResults<inspection> results) {
                for (inspection ex : results)
                {
                    Log.i(TAG, "onChange: "+ex.getRef_no());
                }
                Log.i(TAG, "The size is: " + results.size());
            }
        });

        SharedPreferences shrp = getSharedPreferences(Config.TOKEN_PREF, MODE_PRIVATE);
        Log.i(TAG, "Token>>>>>>>>: "+shrp.getString("token","Null"));
        updaterecyclershit(data);
    }

    private void updaterecyclershit(List<inspection> data) {
        recycleadapter = new PendingAdapter(data, this,  this);
        LinearLayoutManager reLayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(reLayout);
        reLayout.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setAdapter(recycleadapter);
        recycleadapter.notifyDataSetChanged();
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void OnItemClick(int position) {
        Log.i(TAG, "OnItemClick: "+position);
        Log.i(TAG, "pos " + position);
        inspection model = data.get(position);
        String data = model.getRef_no();

        Log.i(TAG, "pos " + data);
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("ref_no", data);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Log.i(TAG, "onBackPressed: ");
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
        if(new DarkModePrefManager(this).isNightMode()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
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

    public void settingpage(View view) {
        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
    }


}
