package rog.gameplay.game_states

import rog.gameplay.GameState
import rog.gameplay.CommonAssets
import rog.engine.RogInput
import rog.gameplay.GameStateDriver
import rog.rexpaint.RexPaint
import rog.gameplay.game_states.OptionsGameState
import rog.gameplay.TriggerBack

case object PauseMenuGameState extends GameState {    
    override def render(): Unit = OptionsGameState.render

    override def update(): Unit = {
        if (RogInput.checkOnce("Cancel")) {
            GameStateDriver.trigger(TriggerBack)
        }
    }
}
