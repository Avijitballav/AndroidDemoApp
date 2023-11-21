package com.talki.booki.app.model.Logout

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LogoutClass {
    @SerializedName("status")
    @Expose
    var status: Int? = null

    @SerializedName("message")
    @Expose
    var message: String? = null
}