package rog.gameplay

import rog.engine.RogStateMachineDriver
import rog.gameplay.game_states._
import rog.engine.RogStateMachineNode

trait GameState extends RogStateMachineNode

case object PauseMenuGameState extends GameState

case object GameStateDriver extends RogStateMachineDriver[GameState, String] {
    var currentState: GameState = FadeFromBlackTransition(StartupGameState)
    
    override def transitions: PartialFunction[(GameState, String),GameState] = {
        case (self@StartupGameState, "end") => FadeToBlackTransition(self, TitleScreenGameState)
        case (self@StartupGameState, "hurry") => FadeToBlackTransition(self, TitleScreenGameState, 10)
        case (self@TitleScreenGameState, "adventure") => FadeToBlackTransition(self, AdventureGameState)
        case (TitleScreenGameState, "options") => OptionsGameState
        case (OptionsGameState, "back") => TitleScreenGameState
        case (AdventureGameState, "pause") => PauseMenuGameState
        case (PauseMenuGameState, "unpause") => AdventureGameState

        case (FadeToBlackTransition(from, to, _dur), _transition) => FadeFromBlackTransition(to)
        case (FadeFromBlackTransition(to, _dur), _transition) => to
    }
}