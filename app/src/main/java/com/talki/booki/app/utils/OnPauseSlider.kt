package com.talki.booki.app.utils

import android.app.Activity
import com.talki.booki.app.R


/**
 * Created by AB on 03/06/19.
 */

class OnPauseSlider(activity: Activity, transitionflag: Int) {

    init {
        if (transitionflag == 2) {
            activity.overridePendingTransition(
                R.anim.right_slide_in_one,
                    R.anim.right_slide_out_one)
        } else if (transitionflag == 1) {
            activity.overridePendingTransition(R.anim.right_slide_in,
                    R.anim.right_slide_out)
        }
    }
}
