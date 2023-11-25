package rog.gameplay.actors

import rog.engine._
import rog.gameplay._
import rog.gameplay.mixins._
import rog.gameplay.actions._
import java.awt.Color;
import rog.gameplay.vows.OrdinaryVows

object Player extends Actor with Memory {
  OrdinaryVows.apply(this)

  var pos = Pos(10, 10)

  def show(): (Char, Color, Color) = {
    ('@', RogColor.TRANSPARENT, Color.YELLOW)
  }

  def update() = {
    if (nextAction.isEmpty) {
      if (RogInput.checkOnce("Up")) {
        nextAction = Some(WalkAction(North))
      } else if (RogInput.checkOnce("Down")) {
        nextAction = Some(WalkAction(South))
      } else if (RogInput.checkOnce("Left")) {
        nextAction = Some(WalkAction(West))
      } else if (RogInput.checkOnce("Right")) {
        nextAction = Some(WalkAction(East))
      } else if (RogInput.checkOnce("Wait")) {
        nextAction = Some(SkipAction)
      } else if (RogInput.checkOnce("Search")) {
        nextAction = Some(SearchAction)
      }
    }
  }

  def render() = {
    val render = RogRenderer()
    Player.memory.map {
      case (pos, chr) => {
        if (!Player.cachedFOV.contains(pos)) {
          val x = pos.x * 16
          val y = pos.y * 16
          render.setColor(Color.BLACK)
          render.fillRect(x.toInt, y.toInt, 16, 16)
          render.setColor(Color.GRAY.darker())
          RogRenderer.textFont.draw(chr)(x.toInt, y.toInt)(render)
        }
      }
    }

    Player.cachedFOV.foreach {
      case pos @ Pos(i, j) => {
        World.getVisual(pos) match {
          case Some((sym, back, front)) => {
            val x = i * 16;
            val y = j * 16;
            render.setColor(back)
            render.fillRect(x.toInt, y.toInt, 16, 16)
            render.setColor(front)
            RogRenderer.textFont.draw(sym)(x.toInt, y.toInt)(render)
          }

          case None => {}
        }
      }
    }
  }
}
