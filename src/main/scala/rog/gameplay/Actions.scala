package rog.gameplay

import rog.engine.RogLog
import rog.gameplay.actors.Player

sealed trait ActionResult
case class ActionSucceed(message: String = "") extends ActionResult
case class ActionSequence(seq: Seq[Action]) extends ActionResult
case class ActionContinuation(action: Action) extends ActionResult
case class ActionAlternate(action: Action) extends ActionResult
case class ActionFail(message: String) extends ActionResult

trait Action {
    def length: Int
    def name: String
    def perform(actor: Actor): ActionResult
}

object ActionTime {
    val none = 0
    val half = 6
    val full = 12
    val long = 18
    val double = 24
}

object Action {
    def run(actor_action: (Actor, Action)): Int = {
        val (actor, action) = actor_action;
        val cost = actor.vowOption("cost:" + action.name).getOrElse(action.length)
        val performance = action.perform(actor)
        val total = performance match {
            case ActionFail(message) => {
                if (actor == Player && message != "") RogLog.add(message)
                actor.vowOption("cost:fail").getOrElse(0)
            }
            case ActionSequence(seq) => cost + seq.map(next => run(actor, next)).sum
            case ActionAlternate(alt) => run((actor, alt))
            case ActionContinuation(alt) => cost + run((actor, alt))
            case ActionSucceed(message) => {
                if (actor == Player && message != "") RogLog.add(message)
                cost
            }
        }

        total
    }
}
