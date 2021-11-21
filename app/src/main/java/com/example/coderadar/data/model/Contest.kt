package com.example.coderadar.data.model


import com.google.gson.annotations.SerializedName

data class Contest(
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("end")
    val end: String,
    @SerializedName("event")
    val event: String,
    @SerializedName("host")
    val host: String,
    @SerializedName("href")
    val href: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("problems")
    val problems: Any,
    @SerializedName("resource")
    val resource: String,
    @SerializedName("resource_id")
    val resourceId: Int,
    @SerializedName("start")
    val start: String
)