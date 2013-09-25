package itp.team1.jobseeker.Layouts;

import itp.team1.jobseeker.R;
import itp.team1.jobseeker.FontUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextViewPlus extends TextView {
    private static final String TAG = "TextView";

    public TextViewPlus(Context context) {
        super(context);
    }

    public TextViewPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
        FontUtils.setCustomFont(this,context,attrs,
                R.styleable.TextViewPlus,
                R.styleable.TextViewPlus_customfont);
    }

    public TextViewPlus(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        FontUtils.setCustomFont(this,context,attrs,
                R.styleable.TextViewPlus,
                R.styleable.TextViewPlus_customfont);
    }


}