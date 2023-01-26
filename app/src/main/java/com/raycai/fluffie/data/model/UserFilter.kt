package com.raycai.fluffie.data.model

import com.raycai.fluffie.util.AppConst

class UserFilter {

    var skinType: String? = null
    var acneProneSelected = false
    var age: String? = null
    var selectedBenefits = ArrayList<String>()
    var selectedConsistencies = ArrayList<String>()
    var selectedFragrances = ArrayList<String>()

    fun generateDataAr(): ArrayList<String> {
        var data = ArrayList<String>()
        4
        if (acneProneSelected) {
            data.add(AppConst.ACNE_PRONE)
        }

        if (age != null) {
            data.add(age!!)
        }

        data.addAll(selectedBenefits)
        data.addAll(selectedConsistencies)
        data.addAll(selectedFragrances)
        return data
    }

}