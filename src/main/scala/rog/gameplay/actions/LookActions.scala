package rog.gameplay.actions

import rog.gameplay._

case object LookAction extends Action {
    def length = ActionTime.none
    def perform(actor: Actor): ActionResult = {
        actor.fov()
        ActionSucceed("")
    }
}