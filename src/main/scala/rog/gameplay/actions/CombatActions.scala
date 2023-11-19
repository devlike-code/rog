package rog.gameplay.actions

import rog.gameplay._

case class AttackAction(target: Actor) extends Action {
    def length = ActionTime.full
    def name = "attack"
    def perform(actor: Actor): ActionResult = 
        ActionSucceed("Don't know how to attack.")
}

