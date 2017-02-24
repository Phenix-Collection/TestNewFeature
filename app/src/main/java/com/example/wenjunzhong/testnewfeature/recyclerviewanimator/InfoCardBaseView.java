package com.example.wenjunzhong.testnewfeature.recyclerviewanimator;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.example.wenjunzhong.testnewfeature.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cbf on 17/2/20. 仿 next 锁屏通知栏
 */
public abstract class InfoCardBaseView extends RelativeLayout {
    public static final String TAG = "InforCard";
    private static int VAIL_SCOLLER_DISTANCE = 0;
    // 滑动间隔
    private static int SCROLL_DR = 300;
    /**
     * 打开 的宽度 70
     */
    private static int OPEN_WIDTH;
    /**
     * 清楚的宽度 105
     */
    private static int DISSMISS_WIDTH;
    /**
     * 图标的宽度 36 + 右间距 14
     **/
    private static int MAGIN_WIDTH;
    // 最小滑动距离
    private static int MIN_SCROLL_DISTNCE;
    // 可滑动的最大距离
    private int MAX_SCROLL_DISTANCE;
    private static int SCROLL_DISTANCE = 100;
    protected ImageView A;
    protected View infoCardBaseView;

    // 执行右滑最大距离
    private int swipeRightMaxDistance;
    // 手指按下X的坐标
    private int downX;
    private int downY;

    protected boolean cancelTap = false;
    private long touchTime = 0;
    // Scroller currX
    private int scrollCurrX;

    private Scroller scroller;
    private Scroller scro;
    private VelocityTracker velocityTracker;
    // 执行一个fling手势动作的最小速度值
    private int MinFlingVelocity;
    // 执行一个fling手势动作的最大速度值
    private int MaxFlingVelocity;
    // 是否是 可点击的
    private boolean touchable = true;

    private boolean isMoving = false;
    private Animation.AnimationListener WanimationListtener;

    protected List qlist = new ArrayList();

    protected Context mContext;
    // long tap 响应事件
    protected Runnable tapRunable = new Runnable() {
        @Override
        public void run() {
            Log.d(TAG, "[LongTap]run");
        }
    };
    // widthPixels 当前屏幕的宽
    protected int widthPixels;
    /**  */
    // 下划线
    protected ImageView imgDivider;
    /**
     * 内容布局
     */
    protected LinearLayout contentView;
    /**
     *
     */
    protected LinearLayout leftContainer;

    // 左边的文字
    protected TextView tvOpen;

    // 右边的文字
    protected TextView tvDissmiss;

    // 通知条数
    protected ViewGroup vpNum;

    private CardStatus cardStatus = CardStatus.Normal;

    enum CardStatus {
        Normal, ActionDetail
    }

    // 内容布局
    protected abstract View inflaterView();

    //
    protected boolean getSwipeLeftEnable() {
        return true;
    }

    protected boolean getSwipeRightEnable() {
        return true;
    }


    public InfoCardBaseView(Context context) {
        super(context);
        init(context);
    }

