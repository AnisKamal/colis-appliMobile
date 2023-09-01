package com.colis.colis_mobile;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.squareup.picasso.Transformation;

public class CircleTransformation implements Transformation {

    @Override
    public Bitmap transform(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;
        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);

        // Créer un bitmap circulaire
        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());
        BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setShader(shader);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawCircle(size / 2f, size / 2f, size / 2f, paint);

        // Libérer les ressources temporaires
        squaredBitmap.recycle();
        source.recycle();

        return bitmap;
    }

    @Override
    public String key() {
        return "circle";
    }
}
