package rog

import rog.engine._
import rog.gameplay._
import rog.gameplay.actors._
import rog.rexpaint.RexPaint
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.core.`type`.TypeReference
import scala.collection.immutable.ArraySeq
import scala.io.Source
import rog.config.RogConfig

object Rog extends RogGame {
    def main(args: Array[String]): Unit = {        
        Rog.run("config.json")
    }
    
    def update() = GameStateDriver.update
    def render() = GameStateDriver.render

    def init() = {
        for (i <- 2 to 13) {
            for (j <- 3 to 14) {
                World.setTileAt(Pos(i, j), WallTile)
            }
        }

        for (i <- 12 to 18) {
            for (j <- 7 to 11) {
                World.setTileAt(Pos(i, j), WallTile)
            }
        }

        for (i <- 3 to 12) {
            for (j <- 4 to 13) {
                World.setTileAt(Pos(i, j), FloorTile)
            }
        }

        for (i <- 13 to 17) {
            for (j <- 8 to 10) {
                World.setTileAt(Pos(i, j), FloorTile)
            }
        }

        for (j <- 4 to 13) {
            World.setTileAt(Pos(8, j), WallTile)
        }


        World.setTileAt(Pos(8, 8), DoorTile(Closed))
        World.addActor(Player)
        Player.fov()

        World.addActor(Dart)

        TurnManager.initialize()
    }
}
