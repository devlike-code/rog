package rog.rexpaint

case class RexPaintDocument(version: Int, layers: Seq[RexPaintLayer]) {
    def show(dx: Int, dy: Int) {
        layers.foreach(layer => {
            layer.show(dx, dy)
        })
    }
}
