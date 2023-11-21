package com.talki.booki.app.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import com.talki.booki.app.utils.Resource.Status.*
import java.sql.DriverManager.println

fun <T> performGetOperation(networkCall: suspend () -> Resource<T>): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())
//        val source = databaseQuery.invoke().map { Resource.success(it) }
//        emitSource(source)

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == SUCCESS) {
            println("***********"+responseStatus.data!!)
            Resource.success(responseStatus.data!!)

        } else if (responseStatus.status == ERROR) {
            emit(Resource.error(responseStatus.message!!))

        }
    }