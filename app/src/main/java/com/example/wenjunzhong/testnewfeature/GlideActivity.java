package com.example.wenjunzhong.testnewfeature;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

/**
 * Created by wenjun.zhong on 2017/5/9.
 */

public class GlideActivity extends Activity{

    private static final String TAG = "GlideActivity";

    private LinearLayout mRootView;
    private ImageView mImageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);

        mRootView = (LinearLayout) findViewById(R.id.glide_root_view);

        final boolean isNewImage = true;
        if(isNewImage) {
            mImageView = (ImageView) LayoutInflater.from(this).inflate(R.layout.image_view, null);
        }else {
            mImageView = (ImageView) LayoutInflater.from(this).inflate(R.layout.image_view, null);
            mRootView.addView(mImageView);
            mImageView.setVisibility(View.INVISIBLE);
            //mImageView = (ImageView) findViewById(R.id.glide_img);
        }


        Glide.with(TestApplication.getInstance())
                .load("https://tpc.googlesyndication.com/daca_images/simgad/2022684233918011966")
               // .load("http://www.huitu.com/design/show/20130116/142820494200.html?_t_t_t=0.7526275933756319")
                .fitCenter()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        Log.w(TAG, "Glide load image exception: " + e);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        Log.w(TAG, "Glide load image success");
                        if(isNewImage) {
                            mRootView.addView(mImageView);
                        }else{
                            mImageView.setVisibility(View.VISIBLE);
                        }
                        return false;
                    }
                })
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        if(isNewImage) {
                            mImageView.setImageDrawable(resource);
                        }
                    }
                });
    }
}
