package rog.gameplay

case class Pos(val x: Int, val y: Int) {
    def +(other: Pos): Pos = 
        Pos(x + other.x, y + other.y)
    
    def -(other: Pos): Pos =
        Pos(x - other.x, y - other.y)
    
    def unapply(): (Int, Int) = 
        (x, y)
}

trait Direction {
    def dir(): Pos
}

case object North extends Direction { def dir(): Pos = Pos(0, -1) }
case object South extends Direction { def dir(): Pos = Pos(0, 1) }
case object East extends Direction { def dir(): Pos = Pos(1, 0) }
case object West extends Direction { def dir(): Pos = Pos(-1, 0) }

