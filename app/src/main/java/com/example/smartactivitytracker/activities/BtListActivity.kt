package com.example.smartactivitytracker.activities

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartactivitytracker.data.ListItem
import com.example.smartactivitytracker.bluetooth.RcAdapter
import com.example.smartactivitytracker.databinding.ActivityBtListBinding


class BtListActivity : AppCompatActivity(), RcAdapter.Listener {

    private var btAdapter: BluetoothAdapter? = null
    private lateinit var binding: ActivityBtListBinding
    private lateinit var adapter: RcAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBtListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        btInit()
    }

    private fun btInit() {
        val btManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        btAdapter = btManager.adapter
        adapter = RcAdapter(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        getPairedDeviced()

    }

    private fun getPairedDeviced() {
        val pairedDevices: Set<BluetoothDevice>? = btAdapter?.bondedDevices
        val tempList = ArrayList<ListItem>()
        pairedDevices?.forEach{
            tempList.add(ListItem(it.name, it.address))
        }
        adapter.submitList(tempList)

    }
    companion object{
        const val DEVICE_KEY = "device_key"
    }

    override fun onClick(item: ListItem) {
        val i = Intent().apply {
            putExtra(DEVICE_KEY, item)
        }
        setResult(RESULT_OK, i)
        finish()
    }
}