package com.example.arts.models

import com.cloudinary.Url

data class Art(
    var id: String="",
    var name: String="",
    var description: String="",
    var imageUrl: String="",
    var userId : String = ""
)