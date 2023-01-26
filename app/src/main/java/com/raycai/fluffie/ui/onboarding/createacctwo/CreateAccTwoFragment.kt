package com.raycai.fluffie.ui.onboarding.createacctwo

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
import com.raycai.fluffie.databinding.FragmentCreateAccTwoBinding
import java.text.SimpleDateFormat
import java.util.*


class CreateAccTwoFragment : BaseFragment() {

    private val TAG = CreateAccTwoFragment::class.java.simpleName
    private lateinit var viewModel: CreateAccTwoViewModel
    private lateinit var binding: FragmentCreateAccTwoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[CreateAccTwoViewModel::class.java]
        binding = FragmentCreateAccTwoBinding.inflate(inflater)
        binding.fragment = this
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        initData()
        setObservers()
        return binding.root
    }

    private fun setObservers() {
        viewModel.enableNext.observeForever {
            binding.btnNext.isEnabled = it
        }

        viewModel.dob.observeForever {
            if (viewModel.dob != null){
                val dobDisplay = SimpleDateFormat(Const.DATE_FORMAT_DISPLAY).format(it)
                binding.tvDob.text = dobDisplay
            }
        }

        viewModel.gender.observeForever {
            if (it != null){
                resetGenderSection()
                when (it) {
                    CreateAccTwoViewModel.Gender.FEMALE -> {
                        binding.ivFemale.setBackgroundResource(R.drawable.gender_circle_pressed)
                        binding.tvFemale.setTextColor(ContextCompat.getColor(requireContext(), R.color.rose))
                    }
                    CreateAccTwoViewModel.Gender.MALE -> {
                        binding.ivMale.setBackgroundResource(R.drawable.gender_circle_pressed)
                        binding.tvMale.setTextColor(ContextCompat.getColor(requireContext(), R.color.rose))
                    }
                    CreateAccTwoViewModel.Gender.NON_BINARY -> {
                        binding.ivNonBinary.setBackgroundResource(R.drawable.gender_circle_pressed)
                        binding.tvNonBinary.setTextColor(ContextCompat.getColor(requireContext(), R.color.rose))
                    }
                    else -> {}
                }
            }
        }

        viewModel.skinTone.observeForever {
            if (it != null){
                resetSkinToneSelection()
                when (it) {
                    CreateAccTwoViewModel.SkinTone.TONE_ONE -> {
                        binding.cvTone1.strokeWidth = 3.dpToPx.toInt()
                    }
                    CreateAccTwoViewModel.SkinTone.TONE_TWO -> {
                        binding.cvTone2.strokeWidth = 3.dpToPx.toInt()
                    }
                    CreateAccTwoViewModel.SkinTone.TONE_THREE -> {
                        binding.cvTone3.strokeWidth = 3.dpToPx.toInt()
                    }
                    CreateAccTwoViewModel.SkinTone.TONE_FOUR -> {
                        binding.cvTone4.strokeWidth = 3.dpToPx.toInt()
                    }
                    CreateAccTwoViewModel.SkinTone.TONE_FIVE -> {
                        binding.cvTone5.strokeWidth = 3.dpToPx.toInt()
                    }
                    CreateAccTwoViewModel.SkinTone.TONE_SIX -> {
                        binding.cvTone6.strokeWidth = 3.dpToPx.toInt()
                    }
                    CreateAccTwoViewModel.SkinTone.TONE_SEVEN -> {
                        binding.cvTone7.strokeWidth = 3.dpToPx.toInt()
                    }
                    CreateAccTwoViewModel.SkinTone.TONE_EIGHT -> {
                        binding.cvTone8.strokeWidth = 3.dpToPx.toInt()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun initData() {
        viewModel.init()
    }


    fun onSelectDobClicked(view: View) {
        val c = Calendar.getInstance()
        if (viewModel.dob.value != null){
            c.time = viewModel.dob.value
        }
        val mYear = c[Calendar.YEAR]
        val mMonth = c[Calendar.MONTH]
        val mDay = c[Calendar.DAY_OF_MONTH]


        val datePickerDialog = DatePickerDialog(requireContext(),
            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                c.set(Calendar.YEAR, year)
                c.set(Calendar.MONTH, monthOfYear)
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                viewModel.dob.postValue(c.time)
            },
            mYear,
            mMonth,
            mDay
        )

        datePickerDialog.datePicker.maxDate = System.currentTimeMillis();
        datePickerDialog.show()
    }

    fun onFemaleClicked(view: View) {
        viewModel.gender.postValue(CreateAccTwoViewModel.Gender.FEMALE)
    }

    fun onMaleClicked(view: View) {
        viewModel.gender.postValue(CreateAccTwoViewModel.Gender.MALE)
    }

    fun onNonBinaryClicked(view: View) {
        viewModel.gender.postValue(CreateAccTwoViewModel.Gender.NON_BINARY)
    }

    fun onSkinToneOneClicked(view: View) {
        viewModel.skinTone.postValue(CreateAccTwoViewModel.SkinTone.TONE_ONE)
    }

    fun onSkinToneTwoClicked(view: View) {
        viewModel.skinTone.postValue(CreateAccTwoViewModel.SkinTone.TONE_TWO)
    }

    fun onSkinToneThreeClicked(view: View) {
        viewModel.skinTone.postValue(CreateAccTwoViewModel.SkinTone.TONE_THREE)
    }

    fun onSkinToneFourClicked(view: View) {
        viewModel.skinTone.postValue(CreateAccTwoViewModel.SkinTone.TONE_FOUR)
    }

    fun onSkinToneFiveClicked(view: View) {
        viewModel.skinTone.postValue(CreateAccTwoViewModel.SkinTone.TONE_FIVE)
    }

    fun onSkinToneSixClicked(view: View) {
        viewModel.skinTone.postValue(CreateAccTwoViewModel.SkinTone.TONE_SIX)
    }

    fun onSkinToneSevenClicked(view: View) {
        viewModel.skinTone.postValue(CreateAccTwoViewModel.SkinTone.TONE_SEVEN)
    }

    fun onSkinToneEightClicked(view: View) {
        viewModel.skinTone.postValue(CreateAccTwoViewModel.SkinTone.TONE_EIGHT)
    }

    fun onNextClicked(view: View) {
        (activity as MainActivity).loadCreateAccountThreeFragment(true)
    }

    fun goBack(view: View) {
        onBackPressed()
    }

    private fun resetSkinToneSelection(){
        binding.cvTone1.strokeWidth = 0
        binding.cvTone2.strokeWidth = 0
        binding.cvTone3.strokeWidth = 0
        binding.cvTone4.strokeWidth = 0
        binding.cvTone5.strokeWidth = 0
        binding.cvTone6.strokeWidth = 0
        binding.cvTone7.strokeWidth = 0
        binding.cvTone8.strokeWidth = 0
    }

    private fun resetGenderSection(){
        binding.ivFemale.setBackgroundResource(R.drawable.gender_circle_normal)
        binding.ivMale.setBackgroundResource(R.drawable.gender_circle_normal)
        binding.ivNonBinary.setBackgroundResource(R.drawable.gender_circle_normal)
        binding.tvFemale.setTextColor(ContextCompat.getColor(requireContext(), R.color.lite_gray))
        binding.tvMale.setTextColor(ContextCompat.getColor(requireContext(), R.color.lite_gray))
        binding.tvNonBinary.setTextColor(ContextCompat.getColor(requireContext(), R.color.lite_gray))
    }

    private fun log(msg: String) {
        AppLog.log(TAG, msg)
    }

}