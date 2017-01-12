package com.ty.highway.highwaysystem.ui.widget.sportsdialog;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.ty.highway.highwaysystem.R;

/**
 * Created by fuweiwei
 * on 13.01.15 at 14:22
 */
public class SpotsDialog extends AlertDialog {

    private static final int DELAY = 150;
    private static final int DURATION = 1500;

    private int size;
    private AnimatedView[] spots;
    private AnimatorPlayer animator;
    private TextView mTvContent;

    public SpotsDialog(Context context) {
        this(context, R.style.SpotsDialogDefault);
    }

    public SpotsDialog(Context context, int theme) {
        super(context, theme);
    }

    public SpotsDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.common_dialog_sports);
        mTvContent = (TextView) findViewById(R.id.title);
        initProgress();
    }
    public void setContent(String s){
        mTvContent.setText(s);
    }
    @Override
    protected void onStart() {
        super.onStart();

        animator = new AnimatorPlayer(createAnimations());
        animator.play();
    }

    @Override
    protected void onStop() {
        super.onStop();

        animator.stop();
    }

    //~

    private void initProgress() {
        ProgressLayout progress = (ProgressLayout) findViewById(R.id.progress);
        size = progress.getSpotsCount();

        spots = new AnimatedView[size];
        int size = getContext().getResources().getDimensionPixelSize(R.dimen.common_spot_size);
        int progressWidth = getContext().getResources().getDimensionPixelSize(R.dimen.common_progress_width);
        for (int i = 0; i < spots.length; i++) {
            AnimatedView v = new AnimatedView(getContext());
            v.setBackgroundResource(R.drawable.comom_spot);
            v.setTarget(progressWidth);
            v.setXFactor(-1f);
            progress.addView(v, size, size);
            spots[i] = v;
        }
    }

    private Animator[] createAnimations() {
        Animator[] animators = new Animator[size];
        for (int i = 0; i < spots.length; i++) {
            Animator move = ObjectAnimator.ofFloat(spots[i], "xFactor", 0, 1);
            move.setDuration(DURATION);
            move.setInterpolator(new HesitateInterpolator());
            move.setStartDelay(DELAY * i);
            animators[i] = move;
        }
        return animators;
    }
}
