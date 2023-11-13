package rog.config

import rog.Rog
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.core.`type`.TypeReference

object RogConfig {
    var loadedConfig: Option[RogConfig] = None

    def load(file: String) = {
        val mapper = JsonMapper.builder()
            .addModule(DefaultScalaModule)
            .build()
        
        try {
            loadedConfig = Some(mapper.readValue(Rog.getClass().getResourceAsStream(file),  new TypeReference[RogConfig] {}))
        } catch {
            case _: Throwable => loadedConfig = None
        }
    }

    def apply() = loadedConfig.get
}

case class RogConfig(
    @JsonProperty("input-mappings") inputMappings: Map[String, Seq[String]],
    @JsonProperty("bitmap-font") bitmapFont: Map[String, Int],
    @JsonProperty("truetype-font") truetypeFont: Map[String, Int],
    @JsonProperty("debug-flags") debugFlags: Seq[String],
)