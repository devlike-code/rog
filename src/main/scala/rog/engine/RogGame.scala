package rog.engine

import rog.config.RogConfig

trait RogGame {
    def init()

    def update()
    def render()

    def run(configFile: String) {
        RogConfig.load(configFile)
        val config = RogConfig()

        config.inputMappings.foreach { case (action, inputs) => {
            RogInput.register(action, inputs:_*)
        }}

        config.bitmapFont.toSeq.headOption match {
            case Some((font, size)) => RogRenderer.rexFont = BitmapRogFont(font, size, size, size)
            case _ => {}
        }
            
        config.truetypeFont.toSeq.headOption match {
            case Some((font, size)) => RogRenderer.textFont = TruetypeRogFont(font, size)
            case _ => {}
        }

        init()
        RogRenderer.show(this)
    }
}
