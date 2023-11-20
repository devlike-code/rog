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
        World.getActors().foreach(_.update())
        TurnManager.update()
    }

    def render() = {
        Player.render()
        RogLog.render()
        Diagnostics.render()

        CommonAssets.sideWindow.show(60, 0)
    }
}
