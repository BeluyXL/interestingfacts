package com.example.interestingfactsaboutnumbers

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Fact(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "number") val number: Int?,
    @ColumnInfo(name = "fact") val fact: String?
)