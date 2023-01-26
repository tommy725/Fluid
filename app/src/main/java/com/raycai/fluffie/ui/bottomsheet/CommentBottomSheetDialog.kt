package com.raycai.fluffie.ui.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.raycai.fluffie.R
import com.raycai.fluffie.databinding.BottomSheetCommentBinding


class CommentBottomSheetDialog : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetCommentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetCommentBinding.inflate(layoutInflater)
        binding.dialog = this
        binding.lifecycleOwner = this
        initComponents()
        return binding.root
    }

    private fun initComponents() {

    }

    fun onLike1Clicked(view: View){

    }

    fun onCloseClicked(view: View){
        dismiss()
    }


    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialogTheme
    }
}