package com.talki.booki.app.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.talki.booki.app.model.LanguageBody
import com.talki.booki.app.model.Loginiew.LoginviewClass
import com.talki.booki.app.data.remote.Repository
import com.talki.booki.app.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel  @Inject constructor
    (
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    private val _response: MutableLiveData<NetworkResult<LoginviewClass>> = MutableLiveData()
    val response: LiveData<NetworkResult<LoginviewClass>> = _response

    fun fetchLoginResponse(task: LanguageBody) = viewModelScope.launch {
        repository.getLogin(task).collect { values ->
            _response.value = values
        }
    }

}