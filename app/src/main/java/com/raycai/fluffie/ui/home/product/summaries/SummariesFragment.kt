package com.raycai.fluffie.ui.home.product.summaries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.dam.bestexpensetracker.util.AppLog
import com.raycai.fluffie.HomeActivity
import com.raycai.fluffie.R
import com.raycai.fluffie.base.BaseFragment
import com.raycai.fluffie.data.model.Con2
import com.raycai.fluffie.data.model.Pros
import com.raycai.fluffie.databinding.FragmentSummariesBinding
import com.raycai.fluffie.ui.home.product.claims.ClaimsFragment

class SummariesFragment : BaseFragment() {

    private val TAG = ClaimsFragment::class.java.simpleName
    private lateinit var viewModel: SummariesViewModel
    private lateinit var binding: FragmentSummariesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[SummariesViewModel::class.java]
        binding = FragmentSummariesBinding.inflate(inflater)
        binding.fragment = this
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        initData()
        return binding.root
    }

    private fun initData() {
        loadPros()
        loadCons()
    }

    fun onShowMoreClicked(view: View) {
        viewModel.isShowingMore = !viewModel.isShowingMore
        loadPros()
        loadCons()
        if (viewModel.isShowingMore) {
            binding.ivShowArrow.setImageResource(R.drawable.ic_up_arrow)
            binding.tvShowMore.text = "Show Less"
        } else {
            binding.ivShowArrow.setImageResource(R.drawable.ic_down)
            binding.tvShowMore.text = "Show More"
        }
    }

    private fun loadCons() {
        var idx = 0
        binding.layoutCons.removeAllViews()
        viewModel.cons.forEach {
            if (viewModel.isShowingMore) {
                addCons(it)
            } else {
                if (idx < 5) {
                    addCons(it)
                }
            }
            idx++
        }
    }

    private fun loadPros() {
        var idx = 0
        binding.layoutPros.removeAllViews()
        viewModel.pros.forEach {
            if (viewModel.isShowingMore) {
                addPros(it)
            } else {
                if (idx < 5) {
                    addPros(it)
                }
            }
            idx++
        }
    }

    private fun addPros(p: Pros) {
        val view = layoutInflater.inflate(R.layout.layout_pros_or_con, null)
        val layoutBg: LinearLayout = view.findViewById(R.id.layoutBg)
        val tvText: TextView = view.findViewById(R.id.tvText)
        val tvVal: TextView = view.findViewById(R.id.tvVal)
        tvText.text = p.txt
        tvVal.text = p.value

        layoutBg.setOnClickListener {
            (activity as HomeActivity).selectedPros = p
            (activity as HomeActivity).selectedCon = null
            (activity as HomeActivity).loadProsOrConFragment(true)
        }

        binding.layoutPros.addView(view)
    }

    private fun addCons(c: Con2) {
        val view = layoutInflater.inflate(R.layout.layout_pros_or_con, null)
        val layoutBg: LinearLayout = view.findViewById(R.id.layoutBg)
        val tvText: TextView = view.findViewById(R.id.tvText)
        val tvVal: TextView = view.findViewById(R.id.tvVal)
        tvText.text = c.txt
        tvVal.text = c.value

        layoutBg.setOnClickListener {
            (activity as HomeActivity).selectedCon = c
            (activity as HomeActivity).selectedPros = null
            (activity as HomeActivity).loadProsOrConFragment(true)
        }

        binding.layoutCons.addView(view)
    }

    private fun log(msg: String) {
        AppLog.log(TAG, msg)
    }

}