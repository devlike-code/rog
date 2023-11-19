package rog.gameplay.vows

import rog.gameplay._

object OrdinaryVows {
    def apply(actor: Actor) = {
        // stats
        actor.vows += ("sight" -> Stat(6))
        actor.vows += ("bravery" -> Stat(6))
        actor.vows += ("health" -> Stat(6))
        actor.vows += ("strength" -> Stat(6))
        actor.vows += ("stamina" -> Stat(12))

        // costs
        actor.vows += ("cost:look" -> Stat(ActionTime.none))
        actor.vows += ("cost:walk" -> Stat(ActionTime.full))
        actor.vows += ("cost:search" -> Stat(ActionTime.full))
    }
}
