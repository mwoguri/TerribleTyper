package com.mygdx.typer

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.DelayedRemovalArray
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.mygdx.typer.entities.*
import com.mygdx.typer.util.Constants
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlin.random.Random

class Level(private val keyProcessor: KeyProcessor) :
        InputProcessor by keyProcessor,
        MoveListener {
    private var maxPossibleScore = 1
    private lateinit var batch: SpriteBatch
    private lateinit var assets: Assets
    private lateinit var player: Player
    private lateinit var grounds: Array<Ground>
    private lateinit var backgrounds: Array<Background>
    private lateinit var coins: DelayedRemovalArray<Coin>
    private lateinit var viewport: Viewport
    private lateinit var hud: Hud
    private lateinit var font: BitmapFont
    private var chest: Chest? = null
    private var score = 0

    override fun success() {
        player.jump()
    }

    override fun error() {
        score -= Constants.WRONG_KEY_PENALTY
    }

    fun show() {
        assets = Assets(AssetManager())
        viewport = ExtendViewport(Constants.WORLD_SIZE_X, Constants.WORLD_SIZE_Y)

        batch = SpriteBatch()
        assets.init()
        font = BitmapFont(Gdx.files.internal("80.fnt"), false)
        font.data.scale(-0.3f)
        font.setColor(Color.BLACK)
        hud = Hud(font, ExtendViewport(500f, 500f))
        coins = DelayedRemovalArray<Coin>()
        keyProcessor.setCoins(coins)
        val levelParser = LevelParser(Json(JsonConfiguration(strictMode = false)))
        val level = levelParser.parse(Gdx.files.internal("MainScene.json").readString())
        grounds = Array<Ground>()
        backgrounds = Array<Background>()
        for (image: Image in level.composite.sImages) {
            when {
                image.imageName == Constants.BACKGROUND -> {
                    val background = Background(assets, Vector2(image.x * Constants.PIXELS_PER_INCH, image.y * Constants.PIXELS_PER_INCH))
                    backgrounds.add(background)
                }
                image.imageName == Constants.LAND -> {
                    val noCoins = image.tags?.contains(Constants.EMPTY) ?: false
                    grounds.add(Ground(assets, Vector2(image.x * Constants.PIXELS_PER_INCH, image.y * Constants.PIXELS_PER_INCH), !noCoins))
                }
                image.imageName == Constants.EMPTY_CHEST -> chest = Chest(assets, Vector2(image.x * Constants.PIXELS_PER_INCH, image.y* Constants.PIXELS_PER_INCH))
            }
        }
        player = Player(assets)
        Gdx.input.inputProcessor = this
        keyProcessor.setMoveListener(this)
        spawn()
    }

    fun render(delta: Float) {
        viewport.apply()


        batch.projectionMatrix = viewport.camera.combined

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        player.update(delta, grounds)
        if (player.isDead()) {
            spawn()
        }

        if (isAtEndOfLevel()){
            player.velocity.x = 0f

            if (score/maxPossibleScore.toFloat() > .8) {
                chest?.isFull = true
            }
        }
        keyProcessor.update(player.position)

        val hitCoins = player.hitCoins(coins)
        coins.begin()
        for (coin in hitCoins) {
            coins.removeValue(coin, false)
            score += Constants.COIN_VALUE
            Gdx.app.log("coins", "hit!: $score")
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

        chest?.render(batch)

        viewport.camera.position.x = player.position.x + Constants.CAMERA_OFFSET_X
        viewport.camera.position.y = 400f

        //render HUD last so it's on top
        hud.render(batch, score)
    }

    private fun isAtEndOfLevel(): Boolean {
        return player.position.x + Constants.PLAYER_WIDTH >= chest?.position?.x ?: 0f
    }

    fun resize(width: Int, height: Int) {
        viewport.update(width, height)
        hud.viewport.update(width, height, true)

    }

    fun dispose() {
        batch.dispose()
        font.dispose()
    }

    private fun generateCoins() {
        for (ground in grounds) {
            if (ground.coins) {
                val numCoins: Int = (Math.random() * 5f).toInt() + 5
                for (position in ground.generateCoinLocations(numCoins)) {
                    val target = if (Random.nextBoolean()) KeyProcessor.HOMEROW else 'j'
                    coins.add(Coin(target, assets, font, position))
                }
            }
        }
    }

    private fun spawn() {
        player.position.set(100f, 430f)
        player.velocity.y = 0f
        score = 0
        coins.clear()
        generateCoins()
        maxPossibleScore = getMaxScore(coins)
    }

    private fun getMaxScore(coins: DelayedRemovalArray<Coin>): Int {
        return coins.size * Constants.COIN_VALUE
    }

}