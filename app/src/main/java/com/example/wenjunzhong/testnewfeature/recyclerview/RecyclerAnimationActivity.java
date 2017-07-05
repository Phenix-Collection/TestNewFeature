package com.example.wenjunzhong.testnewfeature.recyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.wenjunzhong.testnewfeature.R;
import com.example.wenjunzhong.testnewfeature.recyclerview.animations.LeftInRightOutAnimation;

/**
 *
 * Created by wenjun.zhong on 2017/2/21.
 */

public class RecyclerAnimationActivity extends Activity implements View.OnClickListener {

    RecyclerView mRecyclerView;
    RecyclerAnimationAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_animation_layout);
        findViewById(R.id.add_item_top).setOnClickListener(this);
        findViewById(R.id.add_item_middle).setOnClickListener(this);
        findViewById(R.id.add_item_bottom).setOnClickListener(this);
        findViewById(R.id.delete_item_top).setOnClickListener(this);
        findViewById(R.id.delete_item_middle).setOnClickListener(this);
        findViewById(R.id.delete_item_bottom).setOnClickListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_animation_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RecyclerAnimationAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_item_top:
                mAdapter.addItemTop();
                mRecyclerView.smoothScrollToPosition(0);
                break;
            case R.id.add_item_middle:
                mAdapter.addItemMiddle();
                break;
            case R.id.add_item_bottom:
                mAdapter.addItemBottom();
                mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount() - 1);
                break;
            case R.id.delete_item_top:
                mAdapter.deleteItemTop();
                break;
            case R.id.delete_item_middle:
                mAdapter.deleteItemMiddle();
                break;
            case R.id.delete_item_bottom:
                mAdapter.deleteItemBottom();
                break;
        }
    }

    private void addItem() {

    }

    private void deleteItem() {

    }
}
