package rog.gameplay.actions

import rog.gameplay._

case object SkipAction extends Action {
    def length = ActionTime.full
    def name = "skip"
    
    def perform(actor: Actor): ActionResult = {
        ActionSucceed("")
    }
}