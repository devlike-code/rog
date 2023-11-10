package rog.rexpaint

import java.util.zip.GZIPInputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

object GzipUtilities {
    def decode(data: Array[Byte]): Array[Byte] = {
        var inputStream: GZIPInputStream = null
        inputStream = new GZIPInputStream(new ByteArrayInputStream(data))
        
        val outputStream = new ByteArrayOutputStream(data.length)
        var buffer = new Array[Byte](1024)
        while (inputStream.available() > 0) {
            val count = inputStream.read(buffer, 0, 1024)
            if (count > 0) {
                outputStream.write(buffer, 0, count);
            }
        }

        outputStream.close()
        inputStream.close()
        outputStream.toByteArray()
    }
}