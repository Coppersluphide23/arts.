package com.example.arts.models

import androidx.annotation.DrawableRes
import com.example.arts.R

data class CategoryItem(
    val name: String,
    @param:DrawableRes val imageRes: Int
)

val categoryList = listOf(
    CategoryItem("All", R.drawable.schoolofathens),
    CategoryItem("Sculptures", R.drawable.david),
    CategoryItem("European Art", R.drawable.european),
    CategoryItem("Modern Art", R.drawable.modern),
    CategoryItem("African Art", R.drawable.africa),
    CategoryItem("Japanese Art", R.drawable.waveofkanagwa),

)

val availableCategoryNames: List<String>
    get() = categoryList.map { it.name }.filter { !it.equals("All", ignoreCase = true) }
