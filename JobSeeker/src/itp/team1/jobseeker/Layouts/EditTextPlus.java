package itp.team1.jobseeker.Layouts;

import itp.team1.jobseeker.R;
import itp.team1.jobseeker.FontUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class EditTextPlus extends EditText {
    private static final String TAG = "EditText";

    public EditTextPlus(Context context) {
        super(context);
    }

    public EditTextPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
        FontUtils.setCustomFont(this,context,attrs,
                R.styleable.TextViewPlus,
                R.styleable.TextViewPlus_customfont);
    }

    public EditTextPlus(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        FontUtils.setCustomFont(this,context,attrs,
                R.styleable.TextViewPlus,
                R.styleable.TextViewPlus_customfont);
    }

}

