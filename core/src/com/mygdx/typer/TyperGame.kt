package com.mygdx.typer

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.mygdx.typer.entities.Player

class TyperGame : ApplicationAdapter() {
    private lateinit var batch: SpriteBatch
    private lateinit var assets: Assets
    private lateinit var player: Player

    override fun create() {
        batch = SpriteBatch()
        assets = Assets(AssetManager())
        assets.init()
        player = Player(assets)
    }

    override fun render() {
        Gdx.gl.glClearColor(.7f, .7f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        player.render(batch)
    }

    override fun dispose() {
        batch.dispose()
    }
}
