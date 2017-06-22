package com.example.wenjunzhong.testnewfeature;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.example.wenjunzhong.testnewfeature.recyclerview.RecyclerViewOnClickListener;

/**
 * Created by wenjun.zhong on 2017/6/22.
 */


public class RecyclerViewOnTouchItemListenerActivity extends BaseActivity implements RecyclerViewOnClickListener.OnItemClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.snackbar_activity);

        RecyclerViewOnClickListener listener = new RecyclerViewOnClickListener(this, this, mRecyclerView);
        mRecyclerView.addOnItemTouchListener(listener);
    }

    @Override
    public void onItemClick(View view, int adapterPosition, int layoutPosition) {
        Toast.makeText(this, "onItemClick adapterPosition: " + adapterPosition + "; layoutPosition:" + layoutPosition, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Toast.makeText(this, "onItemLongClick position: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemDoubleClick(View view, int position) {
        Toast.makeText(this, "onItemDoubleClick position: " + position, Toast.LENGTH_SHORT).show();
    }
}
