package com.mygdx.typer.entities

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.TimeUtils
import com.mygdx.typer.Assets
import com.mygdx.typer.util.DrawUtils

class Player(val assets: Assets) {
    private val walkStartTime: Long = TimeUtils.nanoTime()

    fun render(batch: SpriteBatch){
        batch.begin()
        val walkTimeSeconds = TimeUtils.timeSinceNanos(walkStartTime)* MathUtils.nanoToSec
        val keyFrame = assets.walk.getKeyFrame(walkTimeSeconds)
        DrawUtils.drawTextureRegion(batch, keyFrame, 0f, 0f)
        batch.end()
    }


}