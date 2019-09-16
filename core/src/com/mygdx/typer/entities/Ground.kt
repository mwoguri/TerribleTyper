package com.mygdx.typer.entities

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.mygdx.typer.Assets
import com.mygdx.typer.util.Constants
import com.mygdx.typer.util.DrawUtils

class Ground(private val assets: Assets,
             val position: Vector2) {

    val width = 2402
    val height = 219

    fun render(batch: SpriteBatch) {
        batch.begin()
        DrawUtils.drawTextureRegion(batch, assets.ground, position.x, position.y)
        batch.end()
    }

    fun generateCoinLocations(numCoins: Int = 10): Array<Vector2> {
        val locations = Array<Vector2>()
        for (i in 1..numCoins) {
            locations.add(Vector2(position.x+width/numCoins * i, position.y+height+Constants.COIN_ALTITUDE))
        }
        return locations
    }
}