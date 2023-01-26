package com.raycai.fluffie.ui.splash

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class SplashViewModel() : ViewModel() {

    private val TAG = SplashViewModel::class.java.simpleName
    val versionNo = MutableLiveData<String>()
    val gotoPreLogin = MutableLiveData<Boolean>()
    val gotoHome = MutableLiveData<Boolean>()

    public fun initData() {
        val user = FirebaseAuth.getInstance().currentUser
        delay {
            gotoPreLogin.postValue(true)
        }
    }

    private fun delay(onComplete: () -> Unit) {
        var countDownTimer = object : CountDownTimer(1500, 500) {
            override fun onFinish() {
                onComplete()
            }

            override fun onTick(p0: Long) {}
        }
        countDownTimer.start()
    }


}