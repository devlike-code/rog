package rog.gameplay

trait ActionResult
case class ActionSucceed(message: String = "") extends ActionResult
case class ActionSequence(seq: Seq[Action]) extends ActionResult
case class ActionAlternate(action: Action) extends ActionResult
case class ActionFail(message: String) extends ActionResult

trait Action {
    def length: Int
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
        action.perform(actor) match {
            case ActionFail(message) => 0
            case ActionSequence(seq) => seq.map(next => run(actor, next)).sum
            case ActionAlternate(alt) => run((actor, alt))
            case ActionSucceed(message) => action.length
        }
    }
}
