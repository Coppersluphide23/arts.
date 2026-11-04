package com.example.arts.models

import androidx.annotation.DrawableRes
import com.example.arts.R

data class CategoryItem(
    val name: String,
    @param:DrawableRes val imageRes: Int
)

val categoryList = listOf(
    CategoryItem("All", R.drawable.mountain_adventure_logo),
    CategoryItem("Sculptures", R.drawable.mountain_adventure_logo),
    CategoryItem("European Art", R.drawable.mountain_adventure_logo),
    CategoryItem("Modern Art", R.drawable.mountain_adventure_logo),
    CategoryItem("African Art", R.drawable.mountain_adventure_logo),
    CategoryItem("Japanese Art", R.drawable.mountain_adventure_logo),
    CategoryItem("Abstract", R.drawable.mountain_adventure_logo)
)

val availableCategoryNames: List<String>
    get() = categoryList.map { it.name }.filter { !it.equals("All", ignoreCase = true) }
