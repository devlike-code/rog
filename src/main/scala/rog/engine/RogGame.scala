package rog.engine

trait RogGame {
    def init()
    def render()

    def run() {
        init()
        RogRenderer.show(this)
    }
}
