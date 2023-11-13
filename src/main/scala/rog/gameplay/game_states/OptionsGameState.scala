package rog.gameplay.game_states

import rog.gameplay.GameState
import rog.gameplay.CommonAssets
import rog.engine.RogInput
import rog.gameplay.GameStateDriver
import rog.rexpaint.RexPaint
import rog.gameplay.TriggerBack

case object OptionsGameState extends GameState {
    
    override def render() = {
        CommonAssets.rogLogo.show(32, 6)
        CommonAssets.optionsTitle.show(30, 18)
    }

    override def update() = {
        if (RogInput.checkOnce("Cancel")) {
            GameStateDriver.trigger(TriggerBack)
        }
    }
}
