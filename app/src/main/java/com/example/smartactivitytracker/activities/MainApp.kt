package com.example.smartactivitytracker.activities

import android.app.Application
import com.example.smartactivitytracker.db.MainDataBase

class MainApp: Application() {
    val database by lazy { MainDataBase.getDataBase(this) }
}

