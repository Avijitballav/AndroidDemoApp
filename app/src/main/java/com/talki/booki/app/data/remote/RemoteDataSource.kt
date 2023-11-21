package com.talki.booki.app.data.remote

import com.talki.booki.app.model.*
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
        private val categorywiseProductService: ApiService,
      ) {



    /* Login */
    suspend fun getLogin(task:LanguageBody) =
        categorywiseProductService.getLogin(task)

}