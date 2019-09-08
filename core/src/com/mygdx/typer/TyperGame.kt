package com.mygdx.typer

import com.badlogic.gdx.Game

class TyperGame : Game() {

    override fun create() {
        setScreen(GameScreen(KeyListener))
    }

}
