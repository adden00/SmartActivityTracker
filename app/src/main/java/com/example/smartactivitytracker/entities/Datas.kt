package com.example.smartactivitytracker.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity (tableName = "main_data")
data class Datas (
    @PrimaryKey (autoGenerate = true)
    val id: Int?,

    @ColumnInfo (name = "data_type")
    val dataType: String,

    @ColumnInfo (name = "value")
    val value: String,

    @ColumnInfo (name = "date_time")
    val dateTime: String

        ): Serializable
