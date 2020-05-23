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
                    JSONObject data = json.getJSONObject("data");
                    inspection ins = realm.createObject(inspection.class, data.getString("ref_no"));
                    ins.setCus_name(data.getString("cus_name"));
                    ins.setV_no(data.getString("v_no"));
                    ins.setDate(data.getString("date"));
                    ins.setMonth(data.getString("month"));
                    ins.setYear(data.getString("year"));
                    ins.setDate_(toDate(data.getString("date_")));
                    ins.setColor(1);
                }
                catch(Exception e)
                {
                    Log.i(TAG, "Exception: "+e.getMessage());
                }

            }
        });

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
