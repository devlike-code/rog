package rog.gameplay.game_states

import rog.engine.RogSceneGraph
import rog.gameplay.GameState

case object AdventureGameState extends GameState {
    override def render(): Unit = {
        RogSceneGraph.render()
    }
}
