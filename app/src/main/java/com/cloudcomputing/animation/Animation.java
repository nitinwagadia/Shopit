package com.cloudcomputing.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Created by Nitin on 12/4/2015.
 */
public class Animation {


    public static void ErrorLayoutAnimation(View v, float start) {
        v.setVisibility(View.VISIBLE);
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator translateX = ObjectAnimator.ofFloat(v, "translationX", start, 0.0F);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(v, "alpha", 1.0F, 0.4F);
        set.setDuration(500);
        set.playTogether(translateX, alpha);
        set.start();

    }

    public static void AnimateListItem(View layout, boolean goesDown) {

        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(layout, "translationY", goesDown == true ? 300 : -300, 0);
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(layout, "alpha", 0.2f, 1.0f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);

        animatorSet.playTogether(animatorTranslateY, animatorAlpha);
        animatorSet.start();

    }


    public static void CircularImageAnimation(View layout, boolean opening) {
        layout.setVisibility(View.VISIBLE);
        ObjectAnimator animatorTranslateX = ObjectAnimator.ofFloat(layout, "translationX", opening == true ? -200 : 200, 0);
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(layout, "alpha", 0.2f, 1.0f);
        ObjectAnimator animationRotation = ObjectAnimator.ofFloat(layout, "rotation", 0f, 360f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);

        animatorSet.playTogether(animatorTranslateX, animatorAlpha, animationRotation);
        animatorSet.start();

    }

}
