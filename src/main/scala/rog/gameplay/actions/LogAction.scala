package rog.gameplay.actions

import rog.gameplay._
import rog.engine.RogLog

case class LogAction(message: String) extends Action {
    def length = ActionTime.none
    def name = "log_message"
    def perform(actor: Actor): ActionResult = {
        RogLog.add(message)
        ActionSucceed("")
    }
}

