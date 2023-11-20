package rog.engine

import java.awt.Graphics2D
import rog.Rog
import java.awt.Font
import javax.imageio.ImageIO
import java.awt.Color

sealed trait RogFont {
    def draw(char: Char)(x: Int, y: Int): Graphics2D => Unit
    def draw(str: String)(x: Int, y: Int): Graphics2D => Unit
}

case class TruetypeRogFont(name: String, size: Int) extends RogFont {    
    val fontFile = Rog.getClass.getResourceAsStream(name);
    val textFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
    val derivedFont = textFont.deriveFont(size.toFloat)

    def draw(char: Char)(x: Int, y: Int): Graphics2D => Unit = {
        (graphics: Graphics2D) => {
            graphics.setFont(derivedFont)
            graphics.drawString(char.toString(), x.toFloat + 3, y.toFloat + 13)
        }
    }

    def draw(str: String)(x: Int, y: Int): Graphics2D => Unit = {
        (graphics: Graphics2D) => {
            graphics.drawString(str, x.toFloat, y.toFloat)
        }
    }
}

case class BitmapRogFont(name: String, columns: Int, symWidth: Int, symHeight: Int) extends RogFont {
    val bufferedImage = ImageIO.read(Rog.getClass().getResourceAsStream(name))
    val (imageWidth, imageHeight) = (bufferedImage.getWidth(), bufferedImage.getHeight());
    def draw(char: Char)(x: Int, y: Int): Graphics2D => Unit = {
        (graphics: Graphics2D) => {
            val dx = char.toInt % columns
            val dy = char.toInt / columns
            val srcRect = (symWidth * dx, symHeight * dy, symWidth * (dx + 1), symHeight * (dy + 1))
            val dstRect = (x, y, x + symWidth, y + symHeight)
            graphics.drawImage(bufferedImage,
                x, y, x + symWidth, y + symHeight, 
                symWidth * dx, symHeight * dy, symWidth * (dx + 1), symHeight * (dy + 1), null)
        }
    }
    
    def draw(str: String)(x: Int, y: Int): Graphics2D => Unit = {
        (graphics: Graphics2D) => {
            str.toCharArray().zipWithIndex.foreach { case (c: Char, i: Int) => {
                draw(c)(x + symWidth * i, y)(graphics)
            }}            
        }
    }
}
