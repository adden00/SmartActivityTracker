package com.example.smartactivitytracker.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.smartactivitytracker.entities.Datas
import com.example.smartactivitytracker.entities.Days
import kotlinx.coroutines.flow.Flow


@Dao
interface Dao {

    @Query ("SELECT * FROM MAIN_DATA")
    fun getAllData(): Flow<List<Datas>>

    @Query ("SELECT * FROM days_data")
    fun getAllDays(): Flow<List<Days>>




    @Insert
    suspend fun insertDataItem(dataItem: Datas)

    @Insert
    suspend fun insertDayItem(dataItem: Days)

//    @Query ("DELETE FROM MAIN_DATA")
//    suspend fun deleteItem()
//
//    @Query ("SELECT COUNT(*) FROM MAIN_DATA WHERE data_type='s'")
//    suspend fun countSteps()

//    @Query ("SELECT AVG() FROM ")

}