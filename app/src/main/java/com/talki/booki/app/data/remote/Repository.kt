package com.talki.booki.app.data.remote



import com.talki.booki.app.model.*

import com.talki.booki.app.model.Loginiew.LoginviewClass

import com.talki.booki.app.utils.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


@ActivityRetainedScoped
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : BaseApiResponse() {



    suspend fun getLogin(task: LanguageBody): Flow<NetworkResult<LoginviewClass>> {
        return flow<NetworkResult<LoginviewClass>> {
            emit(safeApiCall { remoteDataSource.getLogin(task) })
        }.flowOn(Dispatchers.IO)
    }


}
