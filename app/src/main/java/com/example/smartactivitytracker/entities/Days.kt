package com.example.smartactivitytracker.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


    @Entity(tableName = "days_data")
    data class Days (
        @PrimaryKey(autoGenerate = true)
        val id: Int?,

        @ColumnInfo(name = "avg_heart_rate")
        val avgHeart: String,

        @ColumnInfo(name = "avg_steps")
        val avgSteps: String,

        @ColumnInfo(name = "avg_temperature")
        val avgTemp: String,

        @ColumnInfo(name = "date")
        val date: String

    ): Serializable
