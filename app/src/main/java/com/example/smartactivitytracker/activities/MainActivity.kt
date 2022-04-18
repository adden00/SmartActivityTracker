package com.example.smartactivitytracker.activities

import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import com.example.smartactivitytracker.*
import com.example.smartactivitytracker.bluetooth.BtConnection
import com.example.smartactivitytracker.bluetooth.ReceiveThread
import com.example.smartactivitytracker.data.ListItem
import com.example.smartactivitytracker.databinding.ActivityMainBinding
import com.example.smartactivitytracker.db.MainViewModel
import com.example.smartactivitytracker.entities.Datas
import com.example.smartactivitytracker.fragments.FragmentManager
import com.example.smartactivitytracker.fragments.MainMonitorFragment
import com.example.smartactivitytracker.fragments.StatisticFragment
import com.example.smartactivitytracker.fragments.PredictFragment
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), ReceiveThread.Receiver {

    lateinit var binding: ActivityMainBinding                         // переменные (свойства) класса активити
    private lateinit var mainLauncher: ActivityResultLauncher<Intent>
    lateinit var btConnection: BtConnection
    private var listItem: ListItem? = null

    private lateinit var toastConnected: Toast
    private lateinit var toastFailedConnected: Toast
    var page = 1

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModel.MainViewModelFactory((applicationContext as MainApp).database)
    }

    override fun onCreate(savedInstanceState: Bundle?) {       // запускается при создании активити
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)  // инициализация байндинга
        setContentView(binding.root)

        if (getDate() != getCurrentTime()) {
//            newDay()
            saveDate(getCurrentTime())
        }
        Log.d("MyLog", "cur date: ${getDate()}")

        supportActionBar?.title = getString(R.string.app_name_short)

        binding.bottomMenu.selectedItemId = R.id.home_id
        supportFragmentManager.beginTransaction().replace(     // запуск фрагмента мониторинга
            R.id.place_holder,
            MainMonitorFragment.newInstance()
        ).commit()

        binding.bottomMenu.setOnItemSelectedListener {  // выбор иконки в нижнем меню

            when (it.itemId) {    // Действия при выборе меню

                R.id.stat_id -> {
                    FragmentManager.setFragment(StatisticFragment.newInstance(), this)
                }

                R.id.home_id -> {
//                    page = if (page == 1) {
//                        FragmentManager.setFragment(PredictFragment.newInstance(), this)
//                        2
//                    } else {
                        FragmentManager.setFragment(MainMonitorFragment.newInstance(), this)
//                        1
//                    }
                }
                R.id.set_id -> {}
                R.id.predict_id -> {
                    FragmentManager.setFragment(PredictFragment.newInstance(), this)
                }
            }
            true
        }

        onBtListResult()
        init()


    }


    private fun init() {
        val btManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val btAdapter = btManager.adapter
        toastConnected =
            Toast.makeText(this, "Подключено!", Toast.LENGTH_SHORT) // сообщение connected!
        toastFailedConnected =
            Toast.makeText(
                this,
                "Ошибка при подключении!",
                Toast.LENGTH_SHORT
            ) // сообщение connected!

        btConnection = BtConnection(btAdapter, this, toastConnected, toastFailedConnected)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { // функция для действий верхнего бара

        when (item.itemId) {

            R.id.id_list -> mainLauncher.launch(Intent(this, BtListActivity::class.java))

            R.id.id_connect -> // TODO сделать проверку на null
                if (listItem != null)
                    listItem.let { btConnection.connect(it?.mac!!) }
                else
                    Toast.makeText(this, "select the device firstly!", Toast.LENGTH_SHORT).show()
        }


        return true
    }


    private fun onBtListResult() {
        mainLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                listItem = it.data?.getSerializableExtra(BtListActivity.DEVICE_KEY) as ListItem
                supportActionBar?.title = "device: ${listItem?.name}"
            }
        }
    }

    override fun receiveData(data: Datas) {
        mainViewModel.insertData(data)
        runOnUiThread {
            if (data.dataType == "b") {
                binding.tvBatteryPercentage.text = data.value + "%"
            }
            (supportFragmentManager.findFragmentById(R.id.place_holder) as? MainMonitorFragment)?.newDataItem(
                data
            )

        }
    }


    fun startMeasure() {
        btConnection.startMeasure()
        (supportFragmentManager.findFragmentById(R.id.place_holder) as? MainMonitorFragment)?.unlockStartBtn()
    }

    fun stopMeasure() {
        btConnection.stopMeasure();
    }

    private fun getCurrentTime(): String {
        val formatter = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        return formatter.format(Calendar.getInstance().time)
    }

    private fun saveDate(date: String) {
        val pref: SharedPreferences = getSharedPreferences("sharedPref", MODE_PRIVATE)
        val prefEditor: SharedPreferences.Editor = pref.edit()
        prefEditor.putString("CUR_DATE", date)
        prefEditor.commit()
    }

    private fun getDate(): String {
        val pref: SharedPreferences = getSharedPreferences("sharedPref", MODE_PRIVATE)
        return pref.getString("CUR_DATE", null) ?: ""
    }


    private fun newDay() {
        TODO("Not yet implemented")
    }
}