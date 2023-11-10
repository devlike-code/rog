package rog

import rog.engine.RogRenderer
import rog.gameplay.World
import rog.gameplay.Pos
import rog.gameplay.Player
import rog.rexpaint.RexPaint

object Rog {
  def main(args: Array[String]): Unit = {
    World.addActor(Player(Pos(10, 10)))
    RogRenderer.show()
  }
}
