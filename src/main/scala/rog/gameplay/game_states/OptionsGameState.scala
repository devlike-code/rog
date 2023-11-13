package rog.gameplay.game_states

import rog.gameplay.GameState
import rog.gameplay.CommonAssets
import rog.engine.RogInput
import rog.gameplay.GameStateDriver
import rog.rexpaint.RexPaint

case object OptionsGameState extends GameState {
    val options = RexPaint.loadFromResources("rex/titles/options-title.xp")
    
    override def render() = {
        CommonAssets.rogLogo.show(32, 6)
        options.show(30, 18)
    }

    override def update() = {
        if (RogInput.checkOnce("Cancel")) {
            GameStateDriver.trigger("back")
        }
    }
}
