package rog.engine

import java.awt.event.KeyListener
import java.awt.event.KeyEvent
import scala.collection.mutable.Set
import scala.collection.mutable.Map
import scala.collection.mutable.MultiDict
import scala.collection.mutable.Queue

import rog.config.RogConfig

package object RogInputs {
    type InputName = String
    type ActionName = String
}

import rog.engine.RogInputs.ActionName
import rog.engine.RogInputs.InputName

object RogInput extends KeyListener {
    val inputQueue = Queue[(InputName, Boolean)]()
    var previousInputState = Set[InputName]()
    val currentInputState = Set[InputName]()
    val actionMapping = MultiDict[ActionName, InputName]()
    val inputToActions = Map[InputName, ActionName]()
    val actionValues = Map[ActionName, Float]()

    private def getKeyName(key: KeyEvent): String =
        KeyEvent.getKeyText(key.getKeyCode()).toString()

    override def keyTyped(key: KeyEvent): Unit = {}
    
    override def keyPressed(key: KeyEvent): Unit = {
        inputQueue += ((getKeyName(key), true))
    }
        
    override def keyReleased(key: KeyEvent): Unit = {
        if (RogConfig().debugFlags.contains("print_key_names")) {
            println(getKeyName(key))
        }

        inputQueue += ((getKeyName(key), false))
    }

    def update() = {
        previousInputState = currentInputState.clone
        inputQueue.dequeueAll(_ => true).foreach { 
            case (input, true) => currentInputState += input
            case (input, false) => currentInputState -= input
        }
    }

    def register(name: ActionName, inputs: InputName*) = {
        inputs.foreach(input => {
            actionMapping += (name -> input)
            inputToActions += (input -> name)
        })
    }

    def unregister(name: ActionName) = {
        actionMapping.get(name).foreach(input => inputToActions.remove(input))
        actionMapping.removeKey(name)
    }

    def checkOnce(name: ActionName): Boolean = {
        actionMapping.get(name).foldLeft(false)((acc, next) => {
            acc || previousInputState.contains(next) && 
                !currentInputState.contains(next)
        })
    }

    def checkHold(name: ActionName): Boolean = {
        actionMapping.get(name).foldLeft(false)((acc, next) => {
            acc || currentInputState.contains(next)
        })
    }
}