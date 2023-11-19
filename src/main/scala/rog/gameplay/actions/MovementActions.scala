package rog.gameplay.actions

import rog.gameplay._

case class TeleportAction(to: Pos) extends Action {
    def length = ActionTime.none
    def name = "teleport"
    def perform(actor: Actor): ActionResult = {
        World.moveActorTo(actor, to)
        if (actor == actors.Player) {
            actor.fov()
        }
        ActionSucceed()
    }
}

case class WalkAction(move: Direction) extends Action {
    def length = ActionTime.full
    def name = "walk"
    def perform(actor: Actor): ActionResult = {
        val pos = actor.pos + move.dir()
        
        World.describe(pos) match {
            case (Some(target), _, _) if target != actor => 
                ActionAlternate(AttackAction(target))
            
            case (_, Some(DoorTile(Closed)), _) => 
                ActionAlternate(OpenDoorAction(pos))
            
            case (_, _, Some(item)) if actor == actors.Player => 
                ActionSequence(Seq(PickupItemAction(item), TeleportAction(pos)))
            
            case (_, Some(tile), _) if tile.isWalkable =>
                ActionContinuation(TeleportAction(pos))
            
            case _ => 
                ActionFail("Cannot move there.")
        }
    }
}

