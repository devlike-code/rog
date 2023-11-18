package rog.gameplay.actions

import rog.gameplay._

case class AttackAction(target: Actor) extends Action {
    def length = ActionTime.full
    def perform(actor: Actor): ActionResult = ???
}

