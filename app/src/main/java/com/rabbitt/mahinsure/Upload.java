package com.rabbitt.mahinsure;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.rabbitt.simplyvolley.ServerCallback;
import com.rabbitt.simplyvolley.VolleyAdapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Upload extends AppCompatActivity {

    private static final String TAG = "malu";
    private static final int PERMISSION_REQUEST_CODE = 200;

    Bitmap bitmap;

    ImageView imageView;
    String lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        imageView = findViewById(R.id.imageView);

    }

    public void pick_(View view) {
        Pix.start(this, Options.init().setRequestCode(100));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            ArrayList<String> returnValue = null;
            if (data != null) {
                returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
            }
            Log.i(TAG, "onActivityResult: " + returnValue);

            if (returnValue != null) {
                Uri imageUri = Uri.fromFile(new File(returnValue.get(0)));

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
//                    Bitmap bitmap1 = BitmapFactory.decodeFile(String.valueOf(imageUri));

                    imageView.setImageBitmap(mark(bitmap));
                } catch (Exception e) {
                    Log.i(TAG, "Exception: " + e.getMessage() + "  " + e.getCause());
                }
            }
        }
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
        paint.setTextSize(50);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setAntiAlias(true);

        Date c = Calendar.getInstance().getTime();

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat df = new SimpleDateFormat(getString(R.string.date_format));
        String formattedDate = df.format(c);

        String watermark = "Date: "+formattedDate;
        Log.i(TAG, "mark: "+watermark);


        canvas.drawText(watermark, 50, 100, paint);

        String location = "Lat: "+lat+" Lng: "+lng;
        canvas.drawText(location, 50, h-100, paint);


        bitmap = result;
        return result;
    }

    public void Upload_(View view) {
        Log.i(TAG, "Upload_: " + imagetostring(bitmap));
        String aa = imagetostring(bitmap);
        HashMap<String, String> map = new HashMap<>();
        map.put("image", aa);

        VolleyAdapter va = new VolleyAdapter(this);
        va.insertData(map, Config.UPLOAD, new ServerCallback() {
            @Override
            public void onSuccess(String s) {
                Toast.makeText(Upload.this, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String s) {
                Toast.makeText(Upload.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String imagetostring(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 25, outputStream);

        byte[] imageBytes = outputStream.toByteArray();

        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    @SuppressLint({"MissingPermission", "DefaultLocale"})
    @Override
    protected void onStart() {
        super.onStart();

        try
        {
            LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            Location location = null;
            if (locationManager != null) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                Log.i(TAG, "onStart: "+location.getLatitude()+"   "+location.getLongitude());


//                lat = String.valueOf(location.getLatitude());
//                lng = String.valueOf(location.getLongitude());

                lat = String.format("%.5f", location.getLatitude());
                lng = String.format("%.5f", location.getLongitude());
            }

        }
        catch(Exception e)
        {
            Log.i(TAG, "Exception: "+e.getMessage());
        }

        //Requesting permission from the User
        if (!checkPermission()) {
            requestPermission();
        }

    }

    private boolean checkPermission() {

        Log.i(TAG, "checkPermission: ");
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), INTERNET);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int result3 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result4 = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int result5 = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);
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
                                Upload.this.requestPermissions(new String[]{READ_EXTERNAL_STORAGE, INTERNET, WRITE_EXTERNAL_STORAGE, ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION},
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

    public void view_iamge(View view) {

    }
}
