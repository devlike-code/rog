package rog.gameplay.vows

import rog.gameplay._

object OrdinaryVows {
    def apply(actor: Actor) = {
        actor.vows += ("sight" -> Stat(6))
        actor.vows += ("bravery" -> Stat(6))
        actor.vows += ("health" -> Stat(6))
        actor.vows += ("strength" -> Stat(6))
        actor.vows += ("speed" -> Stat(12))
    }
}
