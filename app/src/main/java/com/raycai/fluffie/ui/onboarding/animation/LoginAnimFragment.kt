package com.raycai.fluffie.ui.onboarding.animation

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.dam.bestexpensetracker.util.AppLog
import com.raycai.fluffie.HomeActivity
import com.raycai.fluffie.MainActivity
import com.raycai.fluffie.base.BaseFragment
import com.raycai.fluffie.databinding.FragmentLoginAnimBinding
import com.raycai.fluffie.databinding.FragmentSplashBinding
import com.raycai.fluffie.ui.onboarding.phonelogin.PhoneLoginFragment

class LoginAnimFragment : BaseFragment() {

    private val TAG = LoginAnimFragment::class.java.simpleName
    private lateinit var viewModel: LoginAnimViewModel
    private lateinit var binding: FragmentLoginAnimBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[LoginAnimViewModel::class.java]
        binding = FragmentLoginAnimBinding.inflate(inflater)
        binding.fragment = this
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        initData()
        return binding.root
    }

    private fun initData() {
        viewModel.gotoHome.observeForever {
            (activity as MainActivity).finish()
            startActivity(Intent(requireContext(), HomeActivity::class.java))
        }

        viewModel.initData()
    }

    private fun log(msg: String) {
        AppLog.log(TAG, msg)
    }

}