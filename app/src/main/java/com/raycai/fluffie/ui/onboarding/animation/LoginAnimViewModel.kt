package com.raycai.fluffie.ui.onboarding.animation

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginAnimViewModel() : ViewModel() {

    private val TAG = LoginAnimViewModel::class.java.simpleName
    val gotoHome = MutableLiveData<Boolean>()

    public fun initData(){
        delay {
            gotoHome.postValue(true)
        }
    }

    private fun delay(onComplete: () -> Unit) {
        var countDownTimer = object : CountDownTimer(2000, 500) {
            override fun onFinish() {
                onComplete()
            }

            override fun onTick(p0: Long) {}
        }
        countDownTimer.start()
    }


}