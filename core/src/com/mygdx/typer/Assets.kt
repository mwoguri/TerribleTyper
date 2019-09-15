package com.mygdx.typer

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetErrorListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Array
import com.mygdx.typer.util.Constants
import com.mygdx.typer.util.Constants.JUMP_ATLAS
import com.mygdx.typer.util.Constants.MAIN_SCENE_ATLAS
import com.mygdx.typer.util.Constants.WALK_ATLAS

class Assets(private val assetManager: AssetManager) : AssetErrorListener {
    companion object {
        const val TAG = "assets"
    }

    lateinit var coin: Animation<AtlasRegion>
    lateinit var walk: Animation<AtlasRegion>
    lateinit var jump: TextureRegion
    lateinit var fall: TextureRegion

    lateinit var ground: TextureRegion
    lateinit var background: TextureRegion
    lateinit var home: Texture //TODO put in a spritesheet

    fun init() {
        assetManager.setErrorListener(this)
        home = Texture(Gdx.files.internal("home.png"));
        assetManager.load<TextureAtlas>(WALK_ATLAS, TextureAtlas::class.java)
        assetManager.load<TextureAtlas>(JUMP_ATLAS, TextureAtlas::class.java)
        assetManager.load<TextureAtlas>(MAIN_SCENE_ATLAS, TextureAtlas::class.java)
        assetManager.finishLoading()
        val walkAtlas: TextureAtlas = assetManager.get(WALK_ATLAS)
        val walk1 = walkAtlas.findRegion(Constants.WALK1)
        val walk2 = walkAtlas.findRegion(Constants.WALK2)
        val walk3 = walkAtlas.findRegion(Constants.WALK3)
        val walk4 = walkAtlas.findRegion(Constants.WALK4)

        val walkFrames: Array<AtlasRegion> = Array<AtlasRegion>()
        walkFrames.add(walk1)
        walkFrames.add(walk2)
        walkFrames.add(walk3)
        walkFrames.add(walk4)

        walk = Animation(.15f, walkFrames, PlayMode.LOOP)


        val groundAtlas: TextureAtlas = assetManager.get(MAIN_SCENE_ATLAS)
        ground = groundAtlas.findRegion(Constants.LAND)
        background = groundAtlas.findRegion(Constants.BACKGROUND)

        //TODO fix coins
        val coinFrames: Array<AtlasRegion> = Array<AtlasRegion>()
        coinFrames.add(groundAtlas.findRegion(Constants.COIN1))
        coinFrames.add(groundAtlas.findRegion(Constants.COIN2))
        coinFrames.add(groundAtlas.findRegion(Constants.COIN3))
        coinFrames.add(groundAtlas.findRegion(Constants.COIN4))
        //coinFrames.add(groundAtlas.findRegion(Constants.COIN5))
        coinFrames.add(groundAtlas.findRegion(Constants.COIN6))
        coinFrames.add(groundAtlas.findRegion(Constants.COIN7))
        coinFrames.add(groundAtlas.findRegion(Constants.COIN8))
        coin = Animation(0.05f, coinFrames, PlayMode.LOOP)

        val jumpAtlas: TextureAtlas = assetManager.get(JUMP_ATLAS)
        jump = jumpAtlas.findRegion(Constants.JUMP)
        fall = jumpAtlas.findRegion(Constants.FALL)

    }


    override fun error(asset: AssetDescriptor<*>?, throwable: Throwable?) {
        Gdx.app.log(TAG, "error: ${throwable?.localizedMessage}")
    }
}
