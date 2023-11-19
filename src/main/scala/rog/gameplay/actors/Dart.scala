package rog.gameplay.actors

import rog.engine._
import rog.gameplay._
import rog.gameplay.mixins._
import rog.gameplay.actions._
import java.awt.Color;
import rog.gameplay.vows.OrdinaryVows

object Dart extends Actor with Memory {
    OrdinaryVows.apply(this)
    vows += ("cost:walk" -> Stat(ActionTime.half))
    
    var pos: Pos = Pos(12, 12)
    
    def show(): (Char, Color, Color) = {
        ('x', RogColor.TRANSPARENT, Color.RED)
    }
    
    def update() = {
        if (nextAction.isEmpty) {
            val rand = new scala.util.Random
            val choice = rand.nextInt(4);

            if (choice == 0) {
                nextAction = Some(WalkAction(North))
            } else if (choice == 1) {
                nextAction = Some(WalkAction(South))
            } else if (choice == 2) {
                nextAction = Some(WalkAction(West))
            } else if (choice == 3) {
                nextAction = Some(WalkAction(East))
            }
        }
    }
}

