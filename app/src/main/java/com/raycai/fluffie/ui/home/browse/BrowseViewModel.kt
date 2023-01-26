package com.raycai.fluffie.ui.home.browse

import androidx.lifecycle.MutableLiveData
import com.raycai.fluffie.base.BaseViewModal
import com.raycai.fluffie.data.model.UserFilter
import com.raycai.fluffie.http.response.CategoryListResponse
import com.raycai.fluffie.http.response.ProductListResponse
import com.raycai.fluffie.util.AppConst

class BrowseViewModel() : BaseViewModal() {

    private val TAG = BrowseViewModel::class.java.simpleName

    val maxTopicSelection = AppConst.MAX_USER_FILTER_TOPIC_SELECTIONS

    val tabsChanged = MutableLiveData<Boolean>()
    val productsChanged = MutableLiveData<Boolean>()
    val userFilterSelectionChanged = MutableLiveData<Boolean>()
    val selectedProductCategory = MutableLiveData<CategoryListResponse.Category>()

    var products: ArrayList<ProductListResponse.ProductDetail> = ArrayList()
    val tabs = ArrayList<String>()

    var userFilter: UserFilter? = null

    fun initData() {
    }

    fun onUserFilterAceProneClicked(filter: String) {
        userFilter?.acneProneSelected = !userFilter?.acneProneSelected!!
        userFilterSelectionChanged.postValue(true)
    }

    fun onUserFilterFragranceClicked(filter: String) {
        if (isFragranceExists(filter)) {
            userFilter?.selectedFragrances?.remove(filter)
        } else {
            userFilter?.selectedFragrances?.add(filter)
        }

        userFilterSelectionChanged.postValue(true)
    }

    fun onUserFilterProductConsistencyClicked(filter: String) {
        if (isConsistencyExists(filter)) {
            userFilter?.selectedConsistencies?.remove(filter)
        } else {
            userFilter?.selectedConsistencies?.add(filter)
        }

        userFilterSelectionChanged.postValue(true)
    }

    fun onUserFilterBenefitClicked(filter: String) {
        if (isBenefitExists(filter)) {
            userFilter?.selectedBenefits?.remove(filter)
        } else {
            userFilter?.selectedBenefits?.add(filter)
        }

        userFilterSelectionChanged.postValue(true)
    }

    fun onUserFilterAgeFilterClicked(filter: String) {
        if (userFilter?.age == null) {
            userFilter?.age = filter
        } else {
            userFilter?.age = null
        }
        userFilterSelectionChanged.postValue(true)
    }


    fun isFragranceExists(b: String): Boolean {
        if (userFilter != null) {
            return isExistsInArray(userFilter!!.selectedFragrances, b)
        }
        return false
    }

    fun isBenefitExists(b: String): Boolean {
        if (userFilter != null) {
            return isExistsInArray(userFilter!!.selectedBenefits, b)
        }
        return false
    }

    fun isConsistencyExists(b: String): Boolean {
        if (userFilter != null) {
            return isExistsInArray(userFilter!!.selectedConsistencies, b)
        }
        return false
    }

    fun selectedTopicsCount(): Int {
        var total = 0

        if (!userFilter?.selectedBenefits?.isEmpty()!!) {
            total += userFilter?.selectedBenefits?.size!!
        }

        if (!userFilter?.selectedConsistencies?.isEmpty()!!) {
            total += userFilter?.selectedConsistencies?.size!!
        }

        if (!userFilter?.selectedFragrances?.isEmpty()!!) {
            total += userFilter?.selectedFragrances?.size!!
        }

        return total
    }
}