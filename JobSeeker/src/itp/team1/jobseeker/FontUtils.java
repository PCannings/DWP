package itp.team1.jobseeker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.ref.SoftReference;
import java.util.Hashtable;

public class FontUtils {

    public static final String TAG = "FontUtils";

    public static void setCustomFont(View customView, Context ctx, AttributeSet attrs, int[] attributeSet, int fontId) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, attributeSet);
        String customFont = a.getString(fontId);
        setCustomFont(customView, ctx, customFont);
        a.recycle();
    }

    private static boolean setCustomFont(View customView, Context ctx, String asset) {
        if (TextUtils.isEmpty(asset))
            return false;
        Typeface tf = null;
        try {
            tf = getFont(ctx, asset);
            if (customView instanceof TextView) {
                ((TextView) customView).setTypeface(tf);
            } else if (customView instanceof EditText) {
                ((EditText) customView).setTypeface(tf);
            } else {
                ((Button) customView).setTypeface(tf);
            }
        } catch (Exception e) {
            Log.e(TAG, "Could not get typeface: " + asset, e);
            return false;
        }

        return true;
    }

    private static final Hashtable<String, SoftReference<Typeface>> fontCache = new Hashtable<String, SoftReference<Typeface>>();

    public static Typeface getFont(Context c, String name) {
        synchronized (fontCache) {
            if (fontCache.get(name) != null) {
                SoftReference<Typeface> ref = fontCache.get(name);
                if (ref.get() != null) {
                    return ref.get();
                }
            }

            Typeface typeface = Typeface.createFromAsset(
                    c.getAssets(),
                    "fonts/" + name
            );
            fontCache.put(name, new SoftReference<Typeface>(typeface));

            return typeface;
        }
    }

}

