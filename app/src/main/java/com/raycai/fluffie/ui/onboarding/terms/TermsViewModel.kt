package com.raycai.fluffie.ui.onboarding.terms

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TermsViewModel() : ViewModel() {

    private val TAG = TermsViewModel::class.java.simpleName
    val versionNo = MutableLiveData<String>()

    private fun delay(onComplete: () -> Unit) {
        var countDownTimer = object : CountDownTimer(400, 400) {
            override fun onFinish() {
                onComplete()
            }

            override fun onTick(p0: Long) {}
        }
        countDownTimer.start()
    }


}