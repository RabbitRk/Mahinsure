package com.rabbitt.mahinsure;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rabbitt.mahinsure.adapter.FinishedAdapter;
import com.rabbitt.mahinsure.adapter.PendingAdapter;
import com.rabbitt.mahinsure.model.inspection;
import com.rabbitt.mahinsure.prefs.DarkModePrefManager;
import com.rabbitt.mahinsure.prefs.PrefsManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class HomePage extends AppCompatActivity implements PendingAdapter.OnRecyleItemListener, FinishedAdapter.OnRecyleItemListener {

    private static final String TAG = "maluHome";

    RecyclerView recyclerView, finishedView;

    PendingAdapter recycleadapter;
    FinishedAdapter finishedadapter;

    private List<inspection> pending = new ArrayList<>();
    private List<inspection> finished = new ArrayList<>();
    private Realm realm;

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
        finishedView = findViewById(R.id.finished_recycler);

        realm = Realm.getDefaultInstance();

        RealmResults<inspection> entries;
        entries = realm.where(inspection.class).findAll();
        entries.addChangeListener(new RealmChangeListener<RealmResults<inspection>>() {
            @Override
            public void onChange(@NotNull RealmResults<inspection> results) {

                Log.i(TAG, "onChange: " + results.toString());

                updaterecyclershit(getPendingInspection());
                updaterecyclershit2(getFinishedInspection());
            }
        });

        SharedPreferences shrp = getSharedPreferences(Config.TOKEN_PREF, MODE_PRIVATE);
        Log.i(TAG, "Token>>>>>>>>: " + shrp.getString("token", "Null"));

        updaterecyclershit(getPendingInspection());
        updaterecyclershit2(getFinishedInspection());
    }

    private void updaterecyclershit(List<inspection> data) {
        recycleadapter = new PendingAdapter(data, this, this);
        LinearLayoutManager reLayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(reLayout);
        reLayout.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setAdapter(recycleadapter);
        recycleadapter.notifyDataSetChanged();
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void updaterecyclershit2(List<inspection> data) {
        finishedadapter = new FinishedAdapter(data, this, this);
        LinearLayoutManager reLayout = new LinearLayoutManager(this);
        finishedView.setLayoutManager(reLayout);
        reLayout.setOrientation(RecyclerView.VERTICAL);
        finishedView.setAdapter(finishedadapter);
        finishedadapter.notifyDataSetChanged();
        finishedView.setVisibility(View.VISIBLE);
    }

    public List<inspection> getPendingInspection() {
        pending.clear();
        if (recycleadapter != null)
            recycleadapter.notifyDataSetChanged();
        RealmResults<inspection> entries;
        entries = realm.where(inspection.class).notEqualTo("color", 0).findAll();

        for (inspection ins : entries) {
            inspection model = new inspection();
            model.setRef_no(ins.getRef_no());
            model.setV_no(ins.getV_no());
            model.setCus_name(ins.getCus_name());
            model.setDate(ins.getDate());
            model.setYear(ins.getYear());
            model.setMonth(ins.getMonth());
            model.setColor(ins.getColor());
            model.setContent(ins.getContent());
            pending.add(model);
        }
        return pending;
    }

    public List<inspection> getFinishedInspection() {
        finished.clear();
        if (finishedadapter != null)
            finishedadapter.notifyDataSetChanged();
        // Getting finished inspection
        RealmResults<inspection> finish;
        finish = realm.where(inspection.class).equalTo("color", 0).findAll();

        for (inspection ins : finish) {
            inspection model = new inspection();
            model.setRef_no(ins.getRef_no());
            model.setV_no(ins.getV_no());
            model.setCus_name(ins.getCus_name());
            model.setDate(ins.getDate());
            model.setYear(ins.getYear());
            model.setMonth(ins.getMonth());
            model.setColor(ins.getColor());
            finished.add(model);
        }

        return finished;
    }

    @Override
    public void OnItemClick(int position) {
        Log.i(TAG, "OnItemClick: " + position);
        Log.i(TAG, "pos " + position);
        inspection model = pending.get(position);
        String data = model.getRef_no();
        int boo = model.getColor();

        try
        {
            switch (boo)
            {
                case 1:
                    Log.i(TAG, "pos " + data);
                    Intent intent = new Intent(this, DetailActivity.class);
                    intent.putExtra("ref_no", data);
                    startActivity(intent);
                    finish();
                    break;
                case -1:
                    Toast.makeText(this, "correction   "+model.getContent(), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(this, "Your already finished this inspection", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
        catch(Exception e)
        {
            Log.i(TAG, "Exception: "+e.toString());
        }


    }

    @Override
    public void OnItemClickFinised(int position) {
        Toast.makeText(this, "Your already finished", Toast.LENGTH_SHORT).show();
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
        if (new DarkModePrefManager(this).isNightMode()) {
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
