package com.talki.booki.app.utils

import com.talki.booki.app.core.TalkiBookiApplication
import com.talki.booki.app.core.AppConstants

object UserUtils {
    /**
     * Get User Details
     */
    fun getIsUserLoggedIn(): Boolean {
        return TalkiBookiApplication.applicationInstance.tinyDB.readBoolean(
            AppConstants.KEY_PREFS_USER_IS_LOGGED_IN,
            false
        )
    }
}