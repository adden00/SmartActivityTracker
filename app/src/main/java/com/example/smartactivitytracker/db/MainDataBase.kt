package com.example.smartactivitytracker.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.smartactivitytracker.entities.Datas
import com.example.smartactivitytracker.entities.Days

@Database (entities = [Datas::class, Days::class], version = 1)

abstract class MainDataBase: RoomDatabase() {

    abstract fun getDao(): Dao

    companion object {
        @Volatile
        private var INSTANCE: MainDataBase? = null
        fun getDataBase(context: Context): MainDataBase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context.applicationContext, MainDataBase::class.java, "SATdb").build()
                // TODO: если не будет работать, вернуть с val
            }
        }
    }

}