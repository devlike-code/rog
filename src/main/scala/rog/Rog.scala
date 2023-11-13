package rog

import rog.engine.RogRenderer
import rog.gameplay.World
import rog.gameplay.Pos
import rog.gameplay.Player
import rog.rexpaint.RexPaint
import rog.engine._
import rog.gameplay.GameStateDriver
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.core.`type`.TypeReference
import scala.collection.immutable.ArraySeq
import scala.io.Source
import rog.config.RogConfig
import rog.gameplay.FloorTile

object Rog extends RogGame {
    def main(args: Array[String]): Unit = {        
        Rog.run("config.json")
    }
    
    def update() = GameStateDriver.update
    def render() = GameStateDriver.render

    def init() {
        for (i <- 3 to 12) {
            for (j <- 4 to 13) {
                World.setTileAt(Pos(i, j), FloorTile)
            }
        }

        World.addActor(Player(Pos(10, 10)))
    }
}
