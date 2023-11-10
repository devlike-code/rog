package rog.engine
import scala.collection.mutable.Queue

object Diagnostics {
    val queueSize = 5
    val renderTimes: Queue[Long] = Queue()

    def addRenderTime(time: Long): Unit = {
        renderTimes.enqueue(time)
        if (renderTimes.size > queueSize) {
            renderTimes.dequeue()
        }
    }

    def getAverageRenderTimeMs(): Float = {
        renderTimes.sum.toFloat / queueSize.toFloat
    }
}