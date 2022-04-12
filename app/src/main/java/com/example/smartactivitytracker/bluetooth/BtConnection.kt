package com.example.smartactivitytracker.bluetooth

import android.bluetooth.BluetoothAdapter

class BtConnection(private val adapter: BluetoothAdapter, private val receiver: ReceiveThread.Receiver,
                   private var toast1: Any, private var toast2: Any) {

    lateinit var cThread: ConnectThread

    fun connect(mac: String) {
        if (adapter.isEnabled && mac.isNotEmpty()) {
            val device = adapter.getRemoteDevice(mac)
            device.let {
                cThread = ConnectThread(it, receiver, toast1, toast2)
                cThread.start()
            }
        }
    }

    fun startMeasure(){
        cThread.startMeasure()
    }

    fun sendMessage(message: String) {
        cThread.rThread.sendMessage(message.toByteArray())
    }

    fun stopMeasure() {
        cThread.stopMeasure()
    }

} 