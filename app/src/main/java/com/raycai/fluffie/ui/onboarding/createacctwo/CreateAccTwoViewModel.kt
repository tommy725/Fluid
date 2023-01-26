package com.raycai.fluffie.ui.onboarding.createacctwo

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.PhoneAuthProvider
import java.util.Date

class CreateAccTwoViewModel() : ViewModel() {

    private val TAG = CreateAccTwoViewModel::class.java.simpleName

    enum class Gender{
        NOT_SELECTED, FEMALE, MALE, NON_BINARY
    }

    enum class SkinTone{
        NOT_SELECTED, TONE_ONE, TONE_TWO, TONE_THREE, TONE_FOUR, TONE_FIVE, TONE_SIX, TONE_SEVEN, TONE_EIGHT
    }

    val gender = MutableLiveData<Gender>()
    val dob = MutableLiveData<Date>()
    val skinTone = MutableLiveData<SkinTone>()
    val enableNext = MutableLiveData<Boolean>()

    fun init(){
        gender.value = Gender.NOT_SELECTED
        skinTone.value= SkinTone.NOT_SELECTED

        gender.observeForever {
            validate()
        }

        dob.observeForever {
            validate()
        }

        skinTone.observeForever {
            validate()
        }
    }

    private fun validate(){
        enableNext.postValue(gender.value != Gender.NOT_SELECTED
                && dob.value != null
                && skinTone.value != SkinTone.NOT_SELECTED)
    }

}