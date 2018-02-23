package com.solarexsoft.solarexprogress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by houruhou on 22/02/2018.
 */

public class BubbleProgressView extends View {
    private int DEFAULT_PROGRESS = 90;
    private int DEFAULT_MAX = 100;

    private int DEFAULT_HEIGHT = 200;
    private int DEFAULT_WIDTH = 300;

    private int DEFAULT_BUBBLE_HEIGHT = 100;
    private int DEFAULT_BUBBLE_WIDTH = 100;
    private int DEFAULT_BORDER_COLOR = Color.parseColor("#ff4f9bff");
    private int DEFAULT_BACKGROUND_COLOR = Color.parseColor("#ff494149");

    private int mProgress;
    private int mMax;
    private int mBubbleHeight;
    private int mBubbleWidth;
    private int mBubbleBorderColor;
    private int mBackgroundColor;
    private int mStrokeWidth = 8;
    private int mHalfStroke;
    private Context mContext;
    private Paint mPaintRect;
    private Paint mBackgroundPaint;
    private Path mPathRect;
    private int mWidth;
    private int mHeight;
    private RectF mRectF;


    public BubbleProgressView(Context context) {
        this(context, null);
    }

    public BubbleProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BubbleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable
                    .BubbleProgressView);
            mProgress = typedArray.getInt(R.styleable.BubbleProgressView_bpv_progress,
                    DEFAULT_PROGRESS);
            mMax = typedArray.getInt(R.styleable.BubbleProgressView_bpv_max, DEFAULT_MAX);
            mBubbleHeight = typedArray.getInt(R.styleable.BubbleProgressView_bpv_bubbleheight,
                    DEFAULT_BUBBLE_HEIGHT);
            mBubbleWidth = typedArray.getInt(R.styleable.BubbleProgressView_bpv_bubblewidth,
                    DEFAULT_BUBBLE_WIDTH);
            mBubbleBorderColor = typedArray.getColor(R.styleable
                    .BubbleProgressView_bpv_bordercolor, DEFAULT_BORDER_COLOR);
            mBackgroundColor = typedArray.getColor(R.styleable
                    .BubbleProgressView_bpv_backgroundcolor, DEFAULT_BACKGROUND_COLOR);
            typedArray.recycle();
        }
        mPaintRect = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintRect.setStyle(Paint.Style.STROKE);
        mPaintRect.setStrokeWidth(mStrokeWidth);
        mPaintRect.setColor(mBubbleBorderColor);
        mPaintRect.setPathEffect(new CornerPathEffect(15));
        mPaintRect.setStrokeJoin(Paint.Join.ROUND);
        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        mBackgroundPaint.setStrokeWidth(2);
        mBackgroundPaint.setColor(mBackgroundColor);
        mPathRect = new Path();
        mHalfStroke = mStrokeWidth / 2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = Utils.measure(widthMeasureSpec, Utils.dp2px(mContext, DEFAULT_WIDTH));
        int height = Utils.measure(heightMeasureSpec, Utils.dp2px(mContext, DEFAULT_HEIGHT));
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int luodian = (int) (((float) mProgress / mMax) * mWidth);
        if (luodian <= mBubbleWidth / 2) {
            luodian = mBubbleWidth / 2;
        } else if (luodian + mBubbleWidth / 2 >= (mWidth - mHalfStroke)) {
            luodian = mWidth - mBubbleWidth / 2;
        }
        mPathRect.reset();
        mPathRect.moveTo(luodian - mBubbleWidth / 2, mBubbleHeight);
        mPathRect.lineTo(mHalfStroke, mBubbleHeight);
        mPathRect.lineTo(mHalfStroke, mHeight - mHalfStroke);
        mPathRect.lineTo(mWidth - mHalfStroke, mHeight - mHalfStroke);
        mPathRect.lineTo(mWidth - mHalfStroke, mBubbleHeight);
        mPathRect.lineTo(luodian + mBubbleWidth / 2, mBubbleHeight);
        mPathRect.lineTo(luodian, mHalfStroke);
        mPathRect.close();
        canvas.drawPath(mPathRect, mBackgroundPaint);
        canvas.drawPath(mPathRect, mPaintRect);
    }

    public void setProgress(int progress) {
        if (progress < 0) {
            mProgress = 0;
        } else if (progress > mMax) {
            mProgress = mMax;
        } else {
            mProgress = progress;
        }
        invalidate();
    }

    public void setBorderColor(int color) {
        mPaintRect.setColor(color);
        invalidate();
    }

    public void setBackgroundColor(int color) {
        mBackgroundPaint.setColor(color);
        invalidate();
    }
}
