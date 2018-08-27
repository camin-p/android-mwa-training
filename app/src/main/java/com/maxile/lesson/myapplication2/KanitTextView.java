package com.maxile.lesson.myapplication2;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class KanitTextView extends AppCompatTextView {
    public KanitTextView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        Typeface sukhumvitType = Typeface.createFromAsset(context.getAssets(), "fonts/Kanit-Light.ttf");

        setTypeface(sukhumvitType);
    }

    public KanitTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
        Typeface sukhumvitType = Typeface.createFromAsset(context.getAssets(), "fonts/Kanit-Light.ttf");

        setTypeface(sukhumvitType);
    }

    public KanitTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        Typeface sukhumvitType = Typeface.createFromAsset(context.getAssets(), "fonts/Kanit-Light.ttf");

        setTypeface(sukhumvitType);
    }
}
