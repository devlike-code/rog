package rog.gameplay

import java.awt.Color
import rog.engine.RogColor
import rlforj.los.ShadowCasting
import rlforj.los.PrecisePermissive
import rog.engine.RogInput

trait Ability[T] extends (() => T) {
	def to_value(): Int
}

case class Stat(value: Int) extends Ability[Int] {
	override def apply(): Int = value
	def to_value(): Int = value
}

trait Actor {
	var nextAction: Option[Action] = None
	var pos: Pos
	var time: Int = 0
	var vows: scala.collection.mutable.Map[String, Ability[_]] = scala.collection.mutable.Map()
	var cachedFOV: Set[Pos] = Set()
	var inventory: Seq[Item] = Seq()
	
	def show(): (Char, Color, Color)
	
	def fov(): Set[Pos] = {
		cachedFOV = World.redoVisited(() => {
			val fovCalculator = new PrecisePermissive()
			fovCalculator.visitFieldOfView(World, pos.x, pos.y, this("sight"))
		})
		cachedFOV
	}
	
	def update(): Unit

	def vowOption(ability: String): Option[Int] = 
		this.vows.get(ability).map(_.to_value())

    def apply(ability: String): Int =
        this.vows.get(ability).map(_.to_value()).getOrElse(0)
}
