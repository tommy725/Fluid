package com.raycai.fluffie.ui.onboarding.prelogin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.dam.bestexpensetracker.data.constant.Const
import com.dam.bestexpensetracker.util.AppLog
import com.raycai.fluffie.MainActivity
import com.raycai.fluffie.R
import com.raycai.fluffie.base.BaseFragment
import com.raycai.fluffie.databinding.FragmentPreloginBinding
import com.squareup.picasso.Picasso

class PreLoginFragment : BaseFragment() {

    private val TAG = PreLoginFragment::class.java.simpleName
    private lateinit var viewModel: PreLoginViewModel
    private lateinit var binding: FragmentPreloginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[PreLoginViewModel::class.java]
        binding = FragmentPreloginBinding.inflate(inflater)
        binding.fragment = this
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        initData()
        return binding.root
    }

    private fun initData() {
    }

    fun onSignUpClicked(view: View) {
        (activity as MainActivity).loadCreateAccFragment(true)
    }

    fun onLoginClicked(view: View) {
        if (Const.DEVELOPER_MODE) {
            (activity as MainActivity).loadLoginAnimationFragment();
        } else {
            (activity as MainActivity).loadPhoneLoginFragment(true)
        }
    }

    private fun log(msg: String) {
        AppLog.log(TAG, msg)
    }

}