package com.solarexsoft.solarexprogress;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by houruhou on 05/02/2018.
 */

public class Utils {
    public static int dp2px(Context context, float dp) {
        int ret = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
        return ret;
    }

    public static int measure(int measureSpec, int defaultSize) {
        int mode = View.MeasureSpec.getMode(measureSpec);
        int size = View.MeasureSpec.getSize(measureSpec);

        int result = defaultSize;
        if (mode == View.MeasureSpec.EXACTLY) {
            result = size;
        } else if (mode == View.MeasureSpec.AT_MOST) {
            result = Math.min(defaultSize, size);
        }
        return result;
    }
}
