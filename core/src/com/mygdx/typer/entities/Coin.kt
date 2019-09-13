package com.mygdx.typer.entities

import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.mygdx.typer.Assets
import com.mygdx.typer.KeyProcessor
import com.mygdx.typer.util.DrawUtils


class Coin(val target: Char,
           private val assets: Assets,
           private val font: BitmapFont,
           val position: Vector2) {

    //TODO fix this
    val boundingBox = Rectangle(position.x, position.y, 60f, 60f)

    fun render(batch: SpriteBatch) {
        batch.begin()
        DrawUtils.drawTextureRegion(batch, assets.ground, position.x, position.y, 60f, 60f)
        val toDisplay = if (target == KeyProcessor.HOMEROW) "!!" else target.toString()
        font.draw(batch, toDisplay, position.x, position.y + 50)
        batch.end()
    }
}