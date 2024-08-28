package com.lokalassignment.applokal.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "myjobs")
data class JobEntity (

    @PrimaryKey
    val id: Int,

    val value: String
)