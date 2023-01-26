package com.raycai.fluffie.ui.home.product.claims

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dam.bestexpensetracker.util.AppLog
import com.raycai.fluffie.HomeActivity
import com.raycai.fluffie.R
import com.raycai.fluffie.base.BaseFragment
import com.raycai.fluffie.databinding.FragmentBrowseBinding
import com.raycai.fluffie.databinding.FragmentClaimsBinding
import com.raycai.fluffie.databinding.FragmentHomeBinding
import com.raycai.fluffie.databinding.FragmentProfileBinding
import com.raycai.fluffie.ui.home.productsearch.ProductSearchFragment

class ClaimsFragment : BaseFragment() {

    private val TAG = ClaimsFragment::class.java.simpleName
    private lateinit var viewModel: ClaimsViewModel
    private lateinit var binding: FragmentClaimsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[ClaimsViewModel::class.java]
        binding = FragmentClaimsBinding.inflate(inflater)
        binding.fragment = this
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        initData()
        setObservers()
        return binding.root
    }

    private fun setObservers() {
        viewModel.isShowingMore.observeForever {
            if (it != null) {
                if (it) {
                    binding.tvDesc.visibility = View.VISIBLE
                    binding.ivShowArrow.setImageResource(R.drawable.ic_up_arrow)
                } else {
                    binding.tvDesc.visibility = View.GONE
                    binding.ivShowArrow.setImageResource(R.drawable.ic_down)
                }
            }
        }
        viewModel.selectedProduct.observeForever {
            var category = ""
            if (it != null) {
                if (it.refined_category != null) {
                    category = it.refined_category!!.refined_category
                    if (it.refined_category!!.master_category_id != null) {
                        category = it.refined_category!!.master_category_id!!.master_category
                    }
                }
                binding.tvCategory.text = category
            }
        }
    }

    fun onProductClaimsClicked(view: View) {
        viewModel.onShowMoreClicked()
    }

    private fun initData() {
        viewModel.selectedProduct.postValue((activity as HomeActivity).selectedProduct)
        viewModel.initData()
    }


    private fun log(msg: String) {
        AppLog.log(TAG, msg)
    }

}