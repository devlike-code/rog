package rog.engine

import javax.swing.JPanel
import java.awt.image.BufferedImage
import java.awt.Font
import javax.swing.JFrame
import rog.Rog
import javax.swing.Timer
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Color

import rog.gameplay.Pos
import rog.gameplay.World
import rog.rexpaint.RexPaintDocument
import rog.rexpaint.RexPaint
import java.nio.file.Files

class DoubleBufferingPanel extends JPanel { 
    private var buffer: BufferedImage = null
        
    def this(frame: JFrame) {
        this();
        this.setSize(frame.getSize());
        
        RogRenderer.textFont = TruetypeRogFont("fonts/whitrabt.ttf", 16)
        RogRenderer.rexFont = BitmapRogFont("fonts/MRMOTEXTEX_rexpaintx2.png", 16, 16, 16)
        RogSceneGraph.brain = RexPaint.loadFromResources("rex/brain.xp")
        this.buffer = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_ARGB)
        this.setFont(RogRenderer.textFont.derivedFont)
        val timer = new Timer(32, _ => repaint())
        timer.start()
    }
    
    override def paintComponent(g: Graphics): Unit = {
        super.paintComponent(g)
        val rand = new scala.util.Random
        val measureTimeStart = System.currentTimeMillis
        
        val bufferGraphics = buffer.getGraphics.asInstanceOf[Graphics2D]
        bufferGraphics.setFont(RogRenderer.textFont.derivedFont);
        bufferGraphics.setColor(Color.BLACK)
        bufferGraphics.fillRect(0, 0, getWidth, getHeight)

        RogRenderer.currentRenderer = Some(bufferGraphics)
        
        RogSceneGraph.render()

        RogRenderer.currentRenderer = None
        bufferGraphics.dispose()
        
        val measureTimeStop = System.currentTimeMillis - measureTimeStart
        Diagnostics.addRenderTime(measureTimeStop)
        
        println(Diagnostics.getAverageRenderTimeMs() + "ms")
        
        g.drawImage(buffer, 0, 0, null)
    }
}