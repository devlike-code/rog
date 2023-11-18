package rog.engine

import rog.gameplay._
import rog.gameplay.actions._
import rog.rexpaint.RexPaintDocument
import rlforj.los.ShadowCasting
import rog.gameplay.actors.Player
import java.awt.Color

object RogGameView {
    val rand = new scala.util.Random
    val actionQueue = new scala.collection.mutable.Queue[(Actor, Action)]()
    var eventLog = Seq[String]()

    def update() = {
        Player.update()
        TurnManager.update()
    }

    def render() = {
        val render = RogRenderer()

        Player.memory.map { case (pos, chr) => {
            if (!Player.cachedFOV.contains(pos)) {
                val x = pos.x * 16
                val y = pos.y * 16
                render.setColor(Color.BLACK)
                render.fillRect(x.toInt, y.toInt, 16, 16)
                render.setColor(Color.GRAY.darker())
                RogRenderer.textFont.draw(chr)(x.toInt + 3, y.toInt + 13)(render)
            }
        }}
        
        Player.cachedFOV.foreach{ case pos@Pos(i, j) => {
            World.getVisual(pos) match {
                case Some((sym, back, front)) => {
                    val x = i * 16;
                    val y = j * 16;                    
                    render.setColor(back)
                    render.fillRect(x.toInt, y.toInt, 16, 16)
                    render.setColor(front)
                    RogRenderer.textFont.draw(sym)(x.toInt + 3, y.toInt + 13)(render)
                }
                
                case None => {}
            }
        }}

        //render.setColor(Color.WHITE)
        //RogRenderer.textFont.draw(Player.pos.toString)(241, 30)(render)
    }
}
