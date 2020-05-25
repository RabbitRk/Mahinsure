package com.rabbitt.mahinsure;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.rabbitt.mahinsure.adapter.GridAdapter;
import com.rabbitt.mahinsure.adapter.GridDataAdpater;
import com.rabbitt.mahinsure.broadcast.InternetBroadCast;
import com.rabbitt.mahinsure.misc.GridSpacingItemDecoration;
import com.rabbitt.mahinsure.model.grid;
import com.rabbitt.mahinsure.model.inspection;
import com.rabbitt.mahinsure.prefs.PrefsManager;
import com.rabbitt.simplyvolley.ServerCallback;
import com.rabbitt.simplyvolley.VolleyAdapter;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class PhotoActivity extends AppCompatActivity implements GridAdapter.OnRecyleItemListener {

    private static final String TAG = "maluPhoto";

    private static final int PERMISSION_REQUEST_CODE = 200;
    String lat, lng;

    GridAdapter customAdapter;
    List<grid> events;
    int position = 0;
    RecyclerView recyclerView;
    Bitmap bitmap;

    //Internet broadcast
    InternetBroadCast receiver;
    IntentFilter filter;

    String ref_no_, ref_p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        ref_no_ = getIntent().getStringExtra("ref_no");

        // Getting Ref_no from preference
        ref_p = new PrefsManager(this).getRefNo();

        checkInternetConnectivity();
        // get the reference of RecyclerView
        recyclerView = findViewById(R.id.grid_recycler);

        // set a GridLayoutManager with 3 number of columns , horizontal gravity and false value for reverseLayout to show the items from start to end
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 3, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView

        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, 10, true));

        //region under testing
        GridDataAdpater recyclerData = new GridDataAdpater(this);
        events = recyclerData.getEventList();
        //endregion

        customAdapter = new GridAdapter(PhotoActivity.this, events, this);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
    }

    private void checkInternetConnectivity() {
        filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new InternetBroadCast();
        registerReceiver(receiver, filter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i(TAG, "onActivityResult: Requestcode: " + requestCode + " " + resultCode);

        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            ArrayList<String> returnValue = null;
            if (data != null) {
                returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
            }
            Log.i(TAG, "onActivityResult: " + returnValue);
            if (returnValue != null) {

                try
                {
                    Uri imageUri = Uri.fromFile(new File(returnValue.get(0)));

                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                    Bitmap scaledBitmap;
                    if (bitmap.getWidth() >= bitmap.getHeight()) {
                        scaledBitmap = Bitmap.createBitmap(bitmap, bitmap.getWidth() / 2
                                        - bitmap.getHeight() / 2,
                                0, bitmap.getHeight(), bitmap.getHeight());
                    } else {
                        scaledBitmap = Bitmap.createBitmap(bitmap, 0, bitmap.getHeight() / 2
                                        - bitmap.getWidth() / 2,
                                bitmap.getWidth(), bitmap.getWidth());
                    }

                    Log.i(TAG, "onActivityResult: " + imageUri + " " + bitmap.toString());
                    grid model = events.get(position);
                    model.setImage(mark(scaledBitmap));
                    model.setBool(true);

                    Log.i(TAG, "onActivityResult: " + position + "   " + model.getEvent_name());

                    customAdapter.notifyDataSetChanged();
                }
                catch(Exception e)
                {
                    Log.i(TAG, "Exception: "+e.toString());
                }

//                CropImage.activity(imageUri)
//                        .start(this);
            }
        }

//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//
//            try {
//                if (resultCode == RESULT_OK) {
//                    Uri resultUri = null;
//                    if (result != null) {
//                        resultUri = result.getUri();
//                    }
//
//                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
//
//                    Log.i(TAG, "onActivityResult: " + resultUri + " " + bitmap.toString());
//                    grid model = events.get(position);
//                    model.setImage(mark(bitmap));
//                    model.setBool(true);
//
//                    Log.i(TAG, "onActivityResult: " + position + "   " + model.getEvent_name());
//
//                    customAdapter.notifyDataSetChanged();
//
//                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                    Exception error = result.getError();
//                }
//            } catch (IOException | NullPointerException ex) {
//                Log.i(TAG, "Exception: " + ex.getMessage());
//            }
//
//        }

//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == 0) {
//            Pix.start(this, Options.init().setRequestCode(100));
//        }
    }

    @Override
    public void OnItemClick(int position) {
        Log.i(TAG, "OnItemClick: " + position);

        this.position = position;

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//            }
//        }).start();

        Pix.start(this, Options.init().setRequestCode(100));

    }

    public void upload_image(View view) {
//        Log.i(TAG, "Upload_: " + imagetostring(bitmap));

        VolleyAdapter va = new VolleyAdapter(this);
        Realm realm = Realm.getDefaultInstance();


        int i = 1;
        for (grid model_ : events) {
            if (!model_.getBool()) {
                if (i <= 20)
                {
                    post_dialog();
                    Log.i(TAG, "Inside_if: ");
                    return;
                }
            }
            i++;
        }

        Log.i(TAG, "upload_image: after checking>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Log.i(TAG, "upload_image: t-10");

        for (grid model_ : events) {

            Log.i(TAG, ">>>>>>>>>Testing: " + model_.getEvent_name() + "  " + model_.getImage());

            String aa = imagetostring(model_.getImage());
            HashMap<String, String> map = new HashMap<>();
            map.put("image", aa);
            map.put("name", stringFormattor(model_.getEvent_name()));
            va.insertData(map, Config.UPLOAD, new ServerCallback() {
                @Override
                public void onSuccess(String s) {
                    Toast.makeText(PhotoActivity.this, s, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(String s) {
                    Toast.makeText(PhotoActivity.this, s, Toast.LENGTH_SHORT).show();
                }
            });

        }


        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                try {
                    String ref_no = ref_no_;
                    RealmResults<inspection> persons = realm.where(inspection.class).equalTo("ref_no", ref_no).findAll();
                    persons.setInt("color", 2);
                    persons.setString("sub", getDate());
                } catch (Exception e) {
                    Log.i(TAG, "Exception: " + e.getMessage());
                }
            }
        });


    }

    private void post_dialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.valid_dialog);
        dialog.setCancelable(true);

        try {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }
    }

    public String imagetostring(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 25, outputStream);

        byte[] imageBytes = outputStream.toByteArray();

        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    public Bitmap mark(Bitmap src) {
        int w = src.getWidth();
        int h = src.getHeight();
        Bitmap result = Bitmap.createBitmap(w, h, src.getConfig());

        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(src, 0, 0, null);

        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setAlpha(1000);
        paint.setTextSize(30);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setAntiAlias(true);


        String watermark = "Date: " + getDate();
        Log.i(TAG, "mark: " + watermark);

        canvas.drawText(watermark, 50, 100, paint);

        String location = "Lat: " + lat + " Lng: " + lng;
        canvas.drawText(location, 50, h - 100, paint);

        bitmap = result;
        return result;
    }


    public String stringFormattor(String string) {
        string = string.toLowerCase();
        string = string.replaceAll("\\s+", "");
        return string;
    }

    public String getDate() {
        Date c = Calendar.getInstance().getTime();

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat df = new SimpleDateFormat(getString(R.string.date_format));

        return df.format(c);
    }

    @SuppressLint({"MissingPermission", "DefaultLocale"})
    @Override
    protected void onStart() {
        super.onStart();

        try {
            LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            Location location;
            if (locationManager != null) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    Log.i(TAG, "onStart: " + location.getLatitude() + "   " + location.getLongitude());
                    lat = String.format("%.5f", location.getLatitude());
                    lng = String.format("%.5f", location.getLongitude());
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "Exception: " + e.getMessage());
        }

        //Requesting permission from the User
        if (!checkPermission()) {
            requestPermission();
        }
    }

    @Override
    protected void onDestroy() {
        try {
            if (receiver != null)
                unregisterReceiver(receiver);
        } catch (Exception ignored) {
        }
        super.onDestroy();
    }

    private boolean checkPermission() {
        int result1, result2, result3, result4, result5;

        Log.i(TAG, "checkPermission: ");
        result1 = ContextCompat.checkSelfPermission(getApplicationContext(), INTERNET);
        result2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        result3 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        result4 = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        result5 = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);

        return result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED && result3 == PackageManager.PERMISSION_GRANTED && result4 == PackageManager.PERMISSION_GRANTED && result5 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE, INTERNET, WRITE_EXTERNAL_STORAGE, ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                if (shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)) {
                    showMessageOKCancel(
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    PhotoActivity.this.requestPermissions(new String[]{READ_EXTERNAL_STORAGE, INTERNET, WRITE_EXTERNAL_STORAGE, ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION},
                                            PERMISSION_REQUEST_CODE);
                                }
                            });
                }
            }
        }
    }

    private void showMessageOKCancel(DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(this)
                .setMessage("You need to allow access to all the permissions")
                .setPositiveButton("OK", onClickListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    public void previous(View view) {
        back_checker();
    }

    public void back_checker() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.photo_dialog);
        dialog.setCancelable(false);

        Button dialogButton_ok = dialog.findViewById(R.id.action_ok);
        dialogButton_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(PhotoActivity.this, DetailActivity.class);
                intent.putExtra("ref_no", ref_no_);
                startActivity(intent);
                finish();
            }
        });

        Button dialogButton_cancel = dialog.findViewById(R.id.action_cancel);
        dialogButton_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        try {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        back_checker();
    }
}