    public InfoCardBaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public InfoCardBaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        VAIL_SCOLLER_DISTANCE = dip2px(context, 30);
        OPEN_WIDTH = context.getResources().getDimensionPixelSize(R.dimen.views_shared_infocard_open_indication_width);
        DISSMISS_WIDTH =
                context.getResources().getDimensionPixelSize(R.dimen.views_shared_infocard_dismiss_indication_width);
        MAGIN_WIDTH =
                context.getResources().getDimensionPixelSize(R.dimen.views_shared_inforcardbaseview_action_icon_width)
                        + context.getResources().getDimensionPixelSize(R.dimen.views_shared_infocard_item_marginRight);
        MIN_SCROLL_DISTNCE =
                context.getResources().getDimensionPixelSize(R.dimen.views_shared_scrolltocallview_min_scrolldistance);
        MAX_SCROLL_DISTANCE =
                context.getResources().getDimensionPixelSize(
                        R.dimen.views_shared_scrolltocallview_action_max_scroll_distance);

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        widthPixels = displayMetrics.widthPixels;
        swipeRightMaxDistance = (-widthPixels) + MAGIN_WIDTH;
        SCROLL_DISTANCE = widthPixels;
        MAX_SCROLL_DISTANCE = widthPixels;
        View view = LayoutInflater.from(context).inflate(R.layout.layout_infocardbaseview, this);
        imgDivider = (ImageView) view.findViewById(R.id.views_shared_infocardbaseview_divider);
        contentView = (LinearLayout) view.findViewById(R.id.views_shared_infocardbaseview_container);
        this.contentView.addView(inflaterView(), new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        leftContainer = (LinearLayout) view.findViewById(R.id.views_shared_infocardbaseview_leftswipe_container);
        // 先将此布局滑动到左边去
        leftContainer.scrollTo(-DISSMISS_WIDTH, 0);
        if (scroller == null) {
            scroller = new Scroller(context, new DecelerateInterpolator());
        }
        if (scro == null) {
            scro = new Scroller(context, new DecelerateInterpolator());
        }
        tvOpen = (TextView) findViewById(R.id.views_shared_infocardbaseview_detail_text);
        tvDissmiss = (TextView) findViewById(R.id.views_shared_infocardbaseview_dismiss_text);
        vpNum = (ViewGroup) findViewById(R.id.views_shared_infocardbaseview_actionlist);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        MinFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity() * 8;
        MaxFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
    }

    public View getContentView() {
        return contentView;
    }

