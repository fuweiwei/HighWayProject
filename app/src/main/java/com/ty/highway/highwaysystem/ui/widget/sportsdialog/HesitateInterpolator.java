package com.ty.highway.highwaysystem.ui.widget.sportsdialog;

import android.view.animation.Interpolator;

/**
 * Created by fuweiwei
 * on 13.01.15 at 14:20
 */
class HesitateInterpolator implements Interpolator {

    private static double POW = 1.0/2.0;

    @Override
    public float getInterpolation(float input) {
        return input < 0.5
                ? (float) Math.pow(input * 2, POW) * 0.5f
                : (float) Math.pow((1 - input) * 2, POW) * -0.5f + 1;
    }
}