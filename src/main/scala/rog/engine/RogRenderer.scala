package rog.engine

import javax.swing._
import java.awt._
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import rog.Rog

object RogRenderer {
    var currentRenderer: Option[Graphics2D] = None
    var textFont: TruetypeRogFont = null
    var rexFont: BitmapRogFont = null
    
    def show() {
        SwingUtilities.invokeLater(() => {
            val frame = new JFrame("Rog")
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
            frame.setResizable(false)

            val bufferedImage = ImageIO.read(Rog.getClass().getResourceAsStream("logo/devlike-100x100.png"))
            if (bufferedImage != null) {
                frame.setIconImage(bufferedImage)
            }
            
            frame.setSize(1280, 920)
            
            val drawingPanel = new DoubleBufferingPanel(frame)
            frame.add(drawingPanel)

            frame.setVisible(true)
        })
    }
}

