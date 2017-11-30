package com.amuyu.customview.view.button;


import android.support.annotation.IntDef;

import static com.amuyu.customview.view.button.SimpleStateButton.ERROR_STATE_PROGRESS;
import static com.amuyu.customview.view.button.SimpleStateButton.IDLE_STATE_PROGRESS;
import static com.amuyu.customview.view.button.SimpleStateButton.SUCCESS_STATE_PROGRESS;

@IntDef({
        IDLE_STATE_PROGRESS, ERROR_STATE_PROGRESS, SUCCESS_STATE_PROGRESS
})
public @interface State {
}
