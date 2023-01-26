package com.raycai.fluffie.ui.home.index

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.dam.bestexpensetracker.util.AppLog
import com.raycai.fluffie.base.BaseFragment
import com.raycai.fluffie.databinding.FragmentBrowseBinding
import com.raycai.fluffie.databinding.FragmentHomeBinding
import com.raycai.fluffie.databinding.FragmentIndexBinding
import com.raycai.fluffie.databinding.FragmentProfileBinding

class IndexFragment : BaseFragment() {

    private val TAG = IndexFragment::class.java.simpleName
    private lateinit var viewModel: IndexViewModel
    private lateinit var binding: FragmentIndexBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[IndexViewModel::class.java]
        binding = FragmentIndexBinding.inflate(inflater)
        binding.fragment = this
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        initData()
        return binding.root
    }

    private fun initData() {

    }

    private fun log(msg: String) {
        AppLog.log(TAG, msg)
    }

}