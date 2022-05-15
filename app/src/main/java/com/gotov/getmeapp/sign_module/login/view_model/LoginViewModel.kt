package com.gotov.getmeapp.sign_module.login.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gotov.getmeapp.sign_module.login.model.data.Login
import com.gotov.getmeapp.sign_module.login.model.repository.LoginRepository
import com.gotov.getmeapp.utils.model.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


private const val SUCCESS_CODE = 200
private const val INCORRECT_FIELD_CODE = 403


class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {
    private val status = MutableLiveData<Resource<LoginStatus>>()
    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun login(login: Login): LiveData<Resource<LoginStatus>> {
        status.postValue(Resource.loading(null))
        compositeDisposable.add(
            loginRepository.login(login)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    when (response.code()) {
                        SUCCESS_CODE -> status.postValue(Resource.success(LoginStatus.SUCCESS))
                        INCORRECT_FIELD_CODE -> status.postValue(Resource.error("", LoginStatus.INCORRECT_FIELD))
                        else -> {
                            val body: String?
                            body = response?.body().toString()

                            status.postValue(Resource.error(body, LoginStatus.SERVER_ERROR))
                        }
                    }
                }, { throwable ->
                    status.postValue(Resource.error("Err when try login: " + throwable.message, null))
                })
        )
        return status
    }
}
