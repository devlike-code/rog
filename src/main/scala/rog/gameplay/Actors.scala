package rog.gameplay

import java.awt.Color
import rog.engine.RogColor

trait Actor {
  var pos: Pos;
  var inventory: Seq[Item] = Seq();

  def show(): (Char, Color, Color)
}

case class Player(var pos: Pos) extends Actor {
  
  def show(): (Char, Color, Color) = {
    ('@', RogColor.TRANSPARENT, Color.YELLOW)
  }
}

