package rog.gameplay.game_states

import rog.engine.RogSceneGraph
import rog.gameplay.GameState
import rog.engine.RogInput
import rog.gameplay.GameStateDriver

case object AdventureGameState extends GameState {
    override def render(): Unit = {
        RogSceneGraph.render()
    }

    override def update(): Unit = {
        if (RogInput.checkOnce("Cancel")) {
            GameStateDriver.trigger("pause")
        }
    }
}
