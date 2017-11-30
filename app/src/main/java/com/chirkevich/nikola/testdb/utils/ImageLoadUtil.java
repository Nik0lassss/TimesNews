package com.chirkevich.nikola.testdb.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Колян on 29.05.2017.
 */

public class ImageLoadUtil {

    public static void loadIntoView(ImageView imageView, String src) {
        Glide.with(imageView).load(src).into(imageView);
    }
}
