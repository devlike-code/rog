package rog.gameplay.game_states

import rog.gameplay.GameState
import rog.rexpaint.RexPaint
import rog.rexpaint.RexPaintDocument
import rog.engine.RogInput
import rog.gameplay.GameStateDriver
import rog.gameplay.CommonAssets
import rog.gameplay.TriggerStartAdventure
import rog.gameplay.TriggerOptions

trait TitleScreenMenuOption {
    def previousOption: Option[TitleScreenMenuOption]
    def nextOption: Option[TitleScreenMenuOption]
}

case object StartAdventureMenuOption extends TitleScreenMenuOption {
    override def previousOption = Some(QuitGameMenuOption)
    override def nextOption = Some(OptionsMenuOption)
}

case object OptionsMenuOption extends TitleScreenMenuOption {
    override def previousOption = Some(StartAdventureMenuOption)
    override def nextOption = Some(QuitGameMenuOption)
}

case object QuitGameMenuOption extends TitleScreenMenuOption {
    override def previousOption = Some(OptionsMenuOption)
    override def nextOption = Some(StartAdventureMenuOption)
}

case object TitleScreenGameState extends GameState {
    val start = RexPaint.loadFromResources("rex/buttons/start-adventure.xp")
    val options = RexPaint.loadFromResources("rex/buttons/options.xp")
    val quit = RexPaint.loadFromResources("rex/buttons/quit-game.xp")

    var selected: TitleScreenMenuOption = StartAdventureMenuOption

    def drawButton(image: RexPaintDocument, expectedSelected: TitleScreenMenuOption, x: Int, y: Int) = {
        if (selected == expectedSelected)
            CommonAssets.selectionButton.show(x - 2, y - 1)

        image.show(x, y)
    }

    override def update() = {
        if (RogInput.checkOnce("Up")) {
            selected = selected.previousOption.getOrElse(selected)
        } else if (RogInput.checkOnce("Down")) {
            selected = selected.nextOption.getOrElse(selected)
        } else if (RogInput.checkOnce("Ok")) {
            selected match {
                case StartAdventureMenuOption => 
                    GameStateDriver.trigger(TriggerStartAdventure)

                case OptionsMenuOption => 
                    GameStateDriver.trigger(TriggerOptions)

                case QuitGameMenuOption => 
                    System.exit(0)
            }
        }
    }

    override def render() = {
        CommonAssets.rogLogo.show(32, 6)

        drawButton(start, StartAdventureMenuOption, 32, 20)
        drawButton(options, OptionsMenuOption, 32, 30)
        drawButton(quit, QuitGameMenuOption, 32, 40)
    }
}
