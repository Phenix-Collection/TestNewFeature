package com.example.wenjunzhong.testnewfeature.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.example.wenjunzhong.testnewfeature.R;


/**
 * Created by cbf on 17/2/22.
 */

public class MessageCardView extends InfoCardBaseView {

    public MessageCardView(Context context) {
        super(context);
    }

    public MessageCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MessageCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected View inflaterView() {
        return LayoutInflater.from(mContext).inflate(R.layout.layout_messagecardview, null);
    }
}
