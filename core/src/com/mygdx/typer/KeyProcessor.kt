package com.mygdx.typer

import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.DelayedRemovalArray
import com.mygdx.typer.entities.Coin

class KeyProcessor(val clock: Clock = RealClock) : InputProcessor {
    companion object {
        const val HOMEROW = '*'
        const val MIN_KEYS_HIT = 4
        // must hit at least 4 keys in homerow to be considered a homerow hit
        val HOMEROW_KEYS = setOf('a', 's', 'd', 'f', 'j', 'k', 'l', ';')
        const val HALF_SECOND = 0.5f
        const val NANOS_PER_SECOND = 1000000000
        const val TIMEOUT = HALF_SECOND * NANOS_PER_SECOND
    }

    val previousKeys: HashMap<Char, Long> = HashMap()

    val TAG = "KeyProcessor"
    private var coins: DelayedRemovalArray<Coin>? = null
    private var moveListener: MoveListener? = null
    private var target = HOMEROW


    fun setMoveListener(moveListener: MoveListener) {
        this.moveListener = moveListener
    }

    fun setKey(target: Char = HOMEROW) {
        this.target = target
    }

    override fun keyTyped(character: Char): Boolean {
        if (target == HOMEROW && character in HOMEROW_KEYS) {
            previousKeys[character] = clock.nanoTime()
            if (homerowHit()) {
                moveListener?.success()
            }
        } else if(target == HOMEROW){
            moveListener?.error()
        } else if (character == target) {
            moveListener?.success()
        } else {
            moveListener?.error()
        }
        return true
    }

    private fun homerowHit(): Boolean {
        val currentTime = clock.nanoTime()
        return previousKeys.filterValues {
            currentTime - it < TIMEOUT
        }.size >= MIN_KEYS_HIT
    }

    fun setCoins(coins: DelayedRemovalArray<Coin>) {
        this.coins = coins
    }

    fun update(playerPosition: Vector2) {
        val nextCoin: Coin? = coins?.filter { it.position.x > playerPosition.x }
                ?.sortedWith(compareBy { it.position.x })
                ?.firstOrNull()
        target = nextCoin?.target ?: HOMEROW
    }

    ///////////////////////////////////////////////////////////////
    // Unused overrides
    ///////////////////////////////////////////////////////////////
    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return true
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return true
    }

    override fun scrolled(amount: Int): Boolean {
        return true
    }


    override fun keyDown(keycode: Int): Boolean {
        return true
    }

    override fun keyUp(keycode: Int): Boolean {
        return true
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return true
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        return true
    }
}