package rog.gameplay

import rog.gameplay.actors._

object TurnManager {    
    var queue = scala.collection.mutable.Queue[Actor]()

    def initialize() {
        World.getActors().foreach(actor => {
            actor.time = actor("speed")
            queue.enqueue(actor)
        })

        Player.fov()
    }

    def next(actor: Actor) = {
        queue.dequeue()
        queue.enqueue(actor)
    }

    def update() {
        queue.headOption match {
            case Some(actor) if actor.time <= 0 =>
                next(actor)
            case Some(actor) if actor.nextAction.isDefined => {
                actor.time -= Action.run((actor, actor.nextAction.get))
                actor.nextAction = None
                next(actor)
            }
            case _ => {}
        }
    }
}
