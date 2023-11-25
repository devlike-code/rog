package rog.gameplay

import java.awt.Color
import rog.engine.RogColor

trait Tile {
  val isWalkable: Boolean
  
  def show(): (Char, Color, Color)
}

case object WallTile extends Tile {
  val isWalkable: Boolean = false

  def show() = ('#', RogColor.TRANSPARENT, Color.WHITE)
}

case object FloorTile extends Tile {
  val isWalkable: Boolean = true

  def show() = ('.', RogColor.TRANSPARENT, Color.WHITE)
}

sealed trait DoorState
case object Open extends DoorState
case object Closed extends DoorState
case class Locked(key: Item) extends DoorState

case class DoorTile(state: DoorState) extends Tile {
  val isWalkable: Boolean = state == Open
  
  def show() = state match {
    case Open => ('.', RogColor.TRANSPARENT, Color.WHITE)
    case _ => ('+', RogColor.TRANSPARENT, Color.WHITE)
  }
}