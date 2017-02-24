package com.example.wenjunzhong.testnewfeature.recyclerviewanimator.animations;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.widget.RecyclerView;

/**
 * Created by wenjun.zhong on 2017/2/23.
 */

public class LeftInRightOutAnimation extends BaseItemAnimation {
    @Override
    public ViewPropertyAnimatorCompat getAddAnimation(RecyclerView.ViewHolder viewHolder) {
        ViewCompat.setTranslationX(viewHolder.itemView, -viewHolder.itemView.getWidth());
        return ViewCompat.animate(viewHolder.itemView).translationX(0);
    }

    @Override
    public ViewPropertyAnimatorCompat getRemoveAnimation(RecyclerView.ViewHolder viewHolder) {
        return ViewCompat.animate(viewHolder.itemView).translationX(viewHolder.itemView.getWidth());
    }
}
