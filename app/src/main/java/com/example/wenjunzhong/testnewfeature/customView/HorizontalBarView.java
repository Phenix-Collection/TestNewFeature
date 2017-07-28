package com.example.wenjunzhong.testnewfeature.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.wenjunzhong.testnewfeature.R;

/**
 *
 * Created by wenjun.zhong on 2017/7/28.
 */

public class HorizontalBarView extends View {

    private String mText = "";
    private float mValue = 30.0f;
    private Paint mBarPaintEmpty = new Paint();
    private Paint mBarPaintFill = new Paint();
    private Paint mFillTextPaint = new Paint();
    private Paint mEmptyTextPaint = new Paint();

    private Rect mBarRect = new Rect();
    private Rect mTextRect = new Rect();
    private int mHalfStrokeWidth;

    private int mEmptyColor;
    private int mFillColor;
    private int mEmptyTextColor;
    private int mFillTextColor;
    private int mTextSize = 15;
    private int mTextWidth = 0;
    private int mTextPadding = mTextSize;

    public HorizontalBarView(Context context) {
        this(context, null);
    }

    public HorizontalBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public HorizontalBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        if (attrs == null) {
            mBarPaintEmpty.setColor(Color.GRAY);
            mBarPaintEmpty.setStyle(Paint.Style.FILL);

            mBarPaintFill.setColor(Color.BLUE);
            mBarPaintFill.setStyle(Paint.Style.FILL);

            mEmptyTextPaint.setColor(Color.BLACK);
            mEmptyTextPaint.setAntiAlias(true);

            mFillTextPaint.setColor(Color.WHITE);
            mFillTextPaint.setAntiAlias(true);
        } else {

            TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.HorizontalBarView, 0, 0);

            String fontPath = null;

            try {
                // Retrieve the values from the TypedArray and store into
                // fields of this class.
                //
                // The R.styleable.PieChart_* constants represent the index for
                // each custom attribute in the R.styleable.PieChart array.
                mEmptyColor = a.getColor(R.styleable.HorizontalBarView_bar_emptyColor, Color.GRAY);
                mFillColor = a.getColor(R.styleable.HorizontalBarView_bar_fillColor, Color.BLUE);
                mEmptyTextColor = a.getColor(R.styleable.HorizontalBarView_bar_emptyTextColor, Color.BLACK);
                mFillTextColor = a.getColor(R.styleable.HorizontalBarView_bar_fillTextColor, Color.WHITE);
                mTextSize = a.getDimensionPixelSize(R.styleable.HorizontalBarView_bar_textSize, mTextSize);
                mText = a.getString(R.styleable.HorizontalBarView_bar_text);
                mTextPadding = a.getDimensionPixelSize(R.styleable.HorizontalBarView_bar_textPadding, mTextPadding);
                mValue = a.getFloat(R.styleable.HorizontalBarView_bar_fillPercentage, mValue);
                fontPath = a.getString(R.styleable.HorizontalBarView_bar_fontPath);
                if (mText == null) {
                    mText = "";
                }

            } finally {
                // release the TypedArray so that it can be reused.
                a.recycle();
            }


            mBarPaintEmpty.setColor(mEmptyColor);
            mBarPaintEmpty.setStyle(Paint.Style.FILL);

            mBarPaintFill.setColor(mFillColor);
            mBarPaintFill.setStyle(Paint.Style.FILL);

            mEmptyTextPaint.setColor(mEmptyTextColor);
            mEmptyTextPaint.setStyle(Paint.Style.FILL);
            mEmptyTextPaint.setTextSize(mTextSize);
            mEmptyTextPaint.setAntiAlias(true);

            mFillTextPaint.setColor(mFillTextColor);
            mFillTextPaint.setStyle(Paint.Style.FILL);
            mFillTextPaint.setTextSize(mTextSize);
            mFillTextPaint.setAntiAlias(true);

