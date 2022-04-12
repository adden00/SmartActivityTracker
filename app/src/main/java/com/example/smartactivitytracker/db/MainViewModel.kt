package com.example.smartactivitytracker.db

import androidx.lifecycle.*
import com.example.smartactivitytracker.entities.Datas
import com.example.smartactivitytracker.entities.Days
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class MainViewModel(dataBase: MainDataBase) : ViewModel() {

    private val dao = dataBase.getDao()
    val dayData: LiveData<List<Datas>> = dao.getAllData().asLiveData()

    val daysAvgData: LiveData<List<Days>> = dao.getAllDays().asLiveData()


    fun insertDay(dataItem: Days) = viewModelScope.launch {
        dao.insertDayItem(dataItem)
    }

    fun insertData(dataItem: Datas) = viewModelScope.launch {
        dao.insertDataItem(dataItem)
    }


    class MainViewModelFactory(val database: MainDataBase): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress ("UNCHECKED_CAST") // чтобы не подсвечивал warning
                return MainViewModel(database) as T
            }
            throw IllegalArgumentException("unknown viewModel class!")
        }

    }


}