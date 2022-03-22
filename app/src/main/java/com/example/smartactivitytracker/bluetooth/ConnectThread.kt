package com.example.smartactivitytracker.bluetooth

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import android.widget.Toast
import com.example.smartactivitytracker.bluetooth.ReceiveThread
import java.io.IOException
import java.util.*

class ConnectThread(private val device: BluetoothDevice, private val receiver: ReceiveThread.Receiver, toast: Any) : Thread() {
    private val uuid = "00001101-0000-1000-8000-00805F9B34FB"
    private var mSocket: BluetoothSocket? = null
    lateinit var rThread: ReceiveThread
    private val toast = toast as Toast


    init {
        try {
            mSocket = device.createInsecureRfcommSocketToServiceRecord(UUID.fromString(uuid))
        }catch (i: IOException){
            closeConnection()
        }
    }

    override fun run() {
        try {
            Log.d("MyLog", "connecting")

            mSocket?.connect()
            Log.d("MyLog", "Connected")
            this.toast.show()

        } catch (i: IOException){
            Log.d("MyLog", "Can not connect to device!")
            closeConnection()
        }

    }

    fun startMeasure() {
        try {

            rThread = ReceiveThread(mSocket!!, receiver)
            rThread.start()
        }
        catch (i: IOException) {closeConnection()}
    }


    private fun closeConnection(){
        try {
            mSocket?.close()
        } catch (i: IOException){

        }
    }

    fun stopMeasure() {
        closeConnection()
    }

}