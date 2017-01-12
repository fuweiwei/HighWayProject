package com.ty.highway.highwaysystem.support.utils;

import android.content.Context;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.ty.highway.highwaysystem.R;

/**
 * Created by fuweiwei on 2015/9/25.
 */
public class SnackbarUtils {

    public static void show(Context context, String message) {
        int color = context.getResources().getColor(R.color.common_theme_color);
        Snackbar snackbar  = Snackbar.with(context);
        snackbar.color(color);
        snackbar.text("     "+message+"     ");
        snackbar.duration((Snackbar.SnackbarDuration.LENGTH_LONG.getDuration() / 2));
        SnackbarManager.show(snackbar);

    }

}
