package rog.gameplay.actors

import rog.engine._
import rog.gameplay._
import rog.gameplay.mixins._
import rog.gameplay.actions._
import java.awt.Color;
import rog.gameplay.vows.OrdinaryVows

object Player extends Actor with Memory {
    OrdinaryVows.apply(this)

    var pos: Pos = Pos(10, 10)
    
    def show(): (Char, Color, Color) = {
        ('@', RogColor.TRANSPARENT, Color.YELLOW)
    }
    
    def update() = {
        if (nextAction.isEmpty) {
            if (RogInput.checkOnce("Up")) {
                nextAction = Some(WalkAction(North))
            } else if (RogInput.checkOnce("Down")) {
                nextAction = Some(WalkAction(South))
            } else if (RogInput.checkOnce("Left")) {
                nextAction = Some(WalkAction(West))
            } else if (RogInput.checkOnce("Right")) {
                nextAction = Some(WalkAction(East))
            } else if (RogInput.checkOnce("Wait")) {
                nextAction = Some(SkipAction)
            } else if (RogInput.checkOnce("Search")) {
                nextAction = Some(SearchAction)
            }
        }
    }
}

