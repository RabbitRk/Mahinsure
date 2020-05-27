package com.rabbitt.mahinsure;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daasuu.ahp.AnimateHorizontalProgressBar;
import com.rabbitt.mahinsure.adapter.FinishedAdapter;
import com.rabbitt.mahinsure.adapter.PendingAdapter;
import com.rabbitt.mahinsure.model.inspection;
import com.rabbitt.mahinsure.prefs.DarkModePrefManager;
import com.rabbitt.mahinsure.prefs.PrefsManager;
import com.rabbitt.simplyvolley.ServerCallback;
import com.rabbitt.simplyvolley.VolleyAdapter;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

import static com.rabbitt.mahinsure.prefs.PrefsManager.PREF_NAME;
import static com.rabbitt.mahinsure.prefs.PrefsManager.USER_ID;

public class HomePage extends AppCompatActivity implements PendingAdapter.OnRecyleItemListener, FinishedAdapter.OnRecyleItemListener {

    private static final String TAG = "maluHome";

    RecyclerView recyclerView, finishedView;

    PendingAdapter recycleadapter;
    FinishedAdapter finishedadapter;

    private List<inspection> pending = new ArrayList<>();
    private List<inspection> finished = new ArrayList<>();
    private Realm realm;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nextpage);

        user_id = getSharedPreferences(PREF_NAME, MODE_PRIVATE).getString(USER_ID, "");
        PrefsManager prefsManager = new PrefsManager(this);
        if (!prefsManager.isFirstTimeLaunch()) {
            Log.i(TAG, "onCreate: First time");
            getDatafromMySql();
        }
        prefsManager.setFirstTimeLaunch(true);

