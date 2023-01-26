package com.raycai.fluffie.ui.onboarding.terms

import android.content.pm.PackageManager
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
import com.raycai.fluffie.MainActivity
import com.raycai.fluffie.R
import com.raycai.fluffie.base.BaseFragment
import com.raycai.fluffie.databinding.FragmentPreloginBinding
import com.raycai.fluffie.databinding.FragmentSplashBinding
import com.raycai.fluffie.databinding.FragmentTermsBinding
import com.raycai.fluffie.ui.onboarding.createacc.CreateAccFragment

class TermsFragment : BaseFragment() {

    private val TAG = TermsFragment::class.java.simpleName
    private lateinit var viewModel: TermsViewModel
    private lateinit var binding: FragmentTermsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[TermsViewModel::class.java]
        binding = FragmentTermsBinding.inflate(inflater)
        binding.fragment = this
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        initData()
        return binding.root
    }

    private fun initData() {
        val wordtoSpan: Spannable =
            SpannableString("By using this app you agree to the following conditions:")

        val conditionsClicked: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                Toast.makeText(requireContext(), "Conditions", Toast.LENGTH_SHORT).show()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                setTermsHighlight(ds)
            }
        }

        wordtoSpan.setSpan(conditionsClicked, 35, 56, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.tvTermsTitle.text = wordtoSpan
        binding.tvTermsTitle.movementMethod = LinkMovementMethod.getInstance()
        binding.tvTermsTitle.highlightColor = ContextCompat.getColor(requireContext(), R.color.card_bg)
    }

    private fun setTermsHighlight(ds: TextPaint) {
        ds.textSize = 16.dpToPx
        ds.isUnderlineText = true
        ds.color = ContextCompat.getColor(requireContext(), R.color.lite_blue)
        ds.typeface = ResourcesCompat.getFont(requireContext(), R.font.roboto_regular)
    }

    fun onAgreeClicked(view: View){

    }

    fun onDisagreeClicked(view: View) {

    }

    private fun log(msg: String) {
        AppLog.log(TAG, msg)
    }

}