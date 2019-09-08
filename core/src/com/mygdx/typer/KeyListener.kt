package com.mygdx.typer

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor

object KeyListener : InputProcessor {

    const val TAG = "KeyListener"

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        Gdx.app.log(TAG, "touchUp")
        return true
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        Gdx.app.log(TAG, "mouseMoved")
        return true
    }

    override fun keyTyped(character: Char): Boolean {
        Gdx.app.log(TAG, "keyTyped")
        return true
    }

    override fun scrolled(amount: Int): Boolean {
        Gdx.app.log(TAG, "scrolled")
        return true
    }

    override fun keyUp(keycode: Int): Boolean {
        Gdx.app.log(TAG, "keyUp")
        return true
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        Gdx.app.log(TAG, "touchDragged")
        return true
    }

    override fun keyDown(keycode: Int): Boolean {
        Gdx.app.log(TAG, "keyDown")
        return true
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        Gdx.app.log(TAG, "touchDown")
        return true
    }
}