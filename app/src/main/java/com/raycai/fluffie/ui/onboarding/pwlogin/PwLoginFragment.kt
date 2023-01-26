package com.raycai.fluffie.ui.onboarding.pwlogin

import android.content.Intent
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
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthResult
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
import com.raycai.fluffie.databinding.FragmentPhoneLoginBinding
import com.raycai.fluffie.databinding.FragmentPwLoginBinding
import java.util.concurrent.TimeUnit


class PwLoginFragment : BaseFragment() {

    private val TAG = PwLoginFragment::class.java.simpleName
    private lateinit var viewModel: PwLoginViewModel
    private lateinit var binding: FragmentPwLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[PwLoginViewModel::class.java]
        binding = FragmentPwLoginBinding.inflate(inflater)
        binding.fragment = this
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        initData()
        setObservers()
        return binding.root
    }

    private fun setObservers() {
        viewModel.gotoAnimationFragment.observeForever {
            if (it){
                (activity as MainActivity).loadLoginAnimationFragment()
            }
        }

        viewModel.progress.observeForever {
            if (it){
                showProgress()
            }else{
                hideProgress()
            }
        }

        viewModel.enableLogin.observeForever {
            binding.btnLogin.isEnabled = it
        }

        viewModel.termsChecked.observeForever {
            binding.rbTerms.isChecked = it
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

        //terms text
        val wordtoSpan: Spannable =
            SpannableString("By logging in you agree to our Terms of Use and Privacy Policy.")

        val termsClicked: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                (activity as MainActivity).loadTermsFragment(true)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                setTermsHighlight(ds)
            }
        }

        val privacyPolicy: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                Toast.makeText(requireContext(), "Privacy", Toast.LENGTH_SHORT).show()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                setTermsHighlight(ds)
            }
        }

        wordtoSpan.setSpan(termsClicked, 31, 43, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        wordtoSpan.setSpan(privacyPolicy, 48, 63, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.tvTerms.text = wordtoSpan
        binding.tvTerms.movementMethod = LinkMovementMethod.getInstance()
        binding.tvTerms.highlightColor = ContextCompat.getColor(requireContext(), R.color.lite_gray)
    }


    fun onTermsClicked(view: View) {
        view.hideKeyboard()
        viewModel.termsChecked.postValue(!viewModel.termsChecked.value!!)
    }

    fun onPhoneNumberClearClicked(view: View) {
        view.hideKeyboard()
        binding.etPhoneNo.setText("")
    }

    fun onLoginClicked(view: View) {
        view.hideKeyboard()

    }

    fun loginUsingPhoneNumber(view: View) {
        view.hideKeyboard()
        (activity as MainActivity).loadPhoneLoginFragment(true)
    }

    fun troubleLogin(view: View) {
        view.hideKeyboard()
        (activity as MainActivity).loadResetPasswordFragment(true)
    }

    private fun setTermsHighlight(ds: TextPaint) {
        ds.textSize = 16.dpToPx
        ds.isUnderlineText = true
        ds.color = ContextCompat.getColor(requireContext(), R.color.lite_blue)
        ds.typeface = ResourcesCompat.getFont(requireContext(), R.font.roboto_regular)
    }

    fun goBack(view: View) {
        onBackPressed()
    }

    private fun log(msg: String) {
        AppLog.log(TAG, msg)
    }

}