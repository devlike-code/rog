package rog.gameplay.actions

import rog.gameplay._

case class OpenDoorAction(pos: Pos) extends Action {
    def length = ActionTime.half
    def perform(actor: Actor): ActionResult = {
        World.getTileAt(pos) match {
            case Some(DoorTile(Closed)) => 
                World.setTileAt(pos, DoorTile(Open))
                ActionContinuation(LookAction)
            
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
    def length = ActionTime.half
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
