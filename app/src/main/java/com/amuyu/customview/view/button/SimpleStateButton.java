package com.amuyu.customview.view.button;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.StateSet;

import com.amuyu.customview.R;




public class SimpleStateButton extends AppCompatButton {
    private final String TAG = SimpleStateButton.class.getSimpleName();

    public static final int IDLE_STATE_PROGRESS = 0;
    public static final int ERROR_STATE_PROGRESS = -1;
    public static final int SUCCESS_STATE_PROGRESS = 100;

    private GradientDrawable background;

    private ColorStateList mCurColorState;
    private ColorStateList mIdleColorState;
    private ColorStateList mCompleteColorState;
    private ColorStateList mErrorColorState;

    private StateListDrawable mIdleStateDrawable;
    private StateListDrawable mCompleteStateDrawable;
    private StateListDrawable mErrorStateDrawable;

    private State mState;
    private String mIdleText;
    private String mCompleteText;
    private String mErrorText;


    private int mIconComplete;
    private int mIconError;


    private boolean mConfigurationChanged;

    private enum State {
        IDLE, COMPLETE, ERROR
    }

    private boolean mMorphingInProgress;

    public SimpleStateButton(Context context) {
        super(context);
        init(context, null);
    }

    public SimpleStateButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SimpleStateButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {
        initAttributes(context, attributeSet);
        mCurColorState = mIdleColorState;

        setText(mIdleText);

        initIdleStateDrawable();
        setBackgroundCompat(mIdleStateDrawable);
    }

    private void initErrorStateDrawable() {
        int colorPressed = getPressedColor(mErrorColorState);

        mErrorStateDrawable = new StateListDrawable();

        mErrorStateDrawable.addState(new int[]{android.R.attr.state_pressed}, createDrawable(colorPressed));
        mErrorStateDrawable.addState(StateSet.WILD_CARD, background);
    }

    private void initCompleteStateDrawable() {
        int colorPressed = getPressedColor(mCompleteColorState);

        mCompleteStateDrawable = new StateListDrawable();

        mCompleteStateDrawable.addState(new int[]{android.R.attr.state_pressed}, createDrawable(colorPressed));
        mCompleteStateDrawable.addState(StateSet.WILD_CARD, background);
    }

    private void initIdleStateDrawable() {
        int colorNormal = getNormalColor(mIdleColorState);
        int colorPressed = getPressedColor(mIdleColorState);
        int colorFocused = getFocusedColor(mIdleColorState);
        int colorDisabled = getDisabledColor(mIdleColorState);
        if (background == null) {
            background = createDrawable(colorNormal);
        }

        mIdleStateDrawable = new StateListDrawable();

        mIdleStateDrawable.addState(new int[]{android.R.attr.state_pressed}, createDrawable(colorPressed));
        mIdleStateDrawable.addState(new int[]{android.R.attr.state_focused}, createDrawable(colorFocused));
        mIdleStateDrawable.addState(new int[]{-android.R.attr.state_enabled}, createDrawable(colorDisabled));
        mIdleStateDrawable.addState(StateSet.WILD_CARD, background);
    }

    private int getNormalColor(ColorStateList colorStateList) {
        return colorStateList.getColorForState(new int[]{android.R.attr.state_enabled}, 0);
    }

    private int getPressedColor(ColorStateList colorStateList) {
        return colorStateList.getColorForState(new int[]{android.R.attr.state_pressed}, 0);
    }

    private int getFocusedColor(ColorStateList colorStateList) {
        return colorStateList.getColorForState(new int[]{android.R.attr.state_focused}, 0);
    }

    private int getDisabledColor(ColorStateList colorStateList) {
        return colorStateList.getColorForState(new int[]{-android.R.attr.state_enabled}, 0);
    }

    private GradientDrawable createDrawable(int color) {
        GradientDrawable drawable = (GradientDrawable) getResources().getDrawable(R.drawable.cpb_background).mutate();
        drawable.setColor(color);
        return drawable;
    }

    @Override
    protected void drawableStateChanged() {
        if (mState == State.COMPLETE) {
            initCompleteStateDrawable();
            setBackgroundCompat(mCompleteStateDrawable);
        } else if (mState == State.IDLE) {
            initIdleStateDrawable();
            setBackgroundCompat(mIdleStateDrawable);
        } else if (mState == State.ERROR) {
            initErrorStateDrawable();
            setBackgroundCompat(mErrorStateDrawable);
        }


        super.drawableStateChanged();
    }

