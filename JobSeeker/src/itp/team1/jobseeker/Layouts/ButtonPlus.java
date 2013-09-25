package itp.team1.jobseeker.Layouts;

import itp.team1.jobseeker.R;
import itp.team1.jobseeker.FontUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class ButtonPlus extends Button {
    private static final String TAG = "Button";

    public ButtonPlus(Context context) {
        super(context);
    }

    public ButtonPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
        FontUtils.setCustomFont(this,context,attrs,
                R.styleable.TextViewPlus,
                R.styleable.TextViewPlus_customfont);
    }

    public ButtonPlus(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        FontUtils.setCustomFont(this,context,attrs,
                R.styleable.TextViewPlus,
                R.styleable.TextViewPlus_customfont);
    }

}
