package com.easylife.easylifes.utils

import com.easylife.easylifes.model.allworkouts.AllWorkoutsDataListModel

class UtilityClass private constructor() {
    var list: ArrayList<AllWorkoutsDataListModel> = ArrayList()

    companion object {
        var instance: UtilityClass? = null
            get() {
                if (field == null) {
                    field = UtilityClass()
                }
                return field
            }
            private set
    }
}