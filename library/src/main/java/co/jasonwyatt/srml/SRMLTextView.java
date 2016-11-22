package co.jasonwyatt.srml;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * A simple extension to {@link TextView} which will automatically call out to SRML.getString when
 * {@link #setText(CharSequence)} is called
 *
 * @author jason
 */

public class SRMLTextView extends TextView {
    public SRMLTextView(Context context) {
        super(context);
    }

    public SRMLTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SRMLTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SRMLTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(SRML.markup(getContext(), text.toString()), BufferType.SPANNABLE);
    }
}
