package com.mygdx.typer.entities

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.TimeUtils
import com.mygdx.typer.Assets
import com.mygdx.typer.KeyProcessor
import com.mygdx.typer.util.Constants
import com.mygdx.typer.util.DrawUtils


class Coin(val target: Char,
           private val assets: Assets,
           private val font: BitmapFont,
           val position: Vector2) {

    private val coinStartTime = TimeUtils.nanoTime()
    //TODO fix this
    val boundingBox = Rectangle(position.x, position.y, 60f, 60f)

    fun render(batch: SpriteBatch) {
        val coinTime = TimeUtils.timeSinceNanos(coinStartTime) * MathUtils.nanoToSec
        val region = assets.coin.getKeyFrame(coinTime)

        batch.begin()
        DrawUtils.drawTextureRegion(batch, region, position.x, position.y, 60f, 60f)

        if (target == KeyProcessor.HOMEROW) {
            batch.draw(assets.home, position.x + Constants.HOME_ICON_OFFSET, position.y + Constants.HOME_ICON_OFFSET)
        } else {
            font.draw(batch, target.toString(), position.x + Constants.COIN_OFFSET, position.y + 50)
        }
        batch.end()
    }
}