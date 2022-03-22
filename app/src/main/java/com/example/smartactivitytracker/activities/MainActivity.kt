package com.example.smartactivitytracker.activities

import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.smartactivitytracker.*
import com.example.smartactivitytracker.bluetooth.BtConnection
import com.example.smartactivitytracker.bluetooth.ReceiveThread
import com.example.smartactivitytracker.data.ListItem
import com.example.smartactivitytracker.databinding.ActivityMainBinding
import com.example.smartactivitytracker.fragments.FragmentManager
import com.example.smartactivitytracker.fragments.MainMonitorFragment
import com.example.smartactivitytracker.fragments.StatisticFragment
import com.example.smartactivitytracker.fragments.StepsFragment

class MainActivity : AppCompatActivity(), ReceiveThread.Receiver {

    lateinit var binding: ActivityMainBinding
    private lateinit var mainLauncher: ActivityResultLauncher<Intent>
    lateinit var btConnection: BtConnection
    private var listItem: ListItem?= null

    lateinit var toastConnected: Toast
    var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)  // инициализация байндинга
        setContentView(binding.root)


        //supportActionBar?.setDisplayHomeAsUpEnabled(true) // стрелочка слева наверху
        supportActionBar?.title = getString(R.string.app_name_short)
//        supportActionBar?.setDisplayUseLogoEnabled(true)


        binding.bottomMenu.selectedItemId = R.id.home_id
        supportFragmentManager.beginTransaction().replace(
            R.id.place_holder,
            MainMonitorFragment.newInstance()
        ).commit()

        binding.bottomMenu.setOnItemSelectedListener {  // выбор иконки в нижнем меню

            when(it.itemId){    // Действия при выборе меню

                R.id.stat_id -> {FragmentManager.setFragment(StatisticFragment.newInstance(), this) }

                R.id.home_id -> {
                    page = if (page == 1) {FragmentManager.setFragment(StepsFragment.newInstance(), this)
                        2 } else {
                        FragmentManager.setFragment(MainMonitorFragment.newInstance(), this)
                        1
                    }
                }
                R.id.set_id -> {}
            }
            true
        }

        onBtListResult()
        init()


    }

    private fun init() {
        val btManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val btAdapter = btManager.adapter
        toastConnected = Toast.makeText(this, "connected!", Toast.LENGTH_SHORT) // сообщение connected!
        btConnection = BtConnection(btAdapter, this, toastConnected)
        

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { // функция для действий верхнего бара

        when(item.itemId){

            R.id.id_list -> mainLauncher.launch(Intent(this, BtListActivity::class.java ))

            R.id.id_connect -> // TODO сделать проверку на null
                listItem.let { btConnection.connect(it?.mac!!)}
        }


        return true
    }



    private fun onBtListResult(){
        mainLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK) {
                listItem = it.data?.getSerializableExtra(BtListActivity.DEVICE_KEY) as ListItem
                supportActionBar?.title = "device: ${listItem?.name}"
            }
        }
    }

    override fun receiveData(data: String) {
        runOnUiThread {
            (supportFragmentManager.findFragmentById(R.id.place_holder) as? MainMonitorFragment)?.updateValHeart(data)

        }
    }


    fun startMeasure() {
        btConnection.startMeasure()
        (supportFragmentManager.findFragmentById(R.id.place_holder) as? MainMonitorFragment)?.unlockStartBtn()
    }

    fun stopMeasure() {
        btConnection.stopMeasure();
    }


}