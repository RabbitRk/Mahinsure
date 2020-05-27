package com.rabbitt.mahinsure;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.rabbitt.mahinsure.adapter.DetailAdapter;
import com.rabbitt.mahinsure.adapter.FinishedAdapter;
import com.rabbitt.mahinsure.adapter.PendingAdapter;
import com.rabbitt.mahinsure.model.demo;
import com.rabbitt.mahinsure.model.grid;
import com.rabbitt.mahinsure.model.inspection;
import com.rabbitt.mahinsure.prefs.PrefsManager;
import com.rabbitt.simplyvolley.ServerCallback;
import com.rabbitt.simplyvolley.VolleyAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class DetailActivity extends AppCompatActivity implements DetailAdapter.OnRecyleItemListener {

    private static final String TAG = "maluDetail";
    VolleyAdapter volleyAdapter;
    RecyclerView recyclerView;
    String ref_no, v_no;
    List<demo> model = new ArrayList<>();
    demo data = null;
    LottieAnimationView animationView;
    ViewGroup viewGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        animationView = findViewById(R.id.animation_view);
        viewGroup = findViewById(R.id.group);

        ref_no = getIntent().getStringExtra("ref_no");
        recyclerView = findViewById(R.id.deta_recycler);

        volleyAdapter = new VolleyAdapter(this);

        HashMap<String, String> map = new HashMap<>();
        map.put("ref_no", ref_no);
        map.put("agent_id", "1"); //Get AgentId after login

        volleyAdapter.getData(Config.GET_REF_DATA, new ServerCallback() {
            @Override
            public void onSuccess(String s) {
                Log.i(TAG, "onSuccess: "+s);

                try {
                        JSONObject json = new JSONObject(s);
                        Iterator<String> keys = json.keys();

                        while (keys.hasNext()) {
                            String key = keys.next();
                            data = new demo();

                            data.setLabel(key);
                            data.setData((String) json.get(key));

                            if (key.equals("Vehicle Number"))
                            {
                                v_no = (String) json.get(key);
                            }

                            Log.i(TAG, "onResponse: " + "Key :" + key + "  Value :" + json.get(key));
                            model.add(data);

                            updateRecycler(model);
                        }

                } catch (JSONException e) {
                    Log.i(TAG, "Error: " + e.getMessage());
                }
            }

            @Override
            public void onError(String s) {
                Log.i(TAG, "onError: "+s);
            }
        });

    }

    private void updateRecycler(List<demo> model) {
        DetailAdapter recycleadapter = new DetailAdapter(model, this, this);
        LinearLayoutManager reLayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(reLayout);
        reLayout.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setAdapter(recycleadapter);
        recycleadapter.notifyDataSetChanged();
        recyclerView.setVisibility(View.VISIBLE);

        animationView.setVisibility(View.GONE);
        viewGroup.setVisibility(View.VISIBLE);
    }

    public void startInspection(View view) {

        //Setting Ref no
        new PrefsManager(this).setRefNo(ref_no, v_no);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), PhotoActivity.class);
//                Intent intent = new Intent(getApplicationContext(), Upload.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(DetailActivity.this, HomePage.class));
        finish();
    }

    @Override
    public void OnItemClickFinised(int position) {

    }

    public void go_back(View view) {
        startActivity(new Intent(DetailActivity.this, HomePage.class));
        finish();
    }

//        <<<<<<<<<<<<<<<<<<<<< Delete data from realm >>>>>>>>>>>>>>>>>>>>>
//
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
}
