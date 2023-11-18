package rog.gameplay

import rog.engine.RogStateMachineDriver
import rog.gameplay.game_states._
import rog.engine.RogStateMachineNode
import game_states.OptionsGameState
import rog.config.RogConfig
import game_states.AdventureGameState

trait GameState extends RogStateMachineNode

sealed trait GameStateTransition
case object TriggerEnd extends GameStateTransition
case object TriggerCut extends GameStateTransition
case object TriggerStartAdventure extends GameStateTransition
case object TriggerOptions extends GameStateTransition
case object TriggerBack extends GameStateTransition

case object GameStateDriver extends RogStateMachineDriver[GameState, GameStateTransition] {
    var currentState: GameState = 
        FadeFromBlackTransition(
            if (RogConfig().debugFlags.contains("jump_into_game_directly")) { 
                AdventureGameState 
            } else { 
                StartupGameState
            })
    
    override def transitions: PartialFunction[(GameState, GameStateTransition), GameState] = {
        case (self@StartupGameState, TriggerEnd) => FadeToBlackTransition(self, TitleScreenGameState)
        case (self@StartupGameState, TriggerCut) => FadeToBlackTransition(self, TitleScreenGameState, 10)
        case (self@TitleScreenGameState, TriggerStartAdventure) => FadeToBlackTransition(self, AdventureGameState)
        case (TitleScreenGameState, TriggerOptions) => OptionsGameState
        case (OptionsGameState, TriggerBack) => TitleScreenGameState
        case (AdventureGameState, TriggerOptions) => PauseMenuGameState
        case (PauseMenuGameState, TriggerBack) => AdventureGameState

        case (FadeToBlackTransition(from, to, _dur), _transition) => FadeFromBlackTransition(to)
        case (FadeFromBlackTransition(to, _dur), _transition) => to
    }
}