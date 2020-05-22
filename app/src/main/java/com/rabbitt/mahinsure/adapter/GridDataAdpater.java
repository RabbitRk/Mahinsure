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
                new grid("Chassis", bitmap),
                new grid("Front number plate", bitmap),
                new grid("Rear image", bitmap),
                new grid("Front Bumper", bitmap),
                new grid("Rear Bumper", bitmap),
                new grid("Front left corner", bitmap),
                new grid("Front right corner", bitmap),
                new grid("Left side", bitmap),
                new grid("Right side", bitmap),
                new grid("Left QTR panel", bitmap),
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