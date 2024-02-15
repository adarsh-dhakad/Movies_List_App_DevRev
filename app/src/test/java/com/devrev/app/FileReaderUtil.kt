package com.devrev.app

import java.io.*

object FileReaderUtil {

    fun kotlinReadFileWithNewLineFromResources(fileName: String): String {
        return getInputStreamFromResource(fileName)?.bufferedReader()
            .use { bufferReader -> bufferReader?.readText() } ?: ""
    }
    private fun getInputStreamFromResource(fileName: String) =
        javaClass.classLoader?.getResourceAsStream(fileName)
}