package com.mygdx.typer.entities

import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.mygdx.typer.Assets
import com.mygdx.typer.KeyProcessor
import com.mygdx.typer.util.DrawUtils


class Coin(private val target: Int,
           private val assets: Assets,
           private val font: BitmapFont,
           val position: Vector2) {

    //TODO fix this
    val boundingBox = Rectangle(position.x, position.y, 300f, 300f)

    fun render(batch: SpriteBatch) {
        batch.begin()
        DrawUtils.drawTextureRegion(batch, assets.ground, position.x, position.y)
        val toDisplay = if (target == KeyProcessor.HOMEROW) "!!" else Input.Keys.toString(target)
        font.draw(batch, toDisplay, position.x, position.y + 150)
        batch.end()
    }
}