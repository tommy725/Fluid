package com.raycai.fluffie.ui.onboarding.resetpw

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.dam.bestexpensetracker.util.AppLog
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.raycai.fluffie.MainActivity
import com.raycai.fluffie.R
import com.raycai.fluffie.base.BaseFragment
import com.raycai.fluffie.databinding.FragmentResetPwBinding
import java.util.concurrent.TimeUnit


class ResetPwFragment : BaseFragment() {

    private val TAG = ResetPwFragment::class.java.simpleName
    private lateinit var viewModel: ResetPwViewModel
    private lateinit var binding: FragmentResetPwBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[ResetPwViewModel::class.java]
        binding = FragmentResetPwBinding.inflate(inflater)
        binding.fragment = this
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        initData()
        setObservers()
        return binding.root
    }

    private fun setObservers() {
        viewModel.progress.observeForever {
            if (it){
                showProgress()
            }else{
                hideProgress()
            }
        }

        viewModel.enableSubmitBtn.observeForever {
            binding.btnSubmit.isEnabled = it
        }

        viewModel.countDownText.observeForever {
            binding.tvCountDown.text = it
        }

        viewModel.showCountDown.observeForever {
            if (it) {
                binding.btnOtp.visibility = View.GONE
                binding.tvCountDown.visibility = View.VISIBLE
            } else {
                binding.btnOtp.visibility = View.VISIBLE
                binding.tvCountDown.visibility = View.GONE
            }
        }

        viewModel.otpSent.observeForever {
            if (it) {
                binding.btnOtp.text = "Resend"
            }
        }

        viewModel.enableOtp.observeForever {
            binding.etVerificationCode.isEnabled = it
            binding.btnOtp.isEnabled = it
        }
    }

    private fun initData() {
        viewModel.init()

        //country code picker
        viewModel.countryCode.postValue(binding.countryCodePicker.selectedCountryCode)

        binding.countryCodePicker.setTextSize(16.dpToPx.toInt())
        binding.countryCodePicker.setOnCountryChangeListener {
            viewModel.countryCode.postValue(binding.countryCodePicker.selectedCountryCode)
        }
    }

    fun onPhoneNumberClearClicked(view: View) {
        view.hideKeyboard()
        binding.etPhoneNo.setText("")
    }

    fun onSubmitClicked(view: View) {
        view.hideKeyboard()
        verifyOtp()
    }

    fun onOtpClicked(view: View) {
        view.hideKeyboard()
        sendOtp()
    }

    fun troubleLogin(view: View) {
        view.hideKeyboard()

    }


    private fun verifyOtp() {
        log("verifyOtp()")
        viewModel.progress.postValue(true)
        val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
            viewModel.verificationId!!,
            viewModel.verificationCode.value!!
        )
        signInWithPhoneAuthCredential(credential)
    }

    private fun sendOtp() {
        binding.etVerificationCode.setText("")

        log("sendOtp")
        val phoneNumber = "+${viewModel.countryCode.value}${viewModel.mobileNo.value}"
        log("Phone number: $phoneNumber")

        val options = PhoneAuthOptions.newBuilder(Firebase.auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(viewModel.otpTimeOut.toLong(), TimeUnit.SECONDS)
            .setActivity(activity as MainActivity)
            .setCallbacks(object : OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    log("Verification completed.")
                    signInWithPhoneAuthCredential(credential)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    log("Unable to send otp.")
                    viewModel.progress.postValue(false)
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    log("verification code sent.")
                    viewModel.verificationId = verificationId
                    viewModel.resendToken = token
                    viewModel.otpSentCount = viewModel.otpSentCount + 1
                    viewModel.otpSent.postValue(true)
                    viewModel.progress.postValue(false)
                }
            }).build()

        viewModel.progress.postValue(true)
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        log("signInWithPhoneAuthCredential()")
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    viewModel.progress.postValue(false)
                    (activity as MainActivity).loadUpdatePasswordFragment(true)
                } else {
                    viewModel.progress.postValue(false)
                    showMsgDialog(getString(R.string.app_name), task.exception?.message!!){
                    }
                }
            }
    }

    fun goBack(view: View) {
        onBackPressed()
    }

    private fun log(msg: String) {
        AppLog.log(TAG, msg)
    }

}