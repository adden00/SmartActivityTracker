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

class ReceiveThread(bSocket: BluetoothSocket, private val receiver: Receiver) :
    Thread() {
    var inStream: InputStream? = null

    init {
        try {
            inStream = bSocket.inputStream
        } catch (i: IOException) {}
    }

    override fun run() {
        val buf = ByteArray(255)
        while (true) {
            try {                                            // бесконечный цикл, в котором происходит чтение данных и отправка в активити
                val size = inStream?.read(buf)
                var message = String(buf, 0, size!!)
                message = trim(message)
                var droppedMsg = message.drop(1)
                val pos = droppedMsg.indexOfAny(charArrayOf('s', 't', 'h', 'b'))

                if (pos >= 0) droppedMsg = droppedMsg.substring(0, pos)

                if (message.isNotEmpty()) {
                    message = message.trim()
                    Log.d("MyLog", "Message: $message")

                    receiver.receiveData(
                        Datas(
                            null, message[0].toString(),
                            droppedMsg.trim(), getCurrentTime()))
                }
            } catch (i: IOException) {break}
        }
    }

    private fun getCurrentTime(): String {              // получение текущего времени для каждого входящего значения
        val formatter = SimpleDateFormat("hh:mm:ss - yyyy/MM/dd", Locale.getDefault())
        return formatter.format(Calendar.getInstance().time)
    }

    private fun trim(input: String): String {  // функция уберает лишние символы из входных данных
        var temp: String = input
        temp = temp.replace("\n", "")
        temp = temp.replace(" ", "")
        temp = temp.replace("\r", "")
        return temp
    }

    interface Receiver {               // интерфейс для отправки данных
        fun receiveData(data: Datas)
    }
}