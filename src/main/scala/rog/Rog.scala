package rog

import rog.engine.RogRenderer
import rog.gameplay.World
import rog.gameplay.Pos
import rog.gameplay.Player
import rog.rexpaint.RexPaint
import rog.engine._

object Rog extends RogGame {
    def main(args: Array[String]): Unit = {        
        Rog.run()
    }
    
    def render() {
        RogSceneGraph.render()
    }

    def init() {
        RogRenderer.textFont = TruetypeRogFont("fonts/whitrabt.ttf", 16)
        RogRenderer.rexFont = BitmapRogFont("fonts/MRMOTEXTEX_rexpaintx2.png", 16, 16, 16)
        RogSceneGraph.brain = RexPaint.loadFromResources("rex/brain.xp")

        World.addActor(Player(Pos(10, 10)))
    }
}
