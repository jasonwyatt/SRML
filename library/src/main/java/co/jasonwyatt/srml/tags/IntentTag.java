package co.jasonwyatt.srml.tags;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.view.View;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.jasonwyatt.srml.Utils;

/**
 * Created by jason on 11/14/16.
 *
 * This tag can be used to launch an activity or a service.
 *
 * {{intent class=com.yourcompany.ActivityName action=android.intent.action.VIEW for_service=false x_extraKey=extraValue }}
 *
 * class
 *      REQUIRED.
 *      The fully-qualified name of the activity or service you intend to start.
 * for_service
 *      OPTIONAL.
 *      Denotes whether or not to call <code>context.startService(intent)</code> instead of
 *      <code>context.startActivity(intent)</code>
 * action
 *      OPTIONAL.
 *      what you'd specify in <code>intent.setAction(..)</code>
 * x_[extraKey]
 *      OPTIONAL.
 *      Intent extras with [extraKey] names. They allow you to supply additional information about
 *      how to parse and send the parameter values to the target.
 */
public class IntentTag extends ParameterizedTag {
    static final String NAME = "intent";
    private static final Pattern EXTRAS_KEY_PATTERN = Pattern.compile("x_[^=]+");
    private static final String TYPE_INT = "int";
    private static final String TYPE_LONG = "long";
    private static final String TYPE_CHAR = "char";
    private static final String TYPE_BOOLEAN = "boolean";
    private static final String TYPE_FLOAT = "float";
    private static final String TYPE_DOUBLE = "double";
    private static final String TYPE_BYTE = "byte";
    private static final String TYPE_SHORT = "short";
    private static final String[] TYPES = {
            TYPE_INT, TYPE_LONG, TYPE_CHAR, TYPE_BOOLEAN, TYPE_FLOAT, TYPE_DOUBLE, TYPE_SHORT, TYPE_BYTE,
    };
    private static final Pattern EXTRAS_VALUE_CAST_PATTERN = Pattern.compile("(" + Utils.join("|", TYPES) + ")\\((true|false|-?[0-9]+(\\.[0-9]+)?|[^ ])\\)");
    String mTargetClass;
    String mAction;
    Bundle mExtras;
    boolean mIsForService;

    public IntentTag(String tagStr, int taggedTextStart) {
        super(tagStr, taggedTextStart);
        parseParams();
    }

    private void parseParams() {
        mTargetClass = getParam("class");
        if (mTargetClass == null || mTargetClass.length() == 0) {
            throw new BadParameterException("No class parameter specified at " + getTaggedTextStart());
        }

        String forService = getParam("for_service");
        mIsForService = forService != null && "true".equalsIgnoreCase(forService);

        mAction = getParam("action");
        List<Utils.Pair<String, String>> extraParams = getParamsMatching(EXTRAS_KEY_PATTERN);
        if (!extraParams.isEmpty()) {
            mExtras = new Bundle(extraParams.size());
            populateExtras(mExtras, extraParams);
        } else {
            mExtras = null;
        }
    }

    @Override
    public boolean matchesClosingTag(String tagName) {
        return NAME.equalsIgnoreCase(tagName);
    }

    @Override
    public void operate(Spannable builder, int taggedTextEnd) {
        builder.setSpan(new IntentSpan(mTargetClass, mAction, mExtras, mIsForService), getTaggedTextStart(), taggedTextEnd, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }

    static void populateExtras(Bundle target, List<Utils.Pair<String, String>> params) {
        for (Utils.Pair<String, String> param : params) {
            // clip off the x_ to get the extra name
            String name = param.first.substring(2);
            parseAndSetExtra(target, name, param.second);
        }
    }

    static void parseAndSetExtra(Bundle target, String name, String value) {
        Matcher m = EXTRAS_VALUE_CAST_PATTERN.matcher(value);
        if (m.matches()) {
            String type = m.group(1);
            String rawValue = m.group(2);
            try {
                switch (type) {
                    case TYPE_BOOLEAN:
                        target.putBoolean(name, rawValue != null && !"false".equalsIgnoreCase(rawValue) && !"0".equalsIgnoreCase(rawValue));
                        break;
                    case TYPE_BYTE:
                        target.putByte(name, Byte.parseByte(rawValue));
                        break;
                    case TYPE_CHAR:
                        try {
                            target.putChar(name, (char) Integer.parseInt(rawValue));
                        } catch (NumberFormatException nfe) {
                            target.putChar(name, rawValue.charAt(0));
                        }
                        break;
                    case TYPE_DOUBLE:
                        target.putDouble(name, Double.parseDouble(rawValue));
                        break;
                    case TYPE_FLOAT:
                        target.putFloat(name, Float.parseFloat(rawValue));
                        break;
                    case TYPE_INT:
                        target.putInt(name, Integer.parseInt(rawValue));
                        break;
                    case TYPE_LONG:
                        target.putLong(name, Long.parseLong(rawValue));
                        break;
                    case TYPE_SHORT:
                        target.putShort(name, Short.parseShort(rawValue));
                        break;
                    default:
                        target.putString(name, value);
                }
            } catch (NumberFormatException | ClassCastException e) {
                throw new BadParameterException("Bad value for param \""+name+"\": "+value, e);
            }
        } else {
            target.putString(name, value);
        }
    }

    private static class IntentSpan extends ClickableSpan {
        private final boolean mIsForService;
        private final String mAction;
        private final String mTargetClass;
        private final Bundle mExtras;

        IntentSpan(@NonNull String targetClass, @Nullable String action, @Nullable Bundle extras, boolean isForService) {
            mTargetClass = targetClass;
            mAction = action;
            mExtras = extras;
            mIsForService = isForService;
        }

        @Override
        public void onClick(View widget) {
            Intent intent = new Intent();
            intent.setClassName(widget.getContext(), mTargetClass);
            if (mAction != null) {
                intent.setAction(mAction);
            }
            if (mExtras != null) {
                intent.putExtras(mExtras);
            }

            if (mIsForService) {
                widget.getContext().startService(intent);
            } else {
                widget.getContext().startActivity(intent);
            }
        }
    }
}