    private void initAttributes(Context context, AttributeSet attributeSet) {
        TypedArray attr = getTypedArray(context, attributeSet, R.styleable.CircularProgressButton);
        if (attr == null) {
            return;
        }

        try {

            mIdleText = attr.getString(R.styleable.CircularProgressButton_cpb_textIdle);
            mCompleteText = attr.getString(R.styleable.CircularProgressButton_cpb_textComplete);
            mErrorText = attr.getString(R.styleable.CircularProgressButton_cpb_textError);


            mIconComplete = attr.getResourceId(R.styleable.CircularProgressButton_cpb_iconComplete, 0);
            mIconError = attr.getResourceId(R.styleable.CircularProgressButton_cpb_iconError, 0);


            int blue = getColor(R.color.cpb_blue);
            int white = getColor(R.color.cpb_white);
            int grey = getColor(R.color.cpb_grey);

            int idleStateSelector = attr.getResourceId(R.styleable.CircularProgressButton_cpb_selectorIdle,
                    R.drawable.cpb_idle_state_selector);
            mIdleColorState = getResources().getColorStateList(idleStateSelector);

            int completeStateSelector = attr.getResourceId(R.styleable.CircularProgressButton_cpb_selectorComplete,
                    R.drawable.cpb_complete_state_selector);
            mCompleteColorState = getResources().getColorStateList(completeStateSelector);

            int errorStateSelector = attr.getResourceId(R.styleable.CircularProgressButton_cpb_selectorError,
                    R.drawable.cpb_error_state_selector);
            mErrorColorState = getResources().getColorStateList(errorStateSelector);

        } finally {
            attr.recycle();
        }
    }

    protected int getColor(int id) {
        return getResources().getColor(id);
    }

    protected TypedArray getTypedArray(Context context, AttributeSet attributeSet, int[] attr) {
        return context.obtainStyledAttributes(attributeSet, attr, 0, 0);
    }

    private void animate(ColorStateList newColorState, final OnAnimationEndListener listener) {
        mMorphingInProgress = true;

        ObjectAnimator bgColorAnimation = ObjectAnimator.ofInt(background, "color", getNormalColor(mCurColorState), getNormalColor(newColorState));
        bgColorAnimation.setEvaluator(new ArgbEvaluator());
        bgColorAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                listener.onAnimationEnd();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        bgColorAnimation.start();
    }

    private OnAnimationEndListener mCompleteStateListener = new OnAnimationEndListener() {
        @Override
        public void onAnimationEnd() {
            if (mIconComplete != 0) {
                setText(null);
                setIcon(mIconComplete);
            } else {
                setText(mCompleteText);
            }
            mMorphingInProgress = false;
            mState = State.COMPLETE;
            mCurColorState = mCompleteColorState;
        }
    };

    private OnAnimationEndListener mIdleStateListener = new OnAnimationEndListener() {
        @Override
        public void onAnimationEnd() {
            removeIcon();
            setText(mIdleText);
            mMorphingInProgress = false;
            mState = State.IDLE;
            mCurColorState = mIdleColorState;
        }
    };

    private OnAnimationEndListener mErrorStateListener = new OnAnimationEndListener() {
        @Override
        public void onAnimationEnd() {
            if (mIconError != 0) {
                setText(null);
                setIcon(mIconError);
            } else {
                setText(mErrorText);
            }
            mMorphingInProgress = false;
            mState = State.ERROR;
            mCurColorState = mErrorColorState;
        }
    };

    private void setIcon(int icon) {
        Drawable drawable = getResources().getDrawable(icon);
        if (drawable != null) {
            int padding = (getWidth() / 2) - (drawable.getIntrinsicWidth() / 2);
            setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0);
            setPadding(padding, 0, 0, 0);
        }
    }

    protected void removeIcon() {
        setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        setPadding(0, 0, 0, 0);
    }

    /**
     * Set the View's background. Masks the API changes made in Jelly Bean.
     */
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public void setBackgroundCompat(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
    }

    public boolean isIdleState() {
        return (mState == State.IDLE);
    }

    public void completed() {
        setState(State.COMPLETE);
    }

    public void idled() {
        setState(State.IDLE);
    }

    public void errored() {
        setState(State.ERROR);
    }


    private void setState(State state) {
        if (mMorphingInProgress || getWidth() == 0) {
            return;
        }

        if (state == State.COMPLETE) {
            animate(mCompleteColorState, mCompleteStateListener);
        } else if (state == State.ERROR) {
            animate(mErrorColorState, mErrorStateListener);
        } else if (state == State.IDLE) {
            animate(mIdleColorState, mIdleStateListener);
        }
    }


    public void setBackgroundColor(int color) {
        background.setColor(color);
    }

    public void setStrokeColor(int color) {
//        background.setStrokeColor(color);
    }

    public String getIdleText() {
        return mIdleText;
    }

    public String getCompleteText() {
        return mCompleteText;
    }

    public String getErrorText() {
        return mErrorText;
    }

    public void setIdleText(String text) {
        mIdleText = text;
    }

    public void setCompleteText(String text) {
        mCompleteText = text;
    }

    public void setErrorText(String text) {
        mErrorText = text;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed && mState == null) {
            idled();
        }
    }


}
