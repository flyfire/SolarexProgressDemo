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

/**
 * Created by houruhou on 02/02/2018.
 */

public class CommericalProgressbar extends View {
    public static final String TAG = CommericalProgressbar.class.getSimpleName();
    private Context mContext;
    private Paint mBgPaint;
    private Paint mForePaint;
    private Paint mSecondPaint;
    private int mForeWidth;
    private int mSecondWidth;
    private int mBgColor;
    private int mForeColor;
    private int mSecondProgressColor;
    private int mMax;
    private int mProgress;
    private int mSecondProgress;
    private int mWidth;
    private int mHeight;
    private RectF mBgRect;
    private RectF mForeRect;
    private RectF mSecondRect;
    private ValueAnimator mAnimator;
    private ValueAnimator mSecondAnimator;

    private int DEFAULT_BACKGROUND_COLOR = Color.parseColor("#ff483a42");
    private int DEFAULT_FOREGROUND_COLOR = Color.parseColor("#ff5081f9");
    private int DEFAULT_SECONDPROGRESS_COLOR = Color.parseColor("#ff39304d");
    private int DEFAULT_MAX = 100;
    private int DEFAULT_PROGRESS = 70;
    private int DEFAULT_SECONDPROGRESS = 0;

    private float DEFAULT_WIDTH = 300;
    private float DEFAULT_HEIGHT = 10;


    public CommericalProgressbar(Context context) {
        this(context, null);
    }

    public CommericalProgressbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommericalProgressbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable
                .CommericalProgressbar);
        mBgColor = typedArray.getColor(R.styleable.CommericalProgressbar_cpb_background,
                DEFAULT_BACKGROUND_COLOR);
        mForeColor = typedArray.getColor(R.styleable.CommericalProgressbar_cpb_foreground,
                DEFAULT_FOREGROUND_COLOR);
        mSecondProgressColor = typedArray.getColor(R.styleable
                .CommericalProgressbar_cpb_secondprogresscolor, DEFAULT_SECONDPROGRESS_COLOR);
        mMax = typedArray.getInt(R.styleable.CommericalProgressbar_cpb_max, DEFAULT_MAX);
        mProgress = typedArray.getInt(R.styleable.CommericalProgressbar_cpb_progress,
                DEFAULT_PROGRESS);
        mSecondProgress = typedArray.getInt(R.styleable.CommericalProgressbar_cpb_secondprogress,
                DEFAULT_SECONDPROGRESS);
        typedArray.recycle();

        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setStyle(Paint.Style.FILL);
        mBgPaint.setStrokeCap(Paint.Cap.ROUND);
        mBgPaint.setStrokeWidth(50);
        mBgPaint.setColor(mBgColor);
        mForePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mForePaint.setStyle(Paint.Style.FILL);
        mForePaint.setStrokeWidth(50);
        mForePaint.setStrokeCap(Paint.Cap.ROUND);
        mForePaint.setColor(mForeColor);
        mSecondPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSecondPaint.setStyle(Paint.Style.FILL);
        mSecondPaint.setStrokeWidth(50);
        mSecondPaint.setStrokeCap(Paint.Cap.ROUND);
        mSecondPaint.setColor(mSecondProgressColor);
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
        mBgRect = new RectF(2, 2, w - 2, h - 2);
        float percent = (float) mProgress / mMax;
        mForeWidth = (int) (percent * mWidth);
        mForeRect = new RectF(4, 4, mForeWidth - 4, mHeight - 4);
        float secondPercent = (float) mSecondProgress / mMax;
        mSecondWidth = (int) (secondPercent * mWidth);
        mSecondRect = new RectF(4, 4, mSecondWidth, mHeight - 4);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(mBgRect, mHeight, mHeight, mBgPaint);
        mSecondRect.set(4, 4, mSecondWidth, mHeight - 4);
        canvas.drawRoundRect(mSecondRect, mHeight, mHeight, mSecondPaint);
        mForeRect.set(4, 4, mForeWidth - 4, mHeight - 4);
        canvas.drawRoundRect(mForeRect, mHeight, mHeight, mForePaint);
    }

    public void setProgress(int progress) {
        float percent = (float) progress / mMax;
        mAnimator = ValueAnimator.ofFloat(0, percent);
        mAnimator.setDuration(1000);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float nowPercent = (float) animation.getAnimatedValue();
                mForeWidth = (int) (nowPercent * mWidth);
                invalidate();
            }
        });
        mAnimator.start();
    }

    public void setSecondProgress(int secondProgress) {
        float percent = (float) secondProgress / mMax;
        mSecondAnimator = ValueAnimator.ofFloat(0, percent);
        mSecondAnimator.setDuration(1000);
        mSecondAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float nowPercent = (float) animation.getAnimatedValue();
                mSecondWidth = (int) (nowPercent*mWidth);
                invalidate();
            }
        });
        mSecondAnimator.start();
    }
}
