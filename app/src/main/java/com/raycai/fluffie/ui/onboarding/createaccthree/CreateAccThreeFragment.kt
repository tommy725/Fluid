package com.raycai.fluffie.ui.onboarding.createaccthree

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.dam.bestexpensetracker.data.constant.Const
import com.dam.bestexpensetracker.util.AppLog
import com.raycai.fluffie.MainActivity
import com.raycai.fluffie.R
import com.raycai.fluffie.base.BaseFragment
import com.raycai.fluffie.data.prefs.Prefs
import com.raycai.fluffie.databinding.FragmentCreateAccThreeBinding
import com.raycai.fluffie.databinding.FragmentCreateAccTwoBinding
import com.raycai.fluffie.ui.onboarding.createacctwo.CreateAccTwoViewModel
import java.text.SimpleDateFormat
import java.util.*


class CreateAccThreeFragment : BaseFragment() {

    private val TAG = CreateAccThreeFragment::class.java.simpleName
    private lateinit var viewModel: CreateAccThreeViewModel
    private lateinit var binding: FragmentCreateAccThreeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[CreateAccThreeViewModel::class.java]
        binding = FragmentCreateAccThreeBinding.inflate(inflater)
        binding.fragment = this
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        initData()
        setObservers()
        return binding.root
    }

    private fun setObservers() {
        viewModel.enableFinish.observeForever {
            binding.btnFinish.isEnabled = it
        }

        viewModel.concern.observeForever {
            if (it != null){
                resetConcerns()
                when (it) {
                    CreateAccThreeViewModel.Concern.C_ONE -> {
                        binding.cCon1.chipBackgroundColor = resources.getColorStateList(R.color.rose)
                        binding.cCon1.chipStrokeWidth = 0f
                    }
                    CreateAccThreeViewModel.Concern.C_TWO -> {
                        binding.cCon2.chipBackgroundColor = resources.getColorStateList(R.color.rose)
                        binding.cCon2.chipStrokeWidth = 0f
                    }
                    CreateAccThreeViewModel.Concern.C_THREE -> {
                        binding.cCon3.chipBackgroundColor = resources.getColorStateList(R.color.rose)
                        binding.cCon3.chipStrokeWidth = 0f
                    }
                    CreateAccThreeViewModel.Concern.C_FOUR -> {
                        binding.cCon4.chipBackgroundColor = resources.getColorStateList(R.color.rose)
                        binding.cCon4.chipStrokeWidth = 0f
                    }
                    CreateAccThreeViewModel.Concern.C_FIVE -> {
                        binding.cCon5.chipBackgroundColor = resources.getColorStateList(R.color.rose)
                        binding.cCon5.chipStrokeWidth = 0f
                    }
                    CreateAccThreeViewModel.Concern.C_SIX -> {
                        binding.cCon6.chipBackgroundColor = resources.getColorStateList(R.color.rose)
                        binding.cCon6.chipStrokeWidth = 0f
                    }
                    CreateAccThreeViewModel.Concern.C_SEVEN -> {
                        binding.cCon7.chipBackgroundColor = resources.getColorStateList(R.color.rose)
                        binding.cCon7.chipStrokeWidth = 0f
                    }
                    CreateAccThreeViewModel.Concern.C_EIGHT -> {
                        binding.cCon8.chipBackgroundColor = resources.getColorStateList(R.color.rose)
                        binding.cCon8.chipStrokeWidth = 0f
                    }
                    else -> {}
                }
            }
        }

        viewModel.skinType.observeForever {
            if (it != null){
                resetSkinType()
                when (it) {
                    CreateAccThreeViewModel.SkinType.OILY -> {
                        binding.cOily.chipBackgroundColor = resources.getColorStateList(R.color.rose)
                        binding.cOily.chipStrokeWidth = 0f
                    }
                    CreateAccThreeViewModel.SkinType.DRY -> {
                        binding.cDry.chipBackgroundColor = resources.getColorStateList(R.color.rose)
                        binding.cDry.chipStrokeWidth = 0f
                    }
                    CreateAccThreeViewModel.SkinType.NORMAL -> {
                        binding.cNormal.chipBackgroundColor = resources.getColorStateList(R.color.rose)
                        binding.cNormal.chipStrokeWidth = 0f
                    }
                    CreateAccThreeViewModel.SkinType.SENSITIVE -> {
                        binding.cSensitive.chipBackgroundColor = resources.getColorStateList(R.color.rose)
                        binding.cSensitive.chipStrokeWidth = 0f
                    }
                    CreateAccThreeViewModel.SkinType.COMBINATION -> {
                        binding.cCombination.chipBackgroundColor = resources.getColorStateList(R.color.rose)
                        binding.cCombination.chipStrokeWidth = 0f
                    }
                    CreateAccThreeViewModel.SkinType.NOT_SURE -> {
                        binding.cNotSure.chipBackgroundColor = resources.getColorStateList(R.color.rose)
                        binding.cNotSure.chipStrokeWidth = 0f
                    }
                    else -> {}
                }
            }
        }
    }

    private fun initData() {
        viewModel.init()
    }






    fun onCon1Clicked(view: View) {
        viewModel.concern.postValue(CreateAccThreeViewModel.Concern.C_ONE)
    }

    fun onCon2Clicked(view: View) {
        viewModel.concern.postValue(CreateAccThreeViewModel.Concern.C_TWO)
    }

    fun onCon3Clicked(view: View) {
        viewModel.concern.postValue(CreateAccThreeViewModel.Concern.C_THREE)
    }

    fun onCon4Clicked(view: View) {
        viewModel.concern.postValue(CreateAccThreeViewModel.Concern.C_FOUR)
    }

    fun onCon5Clicked(view: View) {
        viewModel.concern.postValue(CreateAccThreeViewModel.Concern.C_FIVE)
    }

    fun onCon6Clicked(view: View) {
        viewModel.concern.postValue(CreateAccThreeViewModel.Concern.C_SIX)
    }

    fun onCon7Clicked(view: View) {
        viewModel.concern.postValue(CreateAccThreeViewModel.Concern.C_SEVEN)
    }

    fun onCon8Clicked(view: View) {
        viewModel.concern.postValue(CreateAccThreeViewModel.Concern.C_EIGHT)
    }

    fun onOilyClicked(view: View) {
        viewModel.skinType.postValue(CreateAccThreeViewModel.SkinType.OILY)
    }

    fun onDryClicked(view: View) {
        viewModel.skinType.postValue(CreateAccThreeViewModel.SkinType.DRY)
    }

    fun onNormalClicked(view: View) {
        viewModel.skinType.postValue(CreateAccThreeViewModel.SkinType.NORMAL)
    }

    fun onSensitiveClicked(view: View) {
        viewModel.skinType.postValue(CreateAccThreeViewModel.SkinType.SENSITIVE)
    }

    fun onCombinationClicked(view: View) {
        viewModel.skinType.postValue(CreateAccThreeViewModel.SkinType.COMBINATION)
    }

    fun onNotSureClicked(view: View) {
        viewModel.skinType.postValue(CreateAccThreeViewModel.SkinType.NOT_SURE)
    }


    fun onFinishClicked(view: View) {
        Prefs(requireContext()).loggedIn = true
        (activity as MainActivity).loadLoginAnimationFragment()
    }

    fun goBack(view: View) {
        onBackPressed()
    }

    private fun resetConcerns(){
        binding.cCon1.chipBackgroundColor = resources.getColorStateList(R.color.white)
        binding.cCon2.chipBackgroundColor = resources.getColorStateList(R.color.white)
        binding.cCon3.chipBackgroundColor = resources.getColorStateList(R.color.white)
        binding.cCon4.chipBackgroundColor = resources.getColorStateList(R.color.white)
        binding.cCon5.chipBackgroundColor = resources.getColorStateList(R.color.white)
        binding.cCon6.chipBackgroundColor = resources.getColorStateList(R.color.white)
        binding.cCon7.chipBackgroundColor = resources.getColorStateList(R.color.white)
        binding.cCon8.chipBackgroundColor = resources.getColorStateList(R.color.white)
        binding.cCon1.chipStrokeWidth = 1.dpToPx
        binding.cCon2.chipStrokeWidth = 1.dpToPx
        binding.cCon3.chipStrokeWidth = 1.dpToPx
        binding.cCon4.chipStrokeWidth = 1.dpToPx
        binding.cCon5.chipStrokeWidth = 1.dpToPx
        binding.cCon6.chipStrokeWidth = 1.dpToPx
        binding.cCon7.chipStrokeWidth = 1.dpToPx
        binding.cCon8.chipStrokeWidth = 1.dpToPx
    }

    private fun resetSkinType(){
        binding.cOily.chipBackgroundColor = resources.getColorStateList(R.color.white)
        binding.cDry.chipBackgroundColor = resources.getColorStateList(R.color.white)
        binding.cNormal.chipBackgroundColor = resources.getColorStateList(R.color.white)
        binding.cSensitive.chipBackgroundColor = resources.getColorStateList(R.color.white)
        binding.cCombination.chipBackgroundColor = resources.getColorStateList(R.color.white)
        binding.cNotSure.chipBackgroundColor = resources.getColorStateList(R.color.white)
        binding.cOily.chipStrokeWidth = 1.dpToPx
        binding.cDry.chipStrokeWidth = 1.dpToPx
        binding.cNormal.chipStrokeWidth = 1.dpToPx
        binding.cSensitive.chipStrokeWidth = 1.dpToPx
        binding.cCombination.chipStrokeWidth = 1.dpToPx
        binding.cNotSure.chipStrokeWidth = 1.dpToPx
    }

    private fun log(msg: String) {
        AppLog.log(TAG, msg)
    }

}