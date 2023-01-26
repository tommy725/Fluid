package com.raycai.fluffie.ui.onboarding.createaccthree

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.PhoneAuthProvider
import java.util.Date

class CreateAccThreeViewModel() : ViewModel() {

    private val TAG = CreateAccThreeViewModel::class.java.simpleName

    enum class SkinType{
        NOT_SELECTED, OILY, DRY, NORMAL, SENSITIVE, COMBINATION, NOT_SURE
    }

    enum class Concern{
        NOT_SELECTED, C_ONE, C_TWO, C_THREE, C_FOUR, C_FIVE, C_SIX, C_SEVEN, C_EIGHT
    }

    val skinType = MutableLiveData<SkinType>()
    val concern = MutableLiveData<Concern>()
    val enableFinish = MutableLiveData<Boolean>()

    fun init(){
        skinType.value = SkinType.NOT_SELECTED
        concern.value= Concern.NOT_SELECTED

        skinType.observeForever {
            validate()
        }

        concern.observeForever {
            validate()
        }
    }

    private fun validate(){
        enableFinish.postValue(skinType.value != SkinType.NOT_SELECTED
                && concern.value != Concern.NOT_SELECTED)
    }

}