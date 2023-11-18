package rog.gameplay.actions

import rog.gameplay._

case class LogAction(message: String) extends Action {
    def length = ActionTime.none

    def perform(actor: Actor): ActionResult = {
        println(message)
        ActionSucceed("")
    }
}

