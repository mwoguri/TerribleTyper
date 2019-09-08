package com.mygdx.typer.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.TimeUtils
import com.mygdx.typer.Assets
import com.mygdx.typer.util.Constants
import com.mygdx.typer.util.Constants.FORWARD_VELOCITY
import com.mygdx.typer.util.DrawUtils

class Player(private val assets: Assets,
             val position: Vector2 = Vector2(100f, 300f),
             val previousPosition: Vector2 = Vector2(position),
             private val velocity: Vector2 = Vector2(FORWARD_VELOCITY, 0f),
             private var state: State = State.AIRBORNE) {
    private val walkStartTime: Long = TimeUtils.nanoTime()


    fun update(delta: Float, ground: Ground) {
        if (state == State.AIRBORNE) {
            velocity.y -= Constants.GRAVITY * delta
        }
        if (state == State.GROUNDED) {
            velocity.y = 0f
        }

        //Gdx.app.log("position", "${position.x}, ${position.y}")
        previousPosition.set(position)
        position.mulAdd(velocity, delta)

        if (landed(ground)){
            //Gdx.app.log("state", "grounded")
            state = State.GROUNDED
            position.y = ground.position.y + ground.height
        } else {
            state = State.AIRBORNE
        }
    }

    fun render(batch: SpriteBatch) {

        batch.begin()
        val walkTimeSeconds = TimeUtils.timeSinceNanos(walkStartTime) * MathUtils.nanoToSec
        val keyFrame = assets.walk.getKeyFrame(walkTimeSeconds)
        DrawUtils.drawTextureRegion(batch, keyFrame, position.x, position.y)
        batch.end()
    }


    internal fun landed(ground: Ground): Boolean {
        val groundTop = ground.position.y + ground.height
        if (previousPosition.y >= groundTop && position.y <= groundTop) {
            val isRightEnough = position.x < ground.position.x + ground.width
            val isLeftEnough = position.x + Constants.PLAYER_WIDTH > ground.position.x
            return isRightEnough && isLeftEnough
        }
        return false
    }

    enum class State {
        AIRBORNE, GROUNDED
    }

}