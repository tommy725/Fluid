package com.raycai.fluffie.ui.onboarding.createpw

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
import com.raycai.fluffie.databinding.FragmentCreateAccBinding
import com.raycai.fluffie.databinding.FragmentCreatePwBinding
import java.util.concurrent.TimeUnit


class CreatePwFragment : BaseFragment() {

    private val TAG = CreatePwFragment::class.java.simpleName
    private lateinit var viewModel: CreatePwViewModel
    private lateinit var binding: FragmentCreatePwBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[CreatePwViewModel::class.java]
        binding = FragmentCreatePwBinding.inflate(inflater)
        binding.fragment = this
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        initData()
        setObservers()
        return binding.root
    }

    private fun setObservers() {
        viewModel.showPwMismatchError.observeForever {
            if (it){
                binding.tvError.visibility = View.VISIBLE
            }else{
                binding.tvError.visibility = View.GONE
            }
        }

        viewModel.enableSignUp.observeForever {
            binding.btnSignUp.isEnabled = it
        }
    }

    private fun initData() {
        viewModel.init()

    }

    fun onSignUpClicked(view: View) {
        view.hideKeyboard()
        (activity as MainActivity).loadCreateAccountTwoFragment(true)
    }

    fun goBack(view: View) {
        onBackPressed()
    }

    private fun log(msg: String) {
        AppLog.log(TAG, msg)
    }

}