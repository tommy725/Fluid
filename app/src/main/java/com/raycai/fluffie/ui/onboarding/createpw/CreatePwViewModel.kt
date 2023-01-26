package com.raycai.fluffie.ui.onboarding.createpw

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.PhoneAuthProvider

class CreatePwViewModel() : ViewModel() {

    private val TAG = CreatePwViewModel::class.java.simpleName

    val password = MutableLiveData<String>()
    val retypePassword = MutableLiveData<String>()
    val showPwMismatchError = MutableLiveData<Boolean>()
    val enableSignUp = MutableLiveData<Boolean>()

    fun init(){
        showPwMismatchError.value = false

        password.observeForever {
            validate()
        }

        retypePassword.observeForever {
            validate()
        }
    }

    private fun validate(){
        showPwMismatchError.postValue(!password.value.isNullOrEmpty()
                && !retypePassword.value.isNullOrEmpty()
                && password.value != retypePassword.value)

        enableSignUp.postValue(password.value != null
                && retypePassword.value != null
                && password.value == retypePassword.value)
    }

}