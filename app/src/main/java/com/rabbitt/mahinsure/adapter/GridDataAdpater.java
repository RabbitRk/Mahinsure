package com.rabbitt.mahinsure.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;

import androidx.core.content.res.ResourcesCompat;

import com.rabbitt.mahinsure.R;
import com.rabbitt.mahinsure.model.grid;

import java.util.Arrays;
import java.util.List;

public class GridDataAdpater {

    Context context;
    public GridDataAdpater(Context context)
    {
        this.context = context;
    }
    Uri uri;

    public List<grid> getEventList() {

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_photo);

        return Arrays.asList(
                new grid("Chassis", bitmap),
                new grid("Front number plate", bitmap),
                new grid("Rear image", bitmap),
                new grid("Front Bumper", bitmap),
                new grid("Rear Bumper", bitmap),
                new grid("Front left corner", bitmap),
                new grid("Front right corner", bitmap),
                new grid("Left side", bitmap),
                new grid("Right side", bitmap),
                new grid("Left QTR panel" , bitmap),
                new grid("Right QTR panel", bitmap),
                new grid("Engine", bitmap),
                new grid("Chassis plate", bitmap),
                new grid("Dicky open", bitmap),
                new grid("Under chassis", bitmap),
                new grid("Dashboard", bitmap),
                new grid("Odometer", bitmap),
                new grid("CNG LPG kit", bitmap),
                new grid("RC copy", bitmap),
                new grid("Pre-insurance", bitmap),
                new grid("Dents & Scratches", bitmap),
                new grid("Dents & Scratches", bitmap),
                new grid("Dents & Scratches", bitmap)
        );
    }
}