package com.mygdx.typer

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.DelayedRemovalArray
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.mygdx.typer.entities.Coin
import com.mygdx.typer.entities.Ground
import com.mygdx.typer.entities.Player
import com.mygdx.typer.util.Constants

class GameScreen(val keyProcessor: KeyProcessor) : ScreenAdapter(),
        InputProcessor by keyProcessor,
        MoveListener {

    override fun jump() {
        player.jump()
    }

    private lateinit var batch: SpriteBatch
    private lateinit var assets: Assets
    private lateinit var player: Player
    private lateinit var grounds: Array<Ground>
    private lateinit var coins: DelayedRemovalArray<Coin>
    private lateinit var viewport: Viewport
    private lateinit var font: BitmapFont

    override fun show() {
        super.show()
        viewport = ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE)
        batch = SpriteBatch()
        assets = Assets(AssetManager())
        assets.init()
        player = Player(assets)
        grounds = Array<Ground>()
        grounds.add(Ground(assets, Vector2(10f, 10f), 800f, 200f))
        grounds.add(Ground(assets, Vector2(1100f, 80f), 900f, 200f))
        grounds.add(Ground(assets, Vector2(2300f, 10f), 200f, 200f))
        grounds.add(Ground(assets, Vector2(2800f, 10f), 20000f, 200f))
        font = BitmapFont()
        font.data.setScale(10f)
        coins = DelayedRemovalArray<Coin>()
        coins.add(Coin(KeyProcessor.HOMEROW, assets, font, Vector2(900f, 900f)))
        coins.add(Coin(KeyProcessor.HOMEROW, assets, font, Vector2(500f, 200f)))
        coins.add(Coin(Keys.A, assets, font, Vector2(2300f, 900f)))
        coins.add(Coin(KeyProcessor.HOMEROW, assets, font, Vector2(2800f, 900f)))
        coins.add(Coin(KeyProcessor.HOMEROW, assets, font, Vector2(3200f, 900f)))
        coins.add(Coin(Keys.A, assets, font, Vector2(3900f, 900f)))
        coins.add(Coin(Keys.A, assets, font, Vector2(4500f, 900f)))
        coins.add(Coin(Keys.S, assets, font, Vector2(5600f, 900f)))
        coins.add(Coin(Keys.S, assets, font, Vector2(6600f, 900f)))
        coins.add(Coin(KeyProcessor.HOMEROW, assets, font, Vector2(7200f, 900f)))

        Gdx.input.inputProcessor = this
        keyProcessor.setMoveListener(this)
    }

    fun spawn() {
        player.position.set(100f, 600f)
    }

    override fun render(delta: Float) {

        viewport.apply()
        batch.projectionMatrix = viewport.camera.combined

        super.render(delta)
        Gdx.gl.glClearColor(.7f, .7f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)


        player.update(delta, grounds)
        if (player.isDead()) {
            spawn()
        }


        val hitCoins = player.hitCoins(coins)
        coins.begin()
        for (coin in hitCoins) {
            coins.removeValue(coin, false)
            Gdx.app.log("coins", "hit!")
        }
        coins.end()



        for (ground in grounds) {
            ground.render(batch)
        }
        player.render(batch)
        for (coin in coins) {
            coin.render(batch)
        }

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