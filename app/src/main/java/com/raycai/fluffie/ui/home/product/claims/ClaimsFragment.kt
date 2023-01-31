package com.raycai.fluffie.ui.home.product.claims

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.dam.bestexpensetracker.util.AppLog
import com.raycai.fluffie.HomeActivity
import com.raycai.fluffie.R
import com.raycai.fluffie.base.BaseActivity
import com.raycai.fluffie.base.BaseFragment
import com.raycai.fluffie.databinding.FragmentClaimsBinding

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
            if (isSafe() && it != null) {
                if (it.refined_category != null) {
                    if (it.refined_category!!.master_category_id != null) {
                        category = it.refined_category!!.master_category_id!!.master_category
                    }
                    if (!category.isEmpty())
                        category += "/"
                    category += it.refined_category!!.refined_category
                }
                binding.tvCategory.text = category
                binding.tvDesc.text = it.details

                binding.tvVegan.text = resources.getString(R.string.txt_84)
                binding.tvCruelty.text = resources.getString(R.string.txt_84)

                var keyBenefit = ""
                it.key_benefits!!.forEach{
                    if (it.benefit.contains("Vegan")) {
                        binding.tvVegan.text = (activity as BaseActivity).getString(R.string.txt_83)
                    }
                    if (it.benefit.contains("Cruelty-free")) {
                        binding.tvCruelty.text = (activity as BaseActivity).getString(R.string.txt_83)
                    }
                    if (!keyBenefit.isEmpty()) {
                        keyBenefit += ", "
                    }
                    keyBenefit += it.benefit
                }
                binding.tvBenefits.text = keyBenefit

                var claims = ""
                it.prod_claims!!.forEach {
                    if (!claims.isEmpty()) {
                        claims += ", "
                    }
                    claims += it.displaylabel
                }

                binding.tvClaims.text = claims
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