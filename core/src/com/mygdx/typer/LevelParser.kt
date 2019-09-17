package com.mygdx.typer


import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Image(val imageName: String,
                 val x: Float = 0f,
                 val y: Float = 0f,
                 val tags: Array<String>?)

@Serializable
data class Composite(val sImages: Array<Image>)

@Serializable
data class Overlap2D(val sceneName: String, val composite: Composite)


class LevelParser(val json: Json) {

    fun parse(jsonString: String): Overlap2D {

        return json.parse(Overlap2D.serializer(), jsonString)
    }

}