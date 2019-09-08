package com.mygdx.typer

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.mygdx.typer.entities.Ground
import com.mygdx.typer.entities.Player
import com.mygdx.typer.util.Constants

class GameScreen(keyListener: KeyProcessor) : ScreenAdapter(), InputProcessor by keyListener {

    private lateinit var batch: SpriteBatch
    private lateinit var assets: Assets
    private lateinit var player: Player
    private lateinit var ground: Ground
    private lateinit var viewport: Viewport
    private lateinit var font: BitmapFont

    override fun show() {
        super.show()
        viewport = ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE)
        batch = SpriteBatch()
        assets = Assets(AssetManager())
        assets.init()
        player = Player(assets)
        ground = Ground(assets, Vector2(10f, 10f), 800f, 200f)
        font = BitmapFont()
        font.data.setScale(10f)
        Gdx.input.inputProcessor = this
    }

    override fun render(delta: Float) {

        viewport.apply()
        batch.projectionMatrix = viewport.camera.combined

        super.render(delta)
        Gdx.gl.glClearColor(.7f, .7f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)


        player.update(delta, ground)
        player.render(batch)
        ground.render(batch)

        batch.begin()
        font.draw(batch, "Welcome", 0f, 500f)
        batch.end()

        viewport.camera.position.x = player.position.x + Constants.CAMERA_OFFSET_X
        viewport.camera.position.y = 250f
    }


    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        viewport.update(width, height)
    }

    override fun dispose() {
        super.dispose()
        batch.dispose()
    }

}