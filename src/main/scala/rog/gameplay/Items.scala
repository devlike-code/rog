package rog.gameplay

import java.awt.Color

trait Item {
  var pos: Pos

  def show(): (Char, Color, Color)
}

