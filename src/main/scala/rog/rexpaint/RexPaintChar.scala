package rog.rexpaint

import java.awt.Color
import rog.engine.RogRenderer
import rog.gameplay.Pos
import java.awt.Composite
import java.awt.AlphaComposite

object RexPaintChar {
    val srcOver = AlphaComposite.getInstance(AlphaComposite.SRC_OVER);
    val srcOut = AlphaComposite.getInstance(AlphaComposite.SRC_OUT);
}

case class RexPaintChar(front: Color, back: Color, sym: Char) {
    def show(x: Int, y: Int) {
        val render = RogRenderer()
        render.setComposite(RexPaintChar.srcOver);
        render.setColor(back)
        render.fillRect(x, y, 16, 16)
        RogRenderer.rexFont.draw(sym)(x, y)(render)

        render.setComposite(RexPaintChar.srcOut);
        render.setXORMode(front)
        RogRenderer.rexFont.draw(sym)(x, y)(render)
        render.setPaintMode()
        render.setComposite(RexPaintChar.srcOver);
    }
}

