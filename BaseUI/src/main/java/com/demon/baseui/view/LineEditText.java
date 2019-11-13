package com.demon.baseui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatEditText;

import com.demon.baseui.R;

import javax.security.auth.login.LoginException;

/**
 * @author DeMon
 * @date 2018/11/29
 * @email 757454343@qq.com
 * @description
 */
public class LineEditText extends AppCompatEditText {
    private static final String TAG = "LineEditText";
    private Paint linePaint;
    private Rect mRect;

    public LineEditText(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        mRect = new Rect();
        linePaint = new Paint();
        setGravity(Gravity.START);
        setPadding(10, 20, 10, 10);
        setLineSpacing(40, 1);
        //setLines(getLineCount());
    }


    @Override
    protected void onDraw(Canvas paramCanvas) {
        int lineCount = getLineCount();
        int height = getHeight();
        int lineHeight = getLineHeight();
        int lines = height / lineHeight + 1;
        if (lineCount < lines) {
            lineCount = lines;
        }
        int y = getLineBounds(0, mRect);
        int i = 0;
        while (i <= lineCount) {
            paramCanvas.drawLine(15, y + 10, getRight() - 15, y + 10, this.linePaint);
            paramCanvas.save();
            y += lineHeight;
            i++;
        }

        super.onDraw(paramCanvas);
        paramCanvas.restore();

    }
}