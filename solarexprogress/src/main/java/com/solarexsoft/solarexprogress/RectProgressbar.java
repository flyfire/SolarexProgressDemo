package com.solarexsoft.solarexprogress;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.math.BigDecimal;

/**
 * Created by houruhou on 05/02/2018.
 */

public class RectProgressbar extends View {
    public static final String TAG = RectProgressbar.class.getSimpleName();
    private Context mContext;
    private Paint mBgPaint;
    private Paint mForePaint;
    private Paint mGapPaint;
    private ValueAnimator mAnimator;
    private int mForeNum;

    private int mBgColor;
    private int mForeColor;
    private int mMax;
    private int mProgress;
    private int mGap;
    private int mRectNum;
    private int mWidth;
    private int mHeight;

    int allGaps;
    int singleRectWidth;
    int unitWidth;

    private RectF mColorRectF;
    private RectF mGapRectF;

    private int DEFAULT_BACKGROUND_COLOR = Color.parseColor("#ff1d2537");
    private int DEFAULT_FOREGROUND_COLOR = Color.parseColor("#ff436bc5");
    private int DEFAULT_MAX = 100;
    private int DEFAULT_PROGRESS = 61;
    private int DEFAULT_GAP = 6;
    private int DEFAULT_RECTNUM = 20;
    private int DEFAULT_WIDTH = 300;
    private int DEFAULT_HEIGHT = 40;

    public RectProgressbar(Context context) {
        this(context, null);
    }

    public RectProgressbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RectProgressbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RectProgressbar);

        mBgColor = typedArray.getColor(R.styleable.RectProgressbar_rpb_background,
                DEFAULT_BACKGROUND_COLOR);
        mForeColor = typedArray.getColor(R.styleable.RectProgressbar_rpb_foreground,
                DEFAULT_FOREGROUND_COLOR);
        mMax = typedArray.getInt(R.styleable.RectProgressbar_rpb_max, DEFAULT_MAX);
        mProgress = typedArray.getInt(R.styleable.RectProgressbar_rpb_progress, DEFAULT_PROGRESS);
        mGap = typedArray.getInt(R.styleable.RectProgressbar_rpb_gap, DEFAULT_GAP);
        mRectNum = typedArray.getInt(R.styleable.RectProgressbar_rpb_rectnum, DEFAULT_RECTNUM);

        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setColor(mBgColor);
        mBgPaint.setStyle(Paint.Style.FILL);
        mBgPaint.setStrokeWidth(10);
        mForePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mForePaint.setColor(mForeColor);
        mForePaint.setStyle(Paint.Style.FILL);
        mForePaint.setStrokeWidth(10);
        mGapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mGapPaint.setARGB(0, 255, 255, 255);
        mGapPaint.setStyle(Paint.Style.FILL);
        mGapPaint.setStrokeWidth(10);

        mColorRectF = new RectF();
        mGapRectF = new RectF();

        setProgress(mProgress);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = Utils.measure(widthMeasureSpec, Utils.dp2px(getContext(), DEFAULT_WIDTH));
        int height = Utils.measure(heightMeasureSpec, Utils.dp2px(getContext(), DEFAULT_HEIGHT));

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        allGaps = mGap * (mRectNum - 1);
        singleRectWidth = (mWidth - allGaps) / mRectNum;
        unitWidth = mGap + singleRectWidth;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mRectNum; i++) {
            mColorRectF.set(i * unitWidth, 0, i * unitWidth + singleRectWidth, mHeight);
            mGapRectF.set(i * unitWidth + singleRectWidth, 0, (i + 1) * unitWidth, mHeight);
            if (i < mForeNum) {
                canvas.drawRect(mColorRectF, mForePaint);
            } else {
                canvas.drawRect(mColorRectF, mBgPaint);
            }
            if (i != mRectNum - 1){
                canvas.drawRect(mGapRectF, mGapPaint);
            }
        }
    }

    public void setProgress(int progress) {
        mAnimator = ValueAnimator.ofFloat(0, progress);
        mAnimator.setDuration(1000);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float nowProgress = (float) animation.getAnimatedValue();
                float percent = nowProgress * mRectNum / mMax;
                mForeNum = new BigDecimal(percent).setScale(0,BigDecimal.ROUND_HALF_UP).intValue();
                invalidate();
            }
        });
        mAnimator.start();
    }
}
