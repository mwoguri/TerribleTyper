package com.mygdx.typer.entities

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.mygdx.typer.Assets
import com.mygdx.typer.util.DrawUtils

class Ground(private val assets: Assets,
             val position: Vector2,
             val width: Float,
             val height: Float) {

    fun render(batch: SpriteBatch) {
        batch.begin()
        DrawUtils.drawTextureRegion(batch, assets.ground, position.x, position.y, width, height)

        batch.end()
    }
}