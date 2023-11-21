package com.talki.booki.app.data.remote


import com.talki.booki.app.model.*

import com.talki.booki.app.model.Loginiew.LoginviewClass
import com.talki.booki.app.core.ApiConstant
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    /* Language list */
    @POST(ApiConstant.login_URL)
    @Headers("Content-Type:application/json")
    suspend fun getLogin(
        @Body task: LanguageBody
    ): Response<LoginviewClass>

}
