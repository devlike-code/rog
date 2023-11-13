package rog.gameplay.game_states

import rog.gameplay.GameState
import rog.gameplay.GameStateDriver
import java.awt.Graphics2D
import rog.engine.RogRenderer
import java.awt.Color


abstract class FadeTransition[G <: GameState](next: G) extends GameState {
    val frameDuration: Int
    var time: Int = 0

    def alpha(time: Int): Float

    override def update() {
        time += 1
        if (time == frameDuration) {
            GameStateDriver.trigger("end")
        }
    }

    override def render() {
        next.render()
        renderFade((255.0f * alpha(time)).toInt)
    }

    def renderFade(fade: Int) = {
        val render: Graphics2D = RogRenderer()
        val rect = render.getDeviceConfiguration().getBounds();
        render.setColor(new Color(0, 0, 0, fade))
        render.fillRect(0, 0, rect.width, rect.height)
    }
}

case class FadeFromBlackTransition[G <: GameState](to: G, frameDuration: Int = 30) extends FadeTransition(to) {
    def alpha(time: Int) = 1.0f - (time.toFloat / frameDuration.toFloat)

    override def update() = {
        super.update()
        to.update()
    }
}

case class FadeToBlackTransition[G1 <: GameState, G2 <: GameState](from: G1, to: G2, frameDuration: Int = 30) extends FadeTransition(from) {
    def alpha(time: Int) = time.toFloat / frameDuration.toFloat
}

trait TimeDelayMixin {
    val delay: Int
    var time = 0

    def delayStillCounting(): Boolean = {
        time += 1
        if (time == delay) {
            GameStateDriver.trigger("end")
            false
        } else {
            true
        }
    }
}
