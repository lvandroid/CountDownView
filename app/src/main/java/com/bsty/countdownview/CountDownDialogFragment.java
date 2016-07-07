package com.bsty.countdownview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

/**
 * Created by bsty on 7/6/16.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CountDownDialogFragment extends DialogFragment {

    private View rootView;
    ImageView countDown;

    private CountDownView mCdDrawable;
    private Animator mAnimator;
    CountDownDialogFragment dialog;
    private Window window;
    float width;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.count_down_dialog_frg, container, false);
        countDown = (ImageView) rootView.findViewById(R.id.count_down_iv);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        width = dm.widthPixels;


        window = getDialog().getWindow();
        //背景透明
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //去掉标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        int whites = getResources().getColor(R.color.white);
        mCdDrawable = new CountDownView(10, whites, 10, whites, whites);
        countDown.setImageDrawable(mCdDrawable);

        if (mAnimator != null) {
            mAnimator.cancel();
        }
        countDown.setVisibility(View.VISIBLE);
        mAnimator = prepareAnimator();
        mAnimator.start();
        return rootView;
    }

    private Animator prepareAnimator() {
        AnimatorSet animation = new AnimatorSet();

        //进度条动画
        ObjectAnimator progressAnimator = ObjectAnimator.ofFloat(mCdDrawable, "progress", 1f, 0f);
        progressAnimator.setDuration(10000);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                countDown.setVisibility(View.GONE);
                if (dialog != null)
                    dialog.dismiss();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                countDown.setVisibility(View.GONE);
                if (dialog != null)
                    dialog.dismiss();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        // 居中的倒计时数字
        ObjectAnimator showNumAnimator = ObjectAnimator.ofInt(mCdDrawable, "showNumber", 10, 0);
        showNumAnimator.setDuration(10000);
        showNumAnimator.setInterpolator(new LinearInterpolator());


        ObjectAnimator dotProgressAnimator = ObjectAnimator.ofInt(mCdDrawable, "circularCount", 0, 4);
        dotProgressAnimator.setDuration(1500);
        dotProgressAnimator.setRepeatCount(ValueAnimator.INFINITE);
        dotProgressAnimator.setRepeatMode(ValueAnimator.RESTART);
        dotProgressAnimator.setInterpolator(new LinearInterpolator());

        animation.playTogether(progressAnimator, showNumAnimator, dotProgressAnimator);
        return animation;

        //小圆点进度条
    }

    @Override
    public void onResume() {
        super.onResume();
        dialog = this;
        //设置大小
        window.setLayout((int) (width * 0.4f), (int) (width * 0.4f * (7 / 8f)));
    }
}
