package com.example.smartactivitytracker.bluetooth

import android.app.Activity
import android.bluetooth.BluetoothSocket
import android.util.Log
import com.example.smartactivitytracker.entities.Datas
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

class ReceiveThread(private val bSocket: BluetoothSocket, private val receiver: Receiver) :
    Thread() {
    var inStream: InputStream? = null
    var outStream: OutputStream? = null
    internal var activity: Activity? = null
    lateinit var temper: String


    init {
        try {
            inStream = bSocket.inputStream
        } catch (i: IOException) {

        }

        try {
            outStream = bSocket.outputStream
        } catch (i: IOException) {

        }
    }

    override fun run() {
        val buf = ByteArray(255)
        while (true) {
            try {
                val size = inStream?.read(buf)
                var message = String(buf, 0, size!!)
                message = trim(message)
                var droppedMsg = message.drop(1)
                val pos = droppedMsg.indexOfAny(charArrayOf('s', 't', 'h'))

                if (pos >= 0 ) droppedMsg = droppedMsg.substring(0, pos)

                if (message.isNotEmpty()) {
                    message = message.trim()
                    Log.d("MyLog", "Message: $message")

                    receiver.receiveData(
                        Datas(
                            null, message[0].toString(),
                            droppedMsg.trim(), getCurrentTime()
                        )
                    )

                }

            } catch (i: IOException) {
                Log.d("MyLog", "message: caught!"); break
            }
        }

    }

    private fun getCurrentTime(): String {
        val formatter = SimpleDateFormat("hh:mm:ss - yyyy/MM/dd", Locale.getDefault())
        return formatter.format(Calendar.getInstance().time)
    }

    fun sendMessage(byteArray: ByteArray) {
        try {
            outStream?.write(byteArray)
        } catch (i: IOException) {
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
        fun receiveData(data: Datas)

    }
}