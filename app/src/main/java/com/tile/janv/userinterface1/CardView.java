package com.tile.janv.userinterface1;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * @see http://www.java2s.com/Code/Android/UI/extendsFrameLayout.htm
 */
public class CardView extends FrameLayout {
    private TextView textView;
    private int value;

    public CardView(Context context) {
        this(context, null);
    }

    public CardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        textView = new TextView(context, attrs, defStyle);
        textView.setGravity(Gravity.CENTER);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        this.addView(textView, params);
        setBackgroundResource(R.color.cardColor);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        textView.setText(value + "");
    }
}
