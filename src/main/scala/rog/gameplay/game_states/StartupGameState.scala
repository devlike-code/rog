package rog.gameplay.game_states

import rog.gameplay.GameState
import rog.gameplay.game_states._ 
import rog.rexpaint.RexPaint
import rog.gameplay.GameStateDriver
import rog.engine.RogInput

case object StartupGameState extends GameState with TimeDelayMixin {
    val delay = 30
    val logo = RexPaint.loadFromResources("rex/logos/devlike.xp")

    override def update() {
        if (GameStateDriver.currentState == this && delayStillCounting()) {
            if (RogInput.checkOnce("Ok")) {
                GameStateDriver.trigger("hurry")
            }
        }
    }

    override def render() {
        logo.show(29, 15)
    }
}

