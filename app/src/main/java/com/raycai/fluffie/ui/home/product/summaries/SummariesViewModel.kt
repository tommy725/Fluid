package com.raycai.fluffie.ui.home.product.summaries

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raycai.fluffie.ui.home.product.claims.ClaimsViewModel
import com.raycai.fluffie.util.AppConst

class SummariesViewModel : ViewModel() {

    private val TAG = ClaimsViewModel::class.java.simpleName

    val minPros = 5
    var isShowingMore = false
    val pros = AppConst.prosList
    val cons = AppConst.consList


}