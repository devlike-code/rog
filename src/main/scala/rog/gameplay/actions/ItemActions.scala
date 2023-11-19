package rog.gameplay.actions


import rog.gameplay._

case class PickupItemAction(item: Item) extends Action {
    def length = ActionTime.full
    def name = "pickup_item"
    def perform(actor: Actor): ActionResult = {
        actor.inventory = actor.inventory :+ item
        World.removeItemAt(item)
        ActionSucceed(s"You picked up ${item}.")
    }
}

