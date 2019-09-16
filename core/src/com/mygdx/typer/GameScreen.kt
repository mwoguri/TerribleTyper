package com.mygdx.typer

import com.badlogic.gdx.ScreenAdapter


class GameScreen : ScreenAdapter() {

    private val level = Level(KeyProcessor())

    override fun show() {
        super.show()
        level.show()
    }


    override fun render(delta: Float) {
        super.render(delta)
        level.render(delta)
    }


    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        level.resize(width, height)
    }

    override fun dispose() {
        super.dispose()
        level.dispose()
    }
}