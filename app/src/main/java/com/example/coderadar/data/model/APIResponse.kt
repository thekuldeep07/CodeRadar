package com.example.coderadar.data.model


import com.google.gson.annotations.SerializedName

data class APIResponse(
    @SerializedName("objects")
    val contests: List<Contest>
)