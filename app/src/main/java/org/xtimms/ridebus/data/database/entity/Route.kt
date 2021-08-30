package org.xtimms.ridebus.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//
// Created by Xtimms on 28.08.2021.
//
@Entity(tableName = "Routes")
data class Route(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "_id")
    val id: Int,
    @ColumnInfo(name = "number") val number: String,
    @ColumnInfo(name = "description") val description: String
)