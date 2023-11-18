package rog.gameplay.mixins

import rog.gameplay._

trait Memory extends Actor {
    var memory: Map[Pos, Char] = Map()

    override def fov(): Set[Pos] = {
        val newFov: Set[Pos] = super.fov()
        newFov.foreach(pos => {
            memory += (pos -> World.getVisual(pos).get._1)
        })
        
        //memory ++= newFov.keys
        newFov
    }
}
