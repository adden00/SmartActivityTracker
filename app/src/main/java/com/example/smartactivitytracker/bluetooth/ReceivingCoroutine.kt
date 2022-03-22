package com.example.smartactivitytracker.bluetooth

import android.app.Activity
import android.bluetooth.BluetoothSocket
import android.util.Log
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream


class ReceivingCoroutine(private val bSocket: BluetoothSocket, private val receiver: Receiver) {
    var inStream: InputStream?= null
    var outStream: OutputStream?= null
    internal var activity: Activity?= null
    lateinit var temper: String

    init {
        try {
            inStream = bSocket.inputStream
        } catch (i: IOException){

        }

        try {
            outStream = bSocket.outputStream
        } catch (i: IOException){

        }
    }

    suspend fun start() = coroutineScope{
        val job = launch {
            receive()
        }

    }

    private fun receive()  {
        val buf = ByteArray(256)
        while (true){
            try {
                val size = inStream?.read(buf)
                var message = String(buf, 0, size!!)
                message = trim(message)
                if (message.isNotEmpty()) {


                    when {
                        message[0] == '.' -> {
                            temper += message
                            Log.d("MyLog", "message: $temper")
                            receiver.receiveData(temper) // вызываем метод интерфейса, описанный в main_activity
                            temper = ""
                        }
                        message.length > 4 -> {
                            temper = message
                            Log.d("MyLog", "message: $temper")
                            receiver.receiveData(temper) // вызываем метод интерфейса, описанный в main_activity
                            temper = ""
                        }
                        else -> {
                            temper = message
                        }
                    }
                }

            }catch (i: IOException) {
                Log.d("MyLog", "message: caught!"); break}
        }
    }

    private fun trim(input: String): String {  // Убираем лишние символы из входных данных
        var temp: String = input
        temp = temp.replace("\n", "")
        temp = temp.replace(" ", "")
        temp = temp.replace("\r", "")
        return temp
    }
    interface Receiver {
        fun receiveData(data: String)

    }
}