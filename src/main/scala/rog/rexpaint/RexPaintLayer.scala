package rog.rexpaint

import java.awt.Graphics2D
import rog.engine.RogRenderer

case class RexPaintLayer(width: Int, height: Int, data: Array[Array[RexPaintChar]]) {
    def show(dx: Int, dy: Int) = {
        for (y <- 0 until height) {
            for (x <- 0 until width) {
                if (data(x)(y).sym != 32) {
                    data(x)(y).show((dx + x) * 16, (dy + y) * 16)
                }
            }
        }
    }
}
