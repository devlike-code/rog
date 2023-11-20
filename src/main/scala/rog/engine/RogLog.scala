package rog.engine

import java.awt.Color
import rog.gameplay.CommonAssets

object RogLog {
    var log = Seq[String]()

    def add(message: String) = {
        log.lastOption match {
            case Some(last) if last.replace(" (repeated)", "") == message => 
                log = log.init ++ Seq(last.replace(" (repeated)", "") + " (repeated)")
            case _ => 
                log = log :+ message
        }
    }

    def apply(): Seq[String] = log

    def render() = {
        val render = RogRenderer()

        CommonAssets.logWindow.show(0, 45)

        render.setColor(Color.WHITE)
        RogRenderer.textFont.draw(s"Log")(16, 47 * 16 - 6)(render)

        RogLog()
            .takeRight(6)
            .reverse
            .padTo(6, "")            
            .reverse
            .zipWithIndex
            .foreach { case (str, i) => {
                RogRenderer.textFont.draw(str)(16, (49 + i) * 16 - 4)(render)
            }}
    }

}
