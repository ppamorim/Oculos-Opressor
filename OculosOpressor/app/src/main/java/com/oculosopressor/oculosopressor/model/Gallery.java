package com.oculosopressor.oculosopressor.model;

import android.graphics.Bitmap;

/**
 * Created by pedro on 08/11/14.
 */
public class Gallery {

    private Bitmap mBitmap;

    public Gallery(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

}
