package rog.gameplay.actions

import rog.gameplay._

case class LogAction(message: String) extends Action {
    def length = ActionTime.none
    def name = "log_message"
    def perform(actor: Actor): ActionResult = {
        println(message)
        ActionSucceed("")
    }
}

