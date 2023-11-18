package rog.gameplay

import rog.gameplay.actors._

object TurnManager {    
    var queue = scala.collection.mutable.Queue[Actor]()
    private var lastActed: Option[Actor] = None

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
            case head@Some(actor) if actor.time <= 0 && lastActed == head =>                
                World.getActors().foreach(actor => {
                    actor.time = actor("speed")
                    println("UP ", actor, actor.time)
                })
                next(actor)

            case Some(actor) if actor.time <= 0 =>
                next(actor)

            case head@Some(actor) if actor.nextAction.isDefined => {
                lastActed = head
                actor.time -= Action.run((actor, actor.nextAction.get))
                println("DOWN ", actor, actor.time)
                actor.nextAction = None
                next(actor)
            }

            case _ => {}
        }
    }
}
