package com.example.wenjunzhong.testnewfeature;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by wenjun.zhong on 2016/4/14.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{

    ArrayList<String> mList = new ArrayList<>(30);
    private static int createCount = 0;
    private LayoutInflater inflater;
    private int count = 0;

    private String tag;
    public RecyclerAdapter(Context context, String tag) {
        init(context, tag);
    }

    private void init(Context context, String tag) {
        inflater = LayoutInflater.from(context);

        for (int index = 0; index < 60; index ++){
            mList.add(tag + " ab#" + index);
        }
        this.tag = tag;
    }

    public RecyclerAdapter(Context context) {
        init(context, "");
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        count += 1;
        Log.w("test", tag + " onCreateViewHolder " + count);
        MyViewHolder viewHolder = new MyViewHolder(inflater.inflate(R.layout.recycler_item_layout, parent, false));
        viewHolder.itemView.setTag(tag);
        Log.w("test12", "onCreateViewHolder: " + createCount);
        createCount += 1;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Object object = holder.itemView.getTag();

        holder.updateData(mList.get(position));
        Log.w("test", tag + " onBindViewHolder " + position + ", tag: " + (object instanceof String ? object : "null"));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text_1);
        }

        public void updateData(String str){
            textView.setText(str);
        }
    }
}
