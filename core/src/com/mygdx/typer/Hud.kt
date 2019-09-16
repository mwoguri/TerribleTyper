package com.mygdx.typer

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.mygdx.typer.util.Constants

class Hud(val font: BitmapFont, val viewport: ExtendViewport) {

    fun render(batch: SpriteBatch, score: Int) {
        viewport.apply()
        batch.projectionMatrix = viewport.camera.combined
        batch.begin()
        font.draw(batch, "Score: $score", Constants.HUD_MARGIN, viewport.worldHeight - Constants.HUD_MARGIN)
        batch.end()
    }
}