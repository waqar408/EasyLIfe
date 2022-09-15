package com.easylife.easylifes.utils

import com.easylife.easylifes.model.allworkouts.AllWorkoutsDataListModel
import com.easylife.easylifes.model.search.SearchDataModel

class UtilityClass private constructor() {
    var list: ArrayList<AllWorkoutsDataListModel> = ArrayList()
    var searchMealList: ArrayList<SearchDataModel> = ArrayList()

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