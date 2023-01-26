package com.raycai.fluffie.ui.home.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.dam.bestexpensetracker.util.AppLog
import com.google.android.material.chip.Chip
import com.raycai.fluffie.HomeActivity
import com.raycai.fluffie.R
import com.raycai.fluffie.base.BaseFragment
import com.raycai.fluffie.databinding.FragmentBrowseBinding
import com.raycai.fluffie.databinding.FragmentHomeBinding
import com.raycai.fluffie.databinding.FragmentProfileBinding

class ProfileFragment : BaseFragment() {

    private val TAG = ProfileFragment::class.java.simpleName
    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        binding = FragmentProfileBinding.inflate(inflater)
        binding.fragment = this
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        setObservers()
        initData()
        return binding.root
    }

    private fun setObservers() {
        viewModel.selectedReviewType.observeForever {
            if (it != null){
                binding.tvNoReviews.text= "No reviews shared on any ${it?.lowercase()} yet."
            }
        }
    }

    private fun initData() {
        viewModel.initData()
        onTonersClicked(binding.cToners)

        val rr = ResourcesCompat.getFont(requireContext(), R.font.roboto_regular)
        binding.cToners.typeface = rr
        binding.cSerums.typeface = rr
        binding.cMoisturisers.typeface = rr
        binding.cOils.typeface = rr
    }

    fun onTonersClicked(view: View){
        resetChips()
        selectChip(view)
    }

    fun onSerumsClicked(view: View){
        resetChips()
        selectChip(view)
    }

    fun onMoisturisersClicked(view: View){
        resetChips()
        selectChip(view)
    }

    fun onOilsClicked(view: View){
        resetChips()
        selectChip(view)
    }

    private fun selectChip(view: View){
        (view as Chip).chipBackgroundColor = resources.getColorStateList(R.color.rose)
        viewModel.selectedReviewType.postValue((view as Chip).text as String?)
    }

    fun onPostAReviewClicked(view: View){

    }

    private fun resetChips(){
        binding.cToners.chipBackgroundColor = resources.getColorStateList(R.color.gray)
        binding.cSerums.chipBackgroundColor = resources.getColorStateList(R.color.gray)
        binding.cMoisturisers.chipBackgroundColor = resources.getColorStateList(R.color.gray)
        binding.cOils.chipBackgroundColor = resources.getColorStateList(R.color.gray)
    }

    private fun log(msg: String) {
        AppLog.log(TAG, msg)
    }

}