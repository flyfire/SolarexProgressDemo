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
    private int mForeNumi;
    private int mForeNumj;

    private int mBgColor;
    private int mForeColor;
    private int mMax;
    private int mProgress;
    private int mHorizontalGap;
    private int mVerticalGap;
    private int mRectPerline;
    private int mLineNumber;
    private int mWidth;
    private int mHeight;

    int allHorizontalGaps;
    int singleRectWidth;
    int unitWidth;
    int allVerticalGaps;
    int singleRectHeight;
    int unitHeight;

    private RectF mColorRectF;
    private RectF mHorizontalGapRectF;
    private RectF mVerticalGapRectF;

    private int DEFAULT_BACKGROUND_COLOR = Color.parseColor("#ff1d2537");
    private int DEFAULT_FOREGROUND_COLOR = Color.parseColor("#ff436bc5");
    private int DEFAULT_MAX = 100;
    private int DEFAULT_PROGRESS = 61;
    private int DEFAULT_HORIZONTAL_GAP = 2;
    private int DEFAULT_VERTICAL_GAP = 2;
    private int DEFAULT_RECTPERLINE = 20;
    private int DEFAULT_LINENUMBER = 5;
    private int DEFAULT_WIDTH = 300;
    private int DEFAULT_HEIGHT = 40;

    private ProgressCallback progressCallback = null;

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
        mHorizontalGap = typedArray.getInt(R.styleable.RectProgressbar_rpb_horizontalgap,
                DEFAULT_HORIZONTAL_GAP);
        mVerticalGap = typedArray.getInt(R.styleable.RectProgressbar_rpb_verticalgap,
                DEFAULT_VERTICAL_GAP);
        mRectPerline = typedArray.getInt(R.styleable.RectProgressbar_rpb_rectperline,
                DEFAULT_RECTPERLINE);
        mLineNumber = typedArray.getInt(R.styleable.RectProgressbar_rpb_linenumber,
                DEFAULT_LINENUMBER);
        typedArray.recycle();

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
        mHorizontalGapRectF = new RectF();
        mVerticalGapRectF = new RectF();

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
        allHorizontalGaps = mHorizontalGap * (mRectPerline - 1);
        singleRectWidth = (mWidth - allHorizontalGaps) / mRectPerline;
        unitWidth = mHorizontalGap + singleRectWidth;
        allVerticalGaps = mVerticalGap * (mLineNumber - 1);
        singleRectHeight = (mHeight - allVerticalGaps) / mLineNumber;
        unitHeight = mVerticalGap + singleRectHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mLineNumber; i++) {
            int top = i * unitHeight;
            for (int j = 0; j < mRectPerline; j++) {
                mColorRectF.set(j * unitWidth,
                        top,
                        j * unitWidth + singleRectWidth,
                        top + singleRectHeight);
                mHorizontalGapRectF.set(j * unitWidth + singleRectWidth,
                        top,
                        (j + 1) * unitWidth,
                        top + singleRectHeight);
                if (i < mForeNumi || (i == mForeNumi && j < mForeNumj)) {
                    canvas.drawRect(mColorRectF, mForePaint);
                } else {
                    canvas.drawRect(mColorRectF, mBgPaint);
                }
                if (j != mRectPerline - 1) {
                    canvas.drawRect(mHorizontalGapRectF, mGapPaint);
                }
            }
            int verticalGapTop = i * unitHeight + singleRectHeight;
            int verticalGapBottom = verticalGapTop + mVerticalGap;
            mVerticalGapRectF.set(0, verticalGapTop, mWidth, verticalGapBottom);
            if (i < mLineNumber - 1) {
                canvas.drawRect(mVerticalGapRectF, mGapPaint);
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
                float percent = nowProgress * mRectPerline * mLineNumber / mMax;
                mForeNum = new BigDecimal(percent).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
                mForeNumi = mForeNum / mRectPerline;
                mForeNumj = mForeNum % mRectPerline;
                invalidate();
                if (progressCallback != null) {
                    progressCallback.onProgress((int) percent);
                }
            }
        });
        mAnimator.start();
    }

    public void setProgressCallback(ProgressCallback progressCallback) {
        this.progressCallback = progressCallback;
    }

    public static interface ProgressCallback {

        public void onProgress(int percent);
    }
}
