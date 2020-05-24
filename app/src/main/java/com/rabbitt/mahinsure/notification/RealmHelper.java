package com.rabbitt.mahinsure.notification;

import android.content.Context;
import android.util.Log;

import com.rabbitt.mahinsure.R;
import com.rabbitt.mahinsure.model.inspection;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmHelper {

    public static final String TAG = "malu";

    Realm realm;
    Context context;
    public RealmHelper(Context context) {
        this.context = context;
    }

    public void realminsert(final JSONObject json)
    {
        realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NotNull Realm realm) {
                try
                {
                    inspection ins = realm.createObject(inspection.class, json.getString("ref_no"));
                    ins.setCus_name(json.getString("cus_name"));
                    ins.setV_no(json.getString("v_no"));
                    ins.setDate(json.getString("date"));
                    ins.setMonth(json.getString("month"));
                    ins.setYear(json.getString("year"));
                    ins.setDate_(toDate(json.getString("date_")));
                    ins.setColor(1);
                    Log.i(TAG, "execute: "+toDate(json.getString("date_")));
                }
                catch(Exception e)
                {
                    Log.i(TAG, "Exception: "+e.getMessage());
                }

            }
        });

    }

    public void realmupdate(final JSONObject json) {
        try {

            Log.i(TAG, "realmupdate: "+json.getString("ref_no"));

            realm = Realm.getDefaultInstance();

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(@NotNull Realm realm) {
                    try
                    {
                        RealmResults<inspection> persons = realm.where(inspection.class).equalTo("ref_no", json.getString("ref_no")).findAll();
                        persons.setInt("color", json.getInt("color"));
                    }
                    catch(Exception e)
                    {
                        Log.i(TAG, "Exception: "+e.getMessage());
                    }

                }
            });

        }
        catch (Exception e)
        {
            Log.i(TAG, "realmupdate: "+e.toString());
        }
    }

    public Date toDate(String dateString) {
        Date date = null;
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            date = formatter.parse(dateString);
            Log.e("Print result: ", String.valueOf(date));

        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        return date;
    }


}
