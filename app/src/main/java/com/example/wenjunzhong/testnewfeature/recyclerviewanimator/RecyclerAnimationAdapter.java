package com.example.wenjunzhong.testnewfeature.recyclerviewanimator;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;

import com.example.wenjunzhong.testnewfeature.R;

import java.util.ArrayList;

/**
 * Created by wenjun.zhong on 2017/2/21.
 */

public class RecyclerAnimationAdapter extends RecyclerView.Adapter<RecyclerAnimationAdapter.MyHolderView> {

    private int mCount = 0;
    private ArrayList<ItemData> mList = new ArrayList<>(30);
    private LayoutInflater inflater;
    private OnItemClickListener mListener;

    public RecyclerAnimationAdapter(Context context) {
        setHasStableIds(true);
        inflater = LayoutInflater.from(context);
        for (int index = 0; index < 10; index++) {
            mList.add(new ItemData("ab#" + index));
            mCount += 1;
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void addItemTop() {
        mCount += 1;
        mList.add(0, new ItemData("new add item top" + mCount));
        notifyItemInserted(0);
        if (0 != mList.size() - 1) {
            notifyItemRangeChanged(0, mList.size());
        }
    }

    public void addItemBottom() {
        mCount += 1;

        mList.add(new ItemData("new add item bottom" + mCount));
        int pos = mList.size() - 1;
        notifyItemInserted(pos);
        if (pos != mList.size() - 1) {
            notifyItemRangeChanged(pos, mList.size() - pos);
        }
    }

    public void addItemMiddle() {
        mCount += 1;
        mList.add(4, new ItemData("new add middle " + mCount));
        notifyItemInserted(4);
        int pos = 4;
        if (pos != mList.size() - 1) {
            notifyItemRangeChanged(pos, mList.size() - pos);
        }
    }

    public void deleteItemTop() {
        if (getItemCount() <= 0) {
            return;
        }
        int pos = 0;
        mCount -= 1;
        mList.remove(pos);
        notifyItemRemoved(pos);
        if (pos != mList.size() - 1) {
            notifyItemRangeChanged(pos, mList.size() - pos);
        }
    }

    public void deleteItemBottom() {
        if (getItemCount() <= 0) {
            return;
        }
        int pos = mList.size() - 1;
        mCount -= 1;
        mList.remove(pos);
        notifyItemRemoved(pos);

        if (pos != mList.size() - 1) {
            notifyItemRangeChanged(pos, mList.size() - pos);
        }
    }

    public void deleteItemMiddle() {
        int size = getItemCount();
        if (size > 0) {
            mCount -= 1;
            int position = size / 2;
            mList.remove(position);
            notifyItemRemoved(position);

            if (position != mList.size() - 1) {
                notifyItemRangeChanged(position, mList.size() - position);
            }
        }
    }

    @Override
    public MyHolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolderView(inflater.inflate(R.layout.test_recycler_item_view_stub, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyHolderView holder, final int position) {
        holder.setText(mList.get(position));
        // if(mListener != null) {
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w("testStub", "onClick position:" + position);
                ItemData itemData = mList.get(position);
                itemData.isOpen = !itemData.isOpen;
                notifyItemChanged(position);
            }
        });
        // }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public long getItemId(int position) {
        return mList.get(position).hashCode();
    }

    class MyHolderView extends RecyclerView.ViewHolder {
        // boolean isOpen;
        TextView mTextView;
        View rootView;
        TextView mTextView1;

        public MyHolderView(View itemView) {
            super(itemView);
            rootView = itemView;
            mTextView1 = (TextView) itemView.findViewById(R.id.test_text_view_1);
        }

        public void setText(ItemData itemData) {
            mTextView1.setText(itemData.content);
            showTestView(itemData.isOpen);
        }


        private void showTestView(boolean isOpen) {

            if (isOpen) {
                if (mTextView == null) {
                    mTextView = (TextView) ((ViewStub) itemView.findViewById(R.id.stub_view)).inflate();
                } else {
                    mTextView.setVisibility(View.VISIBLE);
                }
                mTextView1.setVisibility(View.GONE);
            } else {
                if (mTextView != null) {
                    mTextView.setVisibility(View.GONE);
                    mTextView1.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public static interface OnItemClickListener {
        void onClick(MyHolderView view, int position);
    }
}
