package rog.gameplay

import java.awt.Color
import rog.gameplay.Actor
import rog.gameplay.Tile
import rog.gameplay.Item

object World {
  private val tiles = scala.collection.mutable.Map[Pos, Tile]()
  private val actors = scala.collection.mutable.Map[Pos, Actor]()
  private val items = scala.collection.mutable.Map[Pos, Item]()

  def addActor(actor: Actor) = {
    actors.update(actor.pos, actor)
  }

  def moveActorTo(actor: Actor, xy: Pos) = {
    actors.remove(actor.pos)
    actor.pos = xy
    actors.update(xy, actor)
  }

  def getActorAt(xy: Pos): Option[Actor] = 
    actors.get(xy)

  def getItemAt(xy: Pos): Option[Item] = 
    items.get(xy)

  def setItemAt(xy: Pos, item: Item): Boolean =
    if (items.contains(xy)) {
      false 
    } else {
      items.update(xy, item)
      true
    }

  def removeItemAt(item: Item): Option[Item] =
    items.remove(item.pos)

  def getTileAt(xy: Pos): Option[Tile] = 
    tiles.get(xy)

  def setTileAt(xy: Pos, tile: Tile) = 
    tiles.update(xy, tile)

  def describe(xy: Pos): (Option[Actor], Option[Tile], Option[Item]) =
    (getActorAt(xy), getTileAt(xy), getItemAt(xy))
  
  def getVisual(xy: Pos): Option[(Char, Color, Color)] = {
    describe(xy) match {
      case (Some(actor), _, _) => Some(actor.show())
      case (_, _, Some(item)) => Some(item.show())
      case (_, Some(tile), _) => Some(tile.show())
      case _ => None
    }
  }
}