//        getDatafromMySql();//Must be deleted

        updateToken();

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

    private void updateToken() {
        SharedPreferences shrp = getSharedPreferences(Config.TOKEN_PREF, MODE_PRIVATE);
        String token = shrp.getString("token", "Null");

        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", user_id);
        map.put("token", token);
        new VolleyAdapter(this).insertData(map, Config.TOKEN_UPDATE, new ServerCallback() {
            @Override
            public void onSuccess(String s) {
                Log.i(TAG, "onSuccessToken: " + s);
            }

            @Override
            public void onError(String s) {
                Log.i(TAG, "onError: " + s);
            }
        });
    }

    JSONObject json;

    private void getDatafromMySql() {
        Log.i(TAG, "getDatafromMySql: " + user_id);
        HashMap<String, String> map = new HashMap<>();
        map.put("agen_id", user_id);

        new VolleyAdapter(this).getData(map, Config.GET_DATA, new ServerCallback() {
            @Override
            public void onSuccess(String s) {

                Log.i(TAG, "onSuccess: "+s);

                try {
                    final JSONArray arr = new JSONArray(s);
                    final int n = arr.length();
                    for (int i = 0; i < n; i++) {
                        json = arr.getJSONObject(i);
                        Log.i(TAG, "onSuccess: "+json.get("submitted_on").toString());
                    }
//                    realm.executeTransaction(new Realm.Transaction() {
//                        @Override
//                        public void execute(Realm realm) {
//
//                            for (int i = 0; i < n; i++) {
//                                try {
//
//
//                                    inspection ins = realm.createObject(inspection.class, json.get("insp_id"));
//                                    ins.setCus_name(json.get("cus_name").toString());
//                                    ins.setV_no(json.get("vehicle_no").toString());
//                                    ins.setDate(json.get("day").toString());
//                                    ins.setMonth(json.get("month").toString());
//                                    ins.setYear(json.get("year").toString());
//                                    ins.setRec(json.get("rec").toString());
//                                    ins.setSub(json.get("submitted_on").toString());
//                                    ins.setApp(json.get("approved_on").toString());
//                                    ins.setContent(json.get("remarks").toString());
//                                    ins.setColor(Integer.parseInt(json.get("approved").toString()));
//
//                                    Log.i(TAG, "execute: "+json.toString());
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//                        }
//                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.i(TAG, "onSuccess: " + s);
            }

            @Override
            public void onError(String s) {
                Log.i(TAG, "onError: " + s);
            }
        });
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
            model.setSub(ins.getSub());
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
            model.setSub(ins.getSub());
            model.setApp(ins.getApp());
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

        try {
            switch (boo) {
                case 1:
                    Log.i(TAG, "pos " + data);
                    Intent intent = new Intent(this, DetailActivity.class);
                    intent.putExtra("ref_no", data);
                    startActivity(intent);
                    finish();
                    break;
                case -1:
                    Toast.makeText(this, "correction   " + model.getContent(), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(this, "Your already finished this inspection", Toast.LENGTH_SHORT).show();
                    break;
            }
        } catch (Exception e) {
            Log.i(TAG, "Exception: " + e.toString());
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
        finish();
    }


    public void get_data(View view) {
        getDatafromMySql();
//        RealmResults<inspection> entries;
//        entries = realm.where(inspection.class).findAll();
//
//        Log.i(TAG, "get_data: "+entries.toString());
//        final AnimateHorizontalProgressBar[] progressBar = new AnimateHorizontalProgressBar[1];
//        final boolean[] bool = {true};
//        final int[] jumpTime = {0};
//        final Dialog dialog = new Dialog(HomePage.this);
//        dialog.setContentView(R.layout.progress_dialog);
//        dialog.setCancelable(false);
//
//        progressBar[0] = dialog.findViewById(R.id.animate_progress_bar);
//        progressBar[0].setMaxWithAnim(1000);
//        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.show();
//
//        final Thread t_dialog = new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//
//                while (bool[0]) {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                    if (jumpTime[0] < 1000) {
//                        jumpTime[0] += 100;
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                progressBar[0].setProgressWithAnim(jumpTime[0]);
//                            }
//                        });
//                        Log.i(TAG, "run: Main: " + jumpTime[0]);
//                    } else {
//                        bool[0] = false;
//                        jumpTime[0] = 0;
//                        dialog.dismiss();
//                    }
//
//                }
//
//            }
//        });
//
//        t_dialog.start();

//        try {
//            runOnUiThread(new Runnable() {
//
//                @Override
//                public void run() {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    Log.i(TAG, "run: "+jumpTime);
//                    progressBar.setProgress(jumpTime);
//                }
//            });
//        } catch (Exception e) {
//            Log.e("tag", e.getMessage());
//        }


//        RealmResults<inspection> entries;
//        entries = realm.where(inspection.class).findAll();
////        inspection model = null;
////        for (inspection ins : entries) {
////            model = new inspection();
////            model.setRef_no(ins.getRef_no());
////            model.setV_no(ins.getV_no());
////            model.setCus_name(ins.getCus_name());
////            model.setDate(ins.getDate());
////            model.setYear(ins.getYear());
////            model.setMonth(ins.getMonth());
////            model.setColor(ins.getColor());
////            model.setContent(ins.getContent());
////        }
//
//        Log.i(TAG, "get_data: "+entries.toString());


//        final ProgressDialog progress;
//        Handler workHandler;
//        progress = new ProgressDialog(this);
//        progress.setMessage("Saving Progress");
//        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        progress.setProgress(0);
//        progress.setMax(100);
//        progress.setCancelable(true);
//        progress.show();
//
//        workHandler = new Handler(Looper.getMainLooper());
//
//        final int totalProgressTime = 100;
//        workHandler.post(new Runnable() {
//            @Override
//            public void run() {
//
//                //Do some work
//
//                while (jumpTime < totalProgressTime) {
//                    try {
//                        sleep(2000);
//                        jumpTime += 5;
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        progress.setProgress(jumpTime);
//                    }
//                });
//            }
//        });
    }
}
