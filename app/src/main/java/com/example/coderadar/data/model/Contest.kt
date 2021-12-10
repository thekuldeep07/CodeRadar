package com.example.coderadar.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "contests_table")
data class Contest(
    @PrimaryKey
    @SerializedName("id")
    val id:Int,
    @SerializedName("end")
    val end: String?,
    @SerializedName("event")
    val event: String?,
    @SerializedName("href")
    val href: String?,
    @SerializedName("resource")
    val resource: String?,
    @SerializedName("start")
    val start: String?
)