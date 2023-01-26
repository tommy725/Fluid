package com.raycai.fluffie.ui.splash

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
import com.raycai.fluffie.databinding.FragmentSplashBinding

class SplashFragment : BaseFragment() {

    private val TAG = SplashFragment::class.java.simpleName
    private lateinit var viewModel: SplashViewModel
    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[SplashViewModel::class.java]
        binding = FragmentSplashBinding.inflate(inflater)
        binding.fragment = this
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        initData()
        return binding.root
    }

    private fun initData() {
        val packageInfo = context?.packageManager?.getPackageInfo(
            context?.packageName!!,
            PackageManager.GET_ACTIVITIES
        )

        viewModel.versionNo.postValue("${packageInfo?.versionName}V")
        viewModel.initData()

        viewModel.gotoHome.observeForever {
            (activity as MainActivity).finish()
            startActivity(Intent(requireContext(), HomeActivity::class.java))
        }

        viewModel.gotoPreLogin.observeForever {
            (activity as MainActivity).loadPreLoginFragment(true)
        }
    }

    private fun log(msg: String) {
        AppLog.log(TAG, msg)
    }

}