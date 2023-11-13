package rog.gameplay

trait ActionResult
case class ActionSucceed(message: String = "") extends ActionResult
case class ActionSequence(seq: Seq[Action]) extends ActionResult
case class ActionAlternate(action: Action) extends ActionResult
case class ActionFail(message: String) extends ActionResult

trait Action {
    def perform(actor: Actor): ActionResult
}

case class TeleportAction(to: Pos) extends Action {
    def perform(actor: Actor): ActionResult = {
        World.moveActorTo(actor, to)
        ActionSucceed()
    }
}

case class OpenDoorAction(pos: Pos) extends Action {
    def perform(actor: Actor): ActionResult = {
        World.getTileAt(pos) match {
            case Some(DoorTile(Closed)) => 
                World.setTileAt(pos, DoorTile(Open))
                ActionSucceed()
            
            case Some(DoorTile(Locked(key))) if actor.inventory.contains(key) =>
                World.setTileAt(pos, DoorTile(Open))
                actor.inventory = actor.inventory.diff(Seq(key))
                ActionSucceed(s"You use the ${key} to unlock the door.")
            
            case Some(DoorTile(Locked(_))) => 
                ActionFail("This door is locked and cannot be opened.")
            
            case Some(DoorTile(Open)) =>
                ActionSucceed()
            
            case _ => 
                ActionFail("No door found at this location.")
        }
    }
}

case class CloseDoorAction(pos: Pos) extends Action {
    def perform(actor: Actor): ActionResult = {
        World.getTileAt(pos) match {
            case Some(DoorTile(Open)) =>
                World.setTileAt(pos, DoorTile(Closed))
                ActionSucceed()
            
            case Some(DoorTile(_)) => 
                ActionFail("Door already closed.")
            
            case _ => 
                ActionFail("No door found at this location.")
        }
    }
}

case class PickupItemAction(item: Item) extends Action {
    def perform(actor: Actor): ActionResult = {
        actor.inventory = actor.inventory :+ item
        World.removeItemAt(item)
        ActionSucceed(s"You picked up ${item}.")
    }
}

case class WalkAction(move: Direction) extends Action {
    def perform(actor: Actor): ActionResult = {
        val pos = actor.pos + move.dir();
        
        World.describe(pos) match {
            case (Some(target), _, _) if target != actor => 
                ActionAlternate(AttackAction(target))
            
            case (_, Some(DoorTile(Closed)), _) => 
                ActionAlternate(OpenDoorAction(pos));
            
            case (_, _, Some(item)) if item.isInstanceOf[Player] => 
                ActionSequence(Seq(PickupItemAction(item), TeleportAction(pos)))
            
            case (_, Some(tile), _) if tile.isWalkable =>
                ActionAlternate(TeleportAction(pos))
            
            case _ => 
                ActionFail("Cannot move there.")
        }
    }
}

case class AttackAction(target: Actor) extends Action {
    def perform(actor: Actor): ActionResult = ???
}