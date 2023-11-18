package rog.gameplay.mixins

import rog.gameplay._

trait Memory extends Actor {
    var memory: Set[Pos] = Set()

    override def fov(): Set[Pos] = {
        val newFov: Set[Pos] = super.fov()
        memory ++= newFov
        newFov
    }
}
