package com.example.android.materialdesigncodelab.utils;

import android.animation.Animator;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;

import com.example.android.materialdesigncodelab.R;


public class InitiateSearch {

    public static void handleToolBar(final Context context, final CardView search, Toolbar toolbarMain, final EditText editText) {
       if (search.getVisibility() == View.VISIBLE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                final Animator animatorHide = ViewAnimationUtils.createCircularReveal(search,
                    search.getWidth() - (int) convertDpToPixel(56, context),
                    (int) convertDpToPixel(23, context),
                    (float) Math.hypot(search.getWidth(), search.getHeight()),
                    0);
                animatorHide.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        search.setVisibility(View.GONE);
                        //((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editText.getWindowToken(), 0);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                animatorHide.setDuration(300);
                animatorHide.start();
            } else {
                //((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editText.getWindowToken(), 0);
                search.setVisibility(View.GONE);
            }
            editText.setText("");
            toolbarMain.setNavigationIcon(R.mipmap.ic_arrow_back);
            toolbarMain.setTitle("Fit Health");
            toolbarMain.getMenu().clear();
            toolbarMain.inflateMenu(R.menu.menu_search);
            search.setEnabled(false);
        } else {
            toolbarMain.setTitle("");
            toolbarMain.getMenu().clear();
            toolbarMain.setNavigationIcon(null);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                final Animator animator = ViewAnimationUtils.createCircularReveal(search,
                    search.getWidth() - (int) convertDpToPixel(56, context),
                    (int) convertDpToPixel(23, context),
                    0,
                    (float) Math.hypot(search.getWidth(), search.getHeight()));
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        //((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                search.setVisibility(View.VISIBLE);
                if (search.getVisibility() == View.VISIBLE) {
                    animator.setDuration(300);
                    animator.start();
                    search.setEnabled(true);
                }
            } else {
                search.setVisibility(View.VISIBLE);
                search.setEnabled(true);
                //((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        }
    }


    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * (metrics.densityDpi / 160f);
    }
}

