package com.talki.booki.app.model.Loginiew

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginviewClass {
    @SerializedName("status")
    @Expose
    var status: Int? = null

    @SerializedName("user")
    @Expose
    var user: User? = null

    @SerializedName("token")
    @Expose
    var token: String? = null

    @SerializedName("message")
    @Expose
    var message: String? = null
}