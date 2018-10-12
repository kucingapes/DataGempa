/*
 * ItemClick.kt on DataGempa
 * Developed by Muhammad Utsman on 10/12/18 11:45 PM
 * Last modified 10/12/18 11:45 PM
 * Copyright (c) 2018 kucingapes
 */

package com.kucingapes.datagempa

import com.kucingapes.datagempa.model.DataGempa

interface ItemClick {
    fun OnItemClickRecycler(gempa: DataGempa.Gempa)
}