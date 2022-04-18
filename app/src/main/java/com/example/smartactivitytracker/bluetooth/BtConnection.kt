package com.example.smartactivitytracker.bluetooth

import android.bluetooth.BluetoothAdapter

class BtConnection(private val adapter: BluetoothAdapter, private val receiver: ReceiveThread.Receiver,
                   private var toast1: Any, private var toast2: Any) {

    lateinit var cThread: ConnectThread                // объект класса, который запускает приём данных

    fun connect(mac: String) {                         // подключение к устройству по mac-адресу
        if (adapter.isEnabled && mac.isNotEmpty()) {
            val device = adapter.getRemoteDevice(mac)
            device.let {
                cThread = ConnectThread(it, receiver, toast1, toast2)
                cThread.start()
            }
        }
    }

    fun startMeasure(){                                // функция для начала чтения данных с блютуза
        cThread.startMeasure()
    }


    fun stopMeasure() {                               // функция для прекращения чтения данных с блютуза
        cThread.stopMeasure()
    }

} 