package com.tile.janv.userinterface1;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tile.janv.userinterface1.logic.ValueContainer;

/**
 * @see http://www.java2s.com/Code/Android/UI/extendsFrameLayout.htm
 */
public class Card extends FrameLayout implements ValueContainer {
    public TextView textView;
    private int value;

    public Card(Context context) {
        this(context, null);
    }

    public Card(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Card(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //programmatically create the view
        textView = new TextView(context, attrs, defStyle);
        textView.setGravity(Gravity.CENTER);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.setMargins(5,5,5,5);
        textView.setBackgroundResource(R.color.cardColor);
        textView.setLayoutParams(params);
        this.addView(textView);
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void setValue(int value) {
        this.value = value;
        if (value > 0) {
            textView.setText(value + "");
        } else {
            textView.setText("");
        }
    }
}
