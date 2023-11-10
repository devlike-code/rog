package rog.ini

import java.nio.file.{Path, Paths}
import scala.io.Source
import rog.Rog

object IniParser {
    def loadFromResources(path: String): Map[String, Map[String, String]] = {
        val source = Source.fromFile(Rog.getClass().getResource(path).getFile())
        val lines = source.getLines().toList
        source.close
        parse(lines)
    }
        
    def loadFromPath(pathString: String): Map[String, Map[String, String]] =
        loadFromPath(Paths.get(pathString))
    
    def loadFromPath(path: Path): Map[String, Map[String, String]] = {
        val source = Source.fromFile(path.toFile)
        val lines = source.getLines().toList
        source.close
        parse(lines)
    }
    
    def parse(lines: List[String]): Map[String, Map[String, String]] = {
        
        def innerParse(lines: List[String], section: String, acc: Map[String, Map[String, String]]): Map[String, Map[String, String]] = {
            def isComment(s: String): Boolean = s.startsWith(";") || s.startsWith("#")
            
            def isDataStart(s: String): Boolean = s.startsWith("<")
            
            def isSection(s: String): Boolean = s.startsWith("[")
            
            def trimSection(s: String): String = s.replaceAll("^\\[", "").replaceAll("\\]$", "")
            
            def keyValue(s: String): Option[(String, String)] = {
                val Pattern = "(.*)=(.*)".r
                s.trim() match {
                    case Pattern(k, v) => Some(k.trim, v.trim)
                    case f => None
                }
            }
            
            lines match {
                case Nil => acc
                case x :: xs =>                
                    val newAcc: Map[String, Map[String, String]] = if (acc.isEmpty) acc + ("" -> Map.empty) else acc
                
                    if (isDataStart(x)) acc                
                    else if (isComment(x)) innerParse(xs, section, newAcc + (section -> (newAcc(section) ++ Map(x -> "#"))))
                    else if (x.isEmpty) innerParse(xs, section, newAcc)            
                    else if (isSection(x))
                        innerParse(xs, trimSection(x), newAcc + (trimSection(x) -> Map.empty))
                    else {
                        keyValue(x) match {
                            case Some((key, value)) => innerParse(xs, section, newAcc + (section -> (newAcc(section) ++ Map(key -> value))))
                            case _ => innerParse(xs, section, newAcc)
                        }                    
                    }
            }
        }
        
        innerParse(lines, "", Map.empty)
    }
}