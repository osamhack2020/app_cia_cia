package com.dygames.cia;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class CustomActionBar extends View {
    Paint paint = new Paint();
    Shader shader = new LinearGradient(0, 0, 0, getHeight(), Color.BLACK, Color.TRANSPARENT, Shader.TileMode.CLAMP);

    public CustomActionBar(Context context) {
        super(context);
    }

    public CustomActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomActionBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setShader(shader);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
    }

    public void setColor(int position) {
        shader = new LinearGradient(0, 0, 0, getHeight(), Color.argb((int) (255 * (Math.min(Math.max(position, 0), 200) / 200f)), 0, 0, 0), Color.TRANSPARENT, Shader.TileMode.CLAMP);
        invalidate();
    }
}
