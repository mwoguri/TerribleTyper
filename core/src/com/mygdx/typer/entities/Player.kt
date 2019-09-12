package com.mygdx.typer.entities

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.TimeUtils
import com.mygdx.typer.Assets
import com.mygdx.typer.util.Constants
import com.mygdx.typer.util.Constants.FORWARD_VELOCITY
import com.mygdx.typer.util.Constants.PLAYER_HEIGHT
import com.mygdx.typer.util.Constants.PLAYER_WIDTH
import com.mygdx.typer.util.DrawUtils

class Player(private val assets: Assets,
             val position: Vector2 = Vector2(100f, 300f),
             val previousPosition: Vector2 = Vector2(position),
             val velocity: Vector2 = Vector2(FORWARD_VELOCITY, 0f),
             private var state: State = State.AIRBORNE) {
    private val walkStartTime: Long = TimeUtils.nanoTime()
    private val boundaryRectangle = Rectangle(position.x, position.y, PLAYER_WIDTH, PLAYER_HEIGHT)


    fun update(delta: Float, grounds: Array<Ground>) {
        if (state == State.AIRBORNE) {
            velocity.y -= Constants.GRAVITY * delta
        }
        if (state == State.GROUNDED) {
            velocity.y = 0f
        }

        previousPosition.set(position)
        position.mulAdd(velocity, delta)
        boundaryRectangle.setPosition(position)

        state = State.AIRBORNE
        for (ground in grounds) {
            if (landed(ground)) {
                state = State.GROUNDED
                position.y = ground.position.y + ground.height
                break
            }
        }
    }

    fun render(batch: SpriteBatch) {

        var region: TextureRegion = assets.fall
        if (state == State.AIRBORNE && velocity.y >= 0) {
            region = assets.jump
        } else if (state == State.GROUNDED) {
            val walkTimeSeconds = TimeUtils.timeSinceNanos(walkStartTime) * MathUtils.nanoToSec
            region = assets.walk.getKeyFrame(walkTimeSeconds)
        }

        batch.begin()
        DrawUtils.drawTextureRegion(batch, region, position.x, position.y)
        batch.end()
    }


    private fun landed(ground: Ground): Boolean {
        val groundTop = ground.position.y + ground.height
        if (previousPosition.y >= groundTop && position.y <= groundTop) {
            val isRightEnough = position.x + Constants.PLAYER_X_OFFSET < ground.position.x + ground.width
            val isLeftEnough = position.x + PLAYER_WIDTH > ground.position.x
            return isRightEnough && isLeftEnough
        }
        return false
    }

    fun hitCoins(coins: Array<Coin>): Array<Coin> {
        val hitCoins = Array<Coin>()
        for (coin in coins) {
            if (boundaryRectangle.overlaps(coin.boundingBox)) {
                hitCoins.add(coin)
            }
        }
        return hitCoins
    }

    fun jump() {
        if (state == State.GROUNDED) { //can't jump if already airborne
            state = State.AIRBORNE
            velocity.y = Constants.JUMP_SPEED
        }
    }

    fun isDead(): Boolean {
        return position.y < Constants.KILL_PLANE
    }

    enum class State {
        AIRBORNE, GROUNDED
    }

}