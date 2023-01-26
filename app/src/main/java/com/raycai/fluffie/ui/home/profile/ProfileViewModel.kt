package com.raycai.fluffie.ui.home.profile

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class ProfileViewModel() : ViewModel() {

    private val TAG = ProfileViewModel::class.java.simpleName

    val selectedReviewType = MutableLiveData<String>()

    public fun initData() {

    }


}