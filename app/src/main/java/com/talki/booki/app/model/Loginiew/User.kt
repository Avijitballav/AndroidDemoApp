package com.talki.booki.app.model.Loginiew

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class User {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("phone")
    @Expose
    var phone: String? = null

    @SerializedName("login_type")
    @Expose
    var loginType: String? = null

    @SerializedName("is_active")
    @Expose
    var isActive: Int? = null

    @SerializedName("expired")
    @Expose
    var expired: Boolean? = null

    @SerializedName("profile_img")
    @Expose
    var profile_img: String? = null
}