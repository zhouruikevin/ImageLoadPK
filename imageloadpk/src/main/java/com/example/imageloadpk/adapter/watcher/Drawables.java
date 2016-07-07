package com.example.imageloadpk.adapter.watcher;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.example.imageloadpk.R;

/**
 * Created by Nevermore on 16/7/3.
 */
public class Drawables {
    public static Drawable sPlaceholderDrawable;
    public static Drawable sErrorDrawable;

    private Drawables() {
    }

    public static void init(final Resources resources) {
        if (sPlaceholderDrawable == null) {
            sPlaceholderDrawable = resources.getDrawable(R.drawable.placeholder, null);
        }
        if (sErrorDrawable == null) {
            sErrorDrawable = resources.getDrawable(R.drawable.error, null);
        }
    }
}