    public void setTouchable(boolean able) {
        touchable = able;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onInterceptTouchEvent : " + motionEvent.getAction());
                downX = (int) motionEvent.getX();
                downY = (int) motionEvent.getY();
                touchTime = System.currentTimeMillis();
                scrollCurrX = scroller.getCurrX();
                cancelTap = false;
                velocityTracker = VelocityTracker.obtain();
                velocityTracker.addMovement(motionEvent);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onInterceptTouchEvent : " + motionEvent.getAction());
                int x = (int) motionEvent.getX();
                if (cardStatus != CardStatus.ActionDetail && Math.abs(x - downX) > MIN_SCROLL_DISTNCE) {
                    Log.d(TAG, "onInterceptTouchEvent : " + "true");
                    return true;
                }
        }
        return super.onInterceptTouchEvent(motionEvent);
    }


    // @Override
    public boolean onTouchEvent1(MotionEvent motionEvent) {
        // 是否可以响应触摸事件,直接返回true
        // if (!touchable) {
        // return true;
        // }
        int action = motionEvent.getAction();
        // Log.d(TAG, "action : " + action);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "ACTION_DOWN ");
                postDelayed(tapRunable, 500);
                return true;
            case MotionEvent.ACTION_UP:
                // Log.d(TAG, "ACTION_UP ");
                // Log.d(TAG, "[LongTap]cancel by up");
                removeCallbacks(tapRunable);
                // Log.d(TAG, "CALL cleanMask in ACTION_UP");
                isMoving = false;
                if (velocityTracker != null) {
                    velocityTracker.addMovement(motionEvent);
                    velocityTracker.computeCurrentVelocity(1000);
                    float abs = Math.abs(velocityTracker.getXVelocity());
                    int motionX = (int) motionEvent.getX();
                    // 没有滑动,只是点击了一下,响应Tab动作
                    if (scrollCurrX == 0 && Math.abs(motionX - downX) < MIN_SCROLL_DISTNCE
                            && System.currentTimeMillis() - touchTime < 300) {
                        Log.d(TAG, " ###valid tab");
                        handleTabEvent();
                        break;
                    }
                    motionX -= downX;

                    // Log.d(TAG, "@@@ motionX = " + motionX + " abs =" + abs + " " +
                    // MinFlingVelocity + " " + MaxFlingVelocity);
                    // 判断是否是一个有效的动作
                    if ((getSwipeRightEnable() || motionX <= MIN_SCROLL_DISTNCE)
                            && (getSwipeLeftEnable() || motionX >= (-MIN_SCROLL_DISTNCE)) || abs > MinFlingVelocity) {
                        // 右滑大于 0 ,用负数判断
                        if (motionX >= -MIN_SCROLL_DISTNCE) {
                            if ((scrollCurrX == 0 && motionX >= OPEN_WIDTH)) {
                                Log.d(TAG, "valid swipe right");
                                // 执行打开动作
                                handleOpenAction();
                                break;
                            } else {
                                Log.d(TAG, "invalid swipe right  ### ");
                                scollerContentView(contentView.getScrollX(), -contentView.getScrollX(), SCROLL_DR);
                                break;
                            }
                        }
                        if (motionX <= -DISSMISS_WIDTH) {
                            Log.d(TAG, " valid swipe left");
                            scollerContentView(contentView.getScrollX(), widthPixels - contentView.getScrollX(),
                                    SCROLL_DR);
                            handleDissmissAction();
                        } else {
                            Log.d(TAG, "### invalid swipe left");
                            // startTvDissmissAlphaAnim(0.0f, SCROLL_DR);
                            scollerContentView(contentView.getScrollX(), -contentView.getScrollX(), SCROLL_DR);
                        }
                        infoCardBaseView = this;
                        // Log.d(TAG, " ADD mask in ACTION_UP");
                        break;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, " ACTION_MOVE:" + downX);
                if (velocityTracker != null) {
                    velocityTracker.addMovement(motionEvent);
                    action = (int) motionEvent.getX();
                    if (downX > 0) {
                        int eventY = (int) motionEvent.getY();
                        if (downY > 0) {
                            int diffX = action - downX;
                            action = eventY - downY;
                            if (!cancelTap
                                    && (Math.abs(diffX) > VAIL_SCOLLER_DISTANCE || Math.abs(action) > VAIL_SCOLLER_DISTANCE)) {
                                cancelTap = true;
                                Log.d(TAG, "[LongTap]cancel by move");
                                removeCallbacks(tapRunable);
                            }
                            // Log.d(TAG, "diffX = " + diffX + " MAX_SCROLL_DISTANCE = " +
                            // MAX_SCROLL_DISTANCE + " SCROLL_DISTANCE = " + SCROLL_DISTANCE);
                            if ((getSwipeRightEnable() || diffX <= 0)
                                    && ((getSwipeLeftEnable() || diffX >= 0) && diffX >= (-widthPixels))) {
                                if (diffX <= (-MIN_SCROLL_DISTNCE)) {
                                    getParent().requestDisallowInterceptTouchEvent(true);
                                }
                                MAX_SCROLL_DISTANCE =
                                        MAX_SCROLL_DISTANCE > DISSMISS_WIDTH ? MAX_SCROLL_DISTANCE : DISSMISS_WIDTH;
                                if (diffX >= (-MAX_SCROLL_DISTANCE)) {
                                    // if (diffX <= MIN_SCROLL_DISTNCE && diffX >
                                    // (-MIN_SCROLL_DISTNCE) && !this.isMoving) {
                                    // this.tvDissmiss.setAlpha(0.0f);
                                    // this.tvOpen.setAlpha(0.0f);
                                    // break;
                                    // }
                                    this.isMoving = true;
                                    if (diffX > MIN_SCROLL_DISTNCE) {
                                        getParent().requestDisallowInterceptTouchEvent(true);
                                    }
                                    if (diffX <= SCROLL_DISTANCE) {
                                        // Log.d(TAG, "contentView =" + ((-diffX) + scrollCurrX) +
                                        // " leftContainer = " + ((swipeRightMaxDistance - diffX) +
                                        // scrollCurrX));
                                        this.contentView.scrollTo((-diffX) + scrollCurrX, 0);
                                        this.leftContainer.scrollTo((this.swipeRightMaxDistance - diffX) + scrollCurrX,
                                                0);
                                        if (diffX <= 0 || diffX > this.OPEN_WIDTH) {
                                            if (diffX < 0) {
                                                this.contentView.setAlpha(1.0f - ((((float) Math.abs(diffX
                                                        - scrollCurrX)) / ((float) widthPixels)) * 2.0f));
                                                this.tvDissmiss.setVisibility(View.VISIBLE);
                                                this.tvDissmiss.setAlpha(((float) Math.abs(diffX))
                                                        / ((float) DISSMISS_WIDTH));
                                                if (this.cardStatus == CardStatus.ActionDetail) {
                                                    // this.vpNum.setAlpha(1.0f - (((float)
                                                    // Math.abs((i - this.scrollCurrX) + this.K)) /
                                                    // ((float) (this.widthPixels - this.K))));
                                                    break;
                                                }
                                            }
                                        }
                                        this.tvOpen.setVisibility(View.VISIBLE);
                                        this.tvOpen
                                                .setAlpha(1.0f - (((float) (scrollCurrX + OPEN_WIDTH) - diffX) / ((float) this.OPEN_WIDTH)));
                                        break;
                                    }
                                }
                            }
                        }
                        this.downY = eventY;
                        break;
                    }
                    this.downX = action;
                    break;
                }
                break;

            case MotionEvent.ACTION_CANCEL:
                // Log.d(TAG, "[LongTap]cancel by cancel");
                removeCallbacks(this.tapRunable);
                // Log.d(TAG, "InforCard, default");
                this.isMoving = false;
                resetView(SCROLL_DR);
                downX = -1;
                ViewParent parent = getParent();
                if (parent != null) {
                    parent.requestDisallowInterceptTouchEvent(false);
                }
                contentView.setAlpha(1.0f);
                tvOpen.setAlpha(0.0f);
                tvDissmiss.setAlpha(0.0f);
                if (velocityTracker != null) {
                    velocityTracker.recycle();
                    velocityTracker = null;
                }
                break;
        }
        return super.onTouchEvent(motionEvent);
    }


    private void resetView(int time) {
        this.leftContainer.setVisibility(View.GONE);
        scollerContentView(this.contentView.getScrollX(), -this.contentView.getScrollX(), time);
    }


    // 执行打开操作
    private void handleDissmissAction() {
        startTvDissmissAlphaAnim(0.0f);
        Log.d(TAG, " 延迟 xx秒 触发清楚");
    }

    private void startTvDissmissAlphaAnim(float to) {
        if (tvDissmiss == null) return;
        float from = tvDissmiss.getAlpha();
        ObjectAnimator animator = ObjectAnimator.ofFloat(tvDissmiss, "alpha", from, to);
        animator.setStartDelay(SCROLL_DR);
        animator.setDuration(SCROLL_DR / 2);
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                tvDissmiss.setAlpha(0.0f);
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
    }


    @Override
    public void computeScroll() {
        if (this.scroller != null) {
            if (this.scroller.computeScrollOffset()) {
                handleScroll();
            }
            if (this.scro != null) {
                if (this.scro.computeScrollOffset()) {
                    handleScroll();
                }
                super.computeScroll();
            }
        }
    }

    /**
     * 处理后续滑动
     **/
    private void handleScroll() {
        // Log.d(TAG, "  #####  handleScroll #### scroller.getCurrX() " + scroller.getCurrX() +
        // " scroller.getCurrX() " + scroller.getCurrY());
        contentView.scrollTo(scroller.getCurrX(), scroller.getCurrY());
        contentView.setAlpha(1.0f - ((((float) Math.abs(scroller.getCurrX())) / ((float) widthPixels)) * 2.0f));
        leftContainer.scrollTo(scroller.getCurrX() + swipeRightMaxDistance, scroller.getCurrY());
        tvDissmiss.setAlpha(((float) scroller.getCurrX()) / ((float) DISSMISS_WIDTH));
        if (scroller.getCurrX() <= 0)
            tvOpen.setAlpha(1.0f - ((float) OPEN_WIDTH + scroller.getCurrX()) / ((float) OPEN_WIDTH));
        postInvalidate();
    }

    /**
     * 执行Tab动画
     */
    protected void handleTabEvent() {
        Log.d(TAG, " #### handleTabEvent 动画 #######");
    }

    /**
     * 执行右滑操作
     */
    public void handleOpenAction() {
        Log.d(TAG, " #### 调用接口,实现打开 操作    #######");
    }


    /**
     * 滑动
     **/
    private void scollerContentView(int startX, int dx, int duration) {
        this.scroller.startScroll(startX, 0, dx, 0, duration);
        invalidate();
    }


    public static int dip2px(Context context, float dipValue) {
        float reSize = context.getResources().getDisplayMetrics().density;
        return (int) ((dipValue * reSize) + 0.5);
    }

}
