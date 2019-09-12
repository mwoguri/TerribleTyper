package com.mygdx.typer.util

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion

object DrawUtils {

    fun drawTextureRegion(batch: SpriteBatch, region: TextureRegion, x: Float, y: Float) {
        batch.draw(region.texture,
                x,
                y,
                0f,
                0f,
                region.regionWidth.toFloat(),
                region.regionHeight.toFloat(),
                1f,
                1f,
                0f,
                region.regionX,
                region.regionY,
                region.regionWidth,
                region.regionHeight,
                false,
                false)
    }

    fun drawTextureRegion(batch: SpriteBatch,
                          region: TextureRegion,
                          x: Float,
                          y: Float,
                          goalWidth: Float,
                          goalHeight: Float) {
        val xScale = goalWidth / region.regionWidth
        val yScale = goalHeight / region.regionHeight
        batch.draw(region.texture,
                x,
                y,
                0f,
                0f,
                region.regionWidth.toFloat(),
                region.regionHeight.toFloat(),
                xScale,
                yScale,
                0f,
                region.regionX,
                region.regionY,
                region.regionWidth,
                region.regionHeight,
                false,
                false)
    }
}
