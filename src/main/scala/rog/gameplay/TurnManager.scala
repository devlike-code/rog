package rog.gameplay

import rog.gameplay.actors._
import rog.gameplay.actions.SkipAction
object TurnManager {    
    var queue = scala.collection.mutable.Queue[Actor]()
    private var lastActed: Option[Actor] = None

    def initialize() = {
        World.getActors().foreach(actor => {
            actor.time = actor("speed")
            queue.enqueue(actor)
        })
    }

    def next(actor: Actor) = {
        queue.dequeue()
        queue.enqueue(actor)        
    }

    def update(): Unit = {
        queue.headOption match {
            case head@Some(actor) if actor.time <= 0 && lastActed == head =>                
                World.getActors().foreach(actor => {
                    actor.time = actor("speed")
                })
                next(actor)

            case Some(actor) if actor.time <= 0 =>
                next(actor)
                update()

            case head@Some(actor) if actor.nextAction.isDefined => {
                lastActed = head
                actor.time -= Action.run((actor, actor.nextAction.get))
                // println("Current actions on ", actor, actor.time)
                actor.nextAction = None
                next(actor)
                update()
            }

            case head@Some(actor) if actor != Player => {
                actor.update()
                actor.time -= Action.run((actor, actor.nextAction.getOrElse(SkipAction)))
                // println("Current actions on ", actor, actor.time)
                actor.nextAction = None
                next(actor)
                update()
            }

            case p => { /* waiting on player*/ }
        }
    }
}
