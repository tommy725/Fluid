package com.raycai.fluffie.ui.onboarding.createacc

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.PhoneAuthProvider

class CreateAccViewModel() : ViewModel() {

    private val TAG = CreateAccViewModel::class.java.simpleName

    val countryCode = MutableLiveData<String>()
    val mobileNo = MutableLiveData<String>()
    val fullName = MutableLiveData<String>()
    val verificationCode = MutableLiveData<String>()
    val termsChecked = MutableLiveData<Boolean>()
    val progress = MutableLiveData<Boolean>()
    val gotoCreatePassword = MutableLiveData<Boolean>()

    //validators
    val enableOtp = MutableLiveData<Boolean>()
    val otpSent = MutableLiveData<Boolean>()
    val otpResendCountDownInProgress = MutableLiveData<Boolean>()
    val showCountDown = MutableLiveData<Boolean>()
    val countDownText = MutableLiveData<String>()
    val enableLogin = MutableLiveData<Boolean>()

    //otp
    val otpTimeOut = 60
    var otpSentCount = 0
    var verificationId: String? = null
    var resendToken: PhoneAuthProvider.ForceResendingToken? = null


    fun init(){
        countDownText.value = "Resend in $otpTimeOut"
        countryCode.value = ""
        mobileNo.value = ""
        verificationCode.value = ""
        termsChecked.value = false
        enableOtp.value = false
        otpSent.value = false
        showCountDown.value = false
        enableLogin.value = false

        otpSent.observeForever {
            if (it){
                var count = otpTimeOut

                val timer = object: CountDownTimer(otpTimeOut * 1000L, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        count -= 1
                        countDownText.postValue("Resend in $count")
                    }

                    override fun onFinish() {
                        Log.i(TAG, "onTick: finished")
                        showCountDown.postValue(false)
                        otpResendCountDownInProgress.postValue(false)
                    }
                }

                timer.start()
                showCountDown.postValue(true)
                otpResendCountDownInProgress.postValue(true)
            }
        }

        countryCode.observeForever {
            otpValidate()
        }

        mobileNo.observeForever {
            otpValidate()
        }

        verificationCode.observeForever {
            validate()
        }

        termsChecked.observeForever {
            validate()
        }
    }

    private fun otpValidate(){
        enableOtp.postValue(countryCode.value?.isNotEmpty()!! && mobileNo.value?.isNotEmpty()!!)
        validate()
    }

    private fun validate(){
        enableLogin.postValue(countryCode.value?.isNotEmpty()!!
                && mobileNo.value?.isNotEmpty()!!
                && verificationId != null
                && verificationCode.value?.length == 6
                && termsChecked.value!!)
    }

}