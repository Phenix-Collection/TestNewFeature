package com.example.wenjunzhong.testnewfeature.recyclerviewanimator.animations;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.widget.RecyclerView;

/**
 * Created by wenjun.zhong on 2017/2/23.
 */

public class RotationAnimation extends BaseItemAnimation {
    @Override
    public ViewPropertyAnimatorCompat getAddAnimation(RecyclerView.ViewHolder viewHolder) {
        ViewCompat.setRotation(viewHolder.itemView, 180);
        // ViewCompat.setAlpha(viewHolder.itemView, 1);
        return ViewCompat.animate(viewHolder.itemView).rotation(0);
    }

    @Override
    public ViewPropertyAnimatorCompat getRemoveAnimation(RecyclerView.ViewHolder viewHolder) {
        return ViewCompat.animate(viewHolder.itemView).rotation(180);
    }
}
