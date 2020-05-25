package com.rabbitt.mahinsure.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.rabbitt.mahinsure.R;
import com.rabbitt.mahinsure.model.grid;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GridDataAdpater {

    private Context context;

    public GridDataAdpater(Context context) {
        this.context = context;
    }

    public List<grid> getEventList() {

        Bitmap bitmap = getBitmapFromVectorDrawable(context);

        return Arrays.asList(
                new grid("Chassis", bitmap, false),
                new grid("Front number plate", bitmap, false),
                new grid("Rear image", bitmap, false),
                new grid("Front Bumper", bitmap, false),
                new grid("Rear Bumper", bitmap, false),
                new grid("Front left corner", bitmap, false),
                new grid("Front right corner", bitmap, false),
                new grid("Left side", bitmap, false),
                new grid("Right side", bitmap, false),
                new grid("Left QTR panel", bitmap, false),
                new grid("Right QTR panel", bitmap, false),
                new grid("Engine", bitmap, false),
                new grid("Chassis plate", bitmap, false),
                new grid("Dicky open", bitmap, false),
                new grid("Under chassis", bitmap, false),
                new grid("Dashboard", bitmap, false),
                new grid("Odometer", bitmap, false),
                new grid("CNG LPG kit", bitmap, false),
                new grid("RC copy", bitmap, false),
                new grid("Pre-insurance", bitmap, false),
                new grid("Dents & Scratches", bitmap, false),
                new grid("Dents & Scratches", bitmap, false),
                new grid("Dents & Scratches", bitmap, false)
        );
    }

    private static Bitmap getBitmapFromVectorDrawable(Context context) {
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.photo__);
        Bitmap bitmap = Bitmap.createBitmap(Objects.requireNonNull(drawable).getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}