package rog.gameplay.actions

import rog.gameplay._

case object LookAction extends Action {
    def length = ActionTime.none
    def name = "look"
    def perform(actor: Actor): ActionResult = {
        actor.fov()
        ActionSucceed("")
    }
}

case object SearchAction extends Action {
    def length = ActionTime.full
    def name = "search"

    def perform(actor: Actor): ActionResult = {
        actor.fov()
        ActionSucceed("")
    }
}