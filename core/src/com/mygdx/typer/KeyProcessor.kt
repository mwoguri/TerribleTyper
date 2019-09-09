package com.mygdx.typer

import com.badlogic.gdx.Input.Keys.*
import com.badlogic.gdx.InputProcessor

class KeyProcessor(val clock: Clock = RealClock) : InputProcessor {
    companion object {
        const val HOMEROW = -1
        // must hit at least 4 keys in homerow to be considered a homerow hit
        val HOMEROW_KEYS = setOf(A, S, D, F, J, K, L, SEMICOLON)
        const val HALF_SECOND = 0.5f
        const val NANOS_PER_SECOND = 1000000000
        const val TIMEOUT = HALF_SECOND * NANOS_PER_SECOND
    }

    val previousKeys: HashMap<Int, Long> = HashMap()

    val TAG = "KeyProcessor"
    private var moveListener: MoveListener? = null
    private var target = HOMEROW


    fun setMoveListener(moveListener: MoveListener) {
        this.moveListener = moveListener
    }

    fun setKey(keycodeTarget: Int = HOMEROW) {
        this.target = keycodeTarget
    }

    override fun keyDown(keycode: Int): Boolean {
        if (target == HOMEROW && keycode in HOMEROW_KEYS) {
            previousKeys[keycode] = clock.nanoTime()
            if (homerowHit()) {
                moveListener?.jump()
            }
        } else if (keycode == target) {
            moveListener?.jump()
        }
        return true
    }

    private fun homerowHit(): Boolean {
        val currentTime = clock.nanoTime()
        return previousKeys.filterValues {
            currentTime - it < TIMEOUT
        }.size >= 4
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

    override fun keyTyped(character: Char): Boolean {
        return true
    }

    override fun scrolled(amount: Int): Boolean {
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