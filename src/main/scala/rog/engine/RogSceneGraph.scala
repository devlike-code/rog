package rog.engine

import rog.gameplay.World
import rog.gameplay.Pos
import rog.rexpaint.RexPaintDocument

object RogSceneGraph {
    var brain: RexPaintDocument = null;
    val rand = new scala.util.Random

    def render() {
        renderWorld()
        renderGui()
    }

    def renderWorld() {
        for (i <- 0 to 78) {
          for (j <- 0 to 54) {
            World.getVisual(Pos(i, j)) match {
              case Some((sym, back, front)) => {
                val x = i * 16;
                val y = j * 16;
        
                RogRenderer.currentRenderer.foreach(render => {
                    render.setColor(back)
                    render.fillRect(x.toInt, y.toInt, 16, 16)
                    render.setColor(front)
                    RogRenderer.textFont.draw(sym)(x.toInt + 3, y.toInt + 13)(render)
                })
              }
        
              case None => {}
            }
          }
        }
    }

    def renderGui() {
      for (i <- 0 to 22) {
        brain.show(rand.nextInt(85), rand.nextInt(45))
      }
    }
}
