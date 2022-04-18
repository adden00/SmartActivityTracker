package com.example.smartactivitytracker.bluetooth

import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import android.widget.Toast
import com.example.smartactivitytracker.activities.MainActivity
import com.example.smartactivitytracker.bluetooth.ReceiveThread
import java.io.IOException
import java.util.*

class ConnectThread(private val device: BluetoothDevice, private val receiver: ReceiveThread.Receiver, toast1: Any, toast2: Any) : Thread() {
    private val uuid = "00001101-0000-1000-8000-00805F9B34FB"
    private var mSocket: BluetoothSocket? = null
    lateinit var rThread: ReceiveThread
    private val toast1 = toast1 as Toast
    private val toast2 = toast2 as Toast

    init {
        try {
            mSocket = device.createInsecureRfcommSocketToServiceRecord(UUID.fromString(uuid))
        }catch (i: IOException){
            closeConnection()
        }
    }

    override fun run() {  // инициализация потока, подключение к устройству
        try {
            mSocket?.connect()
            this.toast1.show()

        } catch (i: IOException){
            this.toast2.show()
            closeConnection()
        }
    }
    private fun closeConnection(){ // закрытие потока
        try {
            mSocket?.close()
        } catch (i: IOException){ }
    }

    fun startMeasure() {    // старт потока измерения
        try {
            rThread = ReceiveThread(mSocket!!, receiver)
            rThread.start()
        }
        catch (i: IOException) {closeConnection()}
    }

    fun stopMeasure() {  // Остановка потока измерения
        closeConnection()
    }
}