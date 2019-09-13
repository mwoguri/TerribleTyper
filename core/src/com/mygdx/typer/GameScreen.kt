package com.mygdx.typer

import com.badlogic.gdx.Gdx
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
import com.mygdx.typer.entities.Background
import com.mygdx.typer.entities.Coin
import com.mygdx.typer.entities.Ground
import com.mygdx.typer.entities.Player
import com.mygdx.typer.util.Constants
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlin.random.Random


class GameScreen(val keyProcessor: KeyProcessor) : ScreenAdapter(),
        InputProcessor by keyProcessor,
        MoveListener {

    private lateinit var batch: SpriteBatch
    private lateinit var assets: Assets
    private lateinit var player: Player
    private lateinit var grounds: Array<Ground>
    private lateinit var backgrounds: Array<Background>
    private lateinit var coins: DelayedRemovalArray<Coin>
    private lateinit var viewport: Viewport
    private lateinit var font: BitmapFont


    override fun show() {
        super.show()

        assets = Assets(AssetManager())
        viewport = ExtendViewport(Constants.WORLD_SIZE_X, Constants.WORLD_SIZE_Y)
        batch = SpriteBatch()
        assets.init()
        font = BitmapFont()
        font.data.setScale(4f)
        coins = DelayedRemovalArray<Coin>()


        val levelParser = LevelParser(Json(JsonConfiguration(strictMode = false)))
        val level = levelParser.parse(Gdx.files.internal("MainScene.json").readString())
        grounds = Array<Ground>()
        backgrounds = Array<Background>()
        for (image: Image in level.composite.sImages) {
            if (image.imageName == Constants.BACKGROUND) {
                backgrounds.add(Background(assets, Vector2(image.x * Constants.PIXELS_PER_INCH, image.y * Constants.PIXELS_PER_INCH)))
            } else if (image.imageName == Constants.LAND) {
                grounds.add(Ground(assets, Vector2(image.x * Constants.PIXELS_PER_INCH, image.y * Constants.PIXELS_PER_INCH)))
            }
        }
        player = Player(assets)

        for (ground in grounds) {
            val numCoins: Int = (Math.random() * 5).toInt() + 5
            for (position in ground.generateCoinLocations(numCoins)) {
                val target = if (Random.nextBoolean()) KeyProcessor.HOMEROW else 'j'
                coins.add(Coin(target, assets, font, position))
            }
        }

        Gdx.input.inputProcessor = this
        keyProcessor.addCoins(coins)
        keyProcessor.setMoveListener(this)
    }

    override fun jump() {
        player.jump()
    }

    fun spawn() {
        player.position.set(100f, 600f)
        player.velocity.y = 0f
    }

    override fun render(delta: Float) {

        viewport.apply()
        batch.projectionMatrix = viewport.camera.combined

        super.render(delta)
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)


        player.update(delta, grounds)
        if (player.isDead()) {
            spawn()
        }
        keyProcessor.update(player.position)

        val hitCoins = player.hitCoins(coins)
        coins.begin()
        for (coin in hitCoins) {
            coins.removeValue(coin, false)
            Gdx.app.log("coins", "hit!")
        }
        coins.end()

        for (background in backgrounds) {
            background.render(batch)
        }
        for (ground in grounds) {
            ground.render(batch)
        }

        player.render(batch)
        for (coin in coins) {
            coin.render(batch)
        }


        viewport.camera.position.x = player.position.x + Constants.CAMERA_OFFSET_X
        viewport.camera.position.y = 400f
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