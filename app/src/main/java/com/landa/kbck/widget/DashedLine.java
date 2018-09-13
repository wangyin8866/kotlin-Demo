package com.landa.kbck.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

import com.landa.kbck.R;

/**
 * Created by haolyy on 2017/6/12.
 * <p>
 * 虚线
 */

public class DashedLine extends View {
    public DashedLine(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(R.color.text_special_color));
        Path path = new Path();
        path.moveTo(0, 1);
        path.lineTo(getWidth(), 1);
        PathEffect effects = new DashPathEffect(new float[]{20, 10}, 1);
        paint.setPathEffect(effects);
        canvas.drawPath(path, paint);
    }
}
