package rog.rexpaint

import java.nio.file.Files
import java.io.File
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.awt.Color
import rog.Rog

object RexPaint {
    def packColor(r: Int, g: Int, b: Int, a: Int): Int = 
        ((a & 0xFF) << 24) |
        ((r & 0xFF) << 16) |
        ((g & 0xFF) << 8)  |
        ((b & 0xFF) << 0)

    def loadFromFile(filename: String): RexPaintDocument = {
        val bytes = Files.readAllBytes(new File(filename).toPath())
        load(bytes)
    }

    def loadFromResources(filename: String): RexPaintDocument = {
        val bytes = Rog.getClass().getResourceAsStream(filename).readAllBytes()
        load(bytes)
    }

    def readSignedInt(buffer: ByteBuffer): Int = {
        val int = buffer.getInt();
        int & (1 << 32) - 1
    }

    def readByte(buffer: ByteBuffer): Int = {
        val b = buffer.get()
        if (b < 0) 256 + b
        else b
    }

    def load(bytes: Array[Byte]): RexPaintDocument = {
        val decompressed = GzipUtilities.decode(bytes)
        val buffer = ByteBuffer.wrap(decompressed)
        buffer.order(ByteOrder.LITTLE_ENDIAN)
        
        val version = readSignedInt(buffer);
        val layerCount = buffer.getInt()
        var layers = Seq[RexPaintLayer]()
        
        for (i <- 0 until layerCount) {
            val width = buffer.getInt()
            val height = buffer.getInt()
            val data = Array.ofDim[RexPaintChar](width, height)
            for (index <- 0 until width * height) {
                val (x, y) = (index / height, index % height)
                val sym = buffer.getInt().toChar
                val (fr, fg, fb) = (readByte(buffer), readByte(buffer), readByte(buffer))
                val (br, bg, bb) = (readByte(buffer), readByte(buffer), readByte(buffer))
                val front = new Color(fr, fg, fb);
                val back = new Color(br, bg, bb);
                data(x)(y) = RexPaintChar(front, back, sym)
            }
            layers = layers :+ RexPaintLayer(width, height, data)
        }

        RexPaintDocument(version, layers)
    }
}
