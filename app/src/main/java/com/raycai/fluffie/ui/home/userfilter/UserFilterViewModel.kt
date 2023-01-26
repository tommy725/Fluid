package com.raycai.fluffie.ui.home.userfilter

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raycai.fluffie.data.model.UserFilter
import com.raycai.fluffie.util.AppConst

class UserFilterViewModel() : ViewModel() {

    private val TAG = UserFilterViewModel::class.java.simpleName

    private val maxTopicSelection = AppConst.MAX_USER_FILTER_TOPIC_SELECTIONS



    val selectedSkinType = MutableLiveData("\uD83C\uDF35 Dry")
    val acneProneSelected = MutableLiveData(false)
    val selectedAge = MutableLiveData<String?>()
    val selectedTopicsCount = MutableLiveData(0)
    val btnActionEnable = MutableLiveData(false)
    val selectedBenefits = ArrayList<String>()
    val selectedConsistencies = ArrayList<String>()
    val selectedFragrances = ArrayList<String>()

    //notifiers
    val onBenefitDataChanged = MutableLiveData(false)
    val onPCDataChanged = MutableLiveData(false)
    val onFragranceChanged = MutableLiveData(false)

    fun setUserFilterData(uf: UserFilter?) {
        if (uf != null) {
            if (uf.skinType != null) {
                selectedSkinType.postValue(uf.skinType)
            }

            if (uf.acneProneSelected) {
                acneProneSelected.postValue(true)
            }

            if (uf.age != null) {
                selectedAge.postValue(uf.age)
            }

            if (uf.selectedBenefits.isNotEmpty()) {
                selectedBenefits.addAll(uf.selectedBenefits)
            }

            if (uf.selectedConsistencies.isNotEmpty()) {
                selectedConsistencies.addAll(uf.selectedConsistencies)
            }

            if (uf.selectedFragrances.isNotEmpty()) {
                selectedFragrances.addAll(uf.selectedFragrances)
            }
        }
    }

    fun fragranceSelected(f: String) {
        if (selectedTopicsCount() < maxTopicSelection) {
            if (fragranceExists(f)) {
                selectedFragrances.remove(f)
            } else {
                selectedFragrances.add(f)
            }
            onFragranceChanged.postValue(true)
        } else {
            if (fragranceExists(f)) {
                selectedFragrances.remove(f)
            }
            onFragranceChanged.postValue(true)
        }
        selectedTopicsCount.postValue(selectedTopicsCount())
    }

    fun pcSelected(pc: String) {
        if (selectedTopicsCount() < maxTopicSelection) {
            if (pcExists(pc)) {
                selectedConsistencies.remove(pc)
            } else {
                selectedConsistencies.add(pc)
            }
            onPCDataChanged.postValue(true)
        } else {
            if (pcExists(pc)) {
                selectedConsistencies.remove(pc)
            }
            onPCDataChanged.postValue(true)
        }
        selectedTopicsCount.postValue(selectedTopicsCount())
    }

    fun benefitSelected(benefit: String) {
        if (selectedTopicsCount() < maxTopicSelection) {
            if (benefitExists(benefit)) {
                selectedBenefits.remove(benefit)
            } else {
                selectedBenefits.add(benefit)
            }
            onBenefitDataChanged.postValue(true)
        } else {
            if (benefitExists(benefit)) {
                selectedBenefits.remove(benefit)
            }
            onBenefitDataChanged.postValue(true)
        }
        selectedTopicsCount.postValue(selectedTopicsCount())
    }

    fun initData() {
        selectedSkinType.observeForever {
            btnActionEnable.postValue(!TextUtils.isEmpty(it))
        }
    }


    private fun selectedTopicsCount(): Int {
        return selectedBenefits.size + selectedConsistencies.size + selectedFragrances.size
    }

    private fun benefitExists(b: String): Boolean {
        selectedBenefits.forEach {
            if (it == b) {
                return true
            }
        }

        return false
    }

    private fun pcExists(b: String): Boolean {
        selectedConsistencies.forEach {
            if (it == b) {
                return true
            }
        }

        return false
    }

    private fun fragranceExists(b: String): Boolean {
        selectedFragrances.forEach {
            if (it == b) {
                return true
            }
        }

        return false
    }

    fun createUserFilterObj(): UserFilter {
        val uf = UserFilter()
        uf.skinType = selectedSkinType.value
        uf.acneProneSelected = acneProneSelected.value!!
        uf.age = selectedAge.value
        uf.selectedBenefits = selectedBenefits
        uf.selectedConsistencies = selectedConsistencies
        uf.selectedFragrances = selectedFragrances
        return uf
    }
}