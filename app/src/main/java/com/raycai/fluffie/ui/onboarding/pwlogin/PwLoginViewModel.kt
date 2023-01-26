package com.raycai.fluffie.ui.onboarding.pwlogin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PwLoginViewModel() : ViewModel() {

    private val TAG = PwLoginViewModel::class.java.simpleName

    val countryCode = MutableLiveData<String>()
    val mobileNo = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val termsChecked = MutableLiveData<Boolean>()
    val progress = MutableLiveData<Boolean>()
    val gotoAnimationFragment = MutableLiveData<Boolean>()

    //validators
    val enableLogin = MutableLiveData<Boolean>()


    fun init(){
        countryCode.value = ""
        mobileNo.value = ""
        password.value = ""
        termsChecked.value = false
        enableLogin.value = false

        countryCode.observeForever {
            validate()
        }

        mobileNo.observeForever {
            validate()
        }

        password.observeForever {
            validate()
        }

        termsChecked.observeForever {
            validate()
        }
    }

    private fun validate(){
        enableLogin.postValue(countryCode.value?.isNotEmpty()!!
                && mobileNo.value?.isNotEmpty()!!
                && password.value?.isNotEmpty()!!
                && termsChecked.value!!)
    }

}