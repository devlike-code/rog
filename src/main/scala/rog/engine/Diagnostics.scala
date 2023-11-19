package rog.engine
import scala.collection.mutable.Queue

object Diagnostics {
    val queueSize = 5
    val renderTimes: Queue[Long] = Queue()
    val updateTimes: Queue[Long] = Queue()

    def addRenderTime(time: Long): Unit = {
        renderTimes.enqueue(time)
        if (renderTimes.size > queueSize) {
            renderTimes.dequeue()
        }
    }

    def getAverageRenderTimeMs(): Float = {
        renderTimes.sum.toFloat / queueSize.toFloat
    }

    def addUpdateTime(time: Long): Unit = {
        updateTimes.enqueue(time)
        if (updateTimes.size > queueSize) {
            updateTimes.dequeue()
        }
    }

    def getAverageUpdateTimeMs(): Float = {
        updateTimes.sum.toFloat / queueSize.toFloat
    }

    def print() = {
        println("[ U: " + getAverageUpdateTimeMs() + "ms | R:" + getAverageRenderTimeMs() + "ms ]")
    }
}