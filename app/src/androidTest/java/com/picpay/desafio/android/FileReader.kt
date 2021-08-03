package com.picpay.desafio.android

import androidx.test.platform.app.InstrumentationRegistry
import com.picpay.desafio.android.core.PicPayApplication
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets.UTF_8

object FileReader {

    fun readStringFromFile(fileName: String): String {
        try {
            val inputStream = (InstrumentationRegistry.getInstrumentation().targetContext
                .applicationContext as PicPayApplication).assets.open(fileName)
            val builder = StringBuilder()
            val reader = InputStreamReader(inputStream, UTF_8)
            reader.readLines().forEach {
                builder.append(it)
            }
            return builder.toString()
        } catch (e: IOException) {
            throw e
        }
    }
}