            if (fontPath != null && fontPath.length() > 0) {
                Typeface t = Typeface.createFromAsset(context.getAssets(), fontPath);
                mFillTextPaint.setTypeface(t);
                mEmptyTextPaint.setTypeface(t);
            }

            mTextWidth = (int) mEmptyTextPaint.measureText(mText);
        }
    }

    public void setText(String text) {
        this.mText = text;
        mTextWidth = (int) mEmptyTextPaint.measureText(mText);
        invalidate();
    }

    public void setPercentage(@FloatRange(from = 0, to = 100) float value) {
        if (value < 0 || value > 100) {
            throw new IllegalArgumentException("value is a percentage and should be between 0 and 100 found -> "
                    + value);
        }
        mValue = value;
        invalidate();
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        int minHeight = super.getSuggestedMinimumHeight();
        if (minHeight <= 0) {
            return mTextSize + mTextPadding * 2;
        } else {
            return minHeight;
        }
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        int minWidth = super.getSuggestedMinimumWidth();
        if (minWidth <= 0) {
            return mTextWidth + mTextPadding * 2;
        } else {
            return minWidth;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mHalfStrokeWidth =
                mBarPaintEmpty.getStrokeWidth() < 2 ? (int) mBarPaintEmpty.getStrokeWidth() : (int) mBarPaintEmpty
                        .getStrokeWidth() / 2;

        int ypad = getPaddingBottom() + getPaddingTop();
        int xpad = getPaddingStart() + getPaddingEnd();

        mBarRect.left = getPaddingStart() + mHalfStrokeWidth;
        mBarRect.top = getPaddingTop() + mHalfStrokeWidth;
        mBarRect.right = getWidth() - getPaddingEnd() - mHalfStrokeWidth;
        mBarRect.bottom = getHeight() - getPaddingBottom() - mHalfStrokeWidth;

        mEmptyTextPaint.getTextBounds(mText, 0, mText.length(), mTextRect);

        mTextRect.left = getPaddingStart() + mHalfStrokeWidth;
        if (isInEditMode()) {
            mTextRect.top = mBarRect.centerY() + mTextRect.bottom + mHalfStrokeWidth;
        } else {
            mTextRect.top = mBarRect.centerY() + mTextRect.height() / 2 + mHalfStrokeWidth;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int minWidth = getPaddingStart() + getPaddingEnd() + getSuggestedMinimumWidth();
        int w = resolveSizeAndState(minWidth, widthMeasureSpec, 1);
        int minHeight = getPaddingBottom() + getPaddingTop() + getSuggestedMinimumHeight();
        int h = resolveSizeAndState(minHeight, heightMeasureSpec, 0);
        setMeasuredDimension(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Build the filled part first
        mBarRect.right = (int) (mBarRect.right * (mValue / 100f));
        int fillRight = mBarRect.right;
        canvas.drawRect(mBarRect, mBarPaintFill);

        // Draw the empty part starting from the end of the filled part
        // this is to avoid overdraw.
        int originalLeft = mBarRect.left;
        mBarRect.left = mBarRect.right;
        mBarRect.right = getWidth() - getPaddingRight() - mHalfStrokeWidth;
        int emptyRight = mBarRect.right;
        canvas.drawRect(mBarRect, mBarPaintEmpty);
        mBarRect.left = originalLeft;

        // double the padding to account for the left and right sides.
        if (emptyRight - fillRight > mTextWidth + (mTextPadding * 2)) {
            // recalculate left for text to keep it close to the fill line.
            mTextRect.left = fillRight + mTextPadding;
            canvas.drawText(mText, mTextRect.left, mTextRect.top, mEmptyTextPaint);

        } else {
            // recalculate left for text to keep it close to the fill line.
            mTextRect.left = fillRight - mTextWidth - mTextPadding;
            canvas.drawText(mText, mTextRect.left, mTextRect.top, mFillTextPaint);
        }
    }
}
