package com.mygdx.typer

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetErrorListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Array
import com.mygdx.typer.util.Constants
import com.mygdx.typer.util.Constants.GROUND_ATLAS
import com.mygdx.typer.util.Constants.JUMP_ATLAS
import com.mygdx.typer.util.Constants.MAIN_SCENE_ATLAS
import com.mygdx.typer.util.Constants.WALK_ATLAS

class Assets(private val assetManager: AssetManager) : AssetErrorListener {
    companion object {
        const val TAG = "assets"
    }

    lateinit var walk: Animation<AtlasRegion>
    lateinit var jump: TextureRegion
    lateinit var fall: TextureRegion

    lateinit var ground: TextureRegion
    lateinit var background: TextureRegion

    fun init() {
        assetManager.setErrorListener(this)

        assetManager.load<TextureAtlas>(WALK_ATLAS, TextureAtlas::class.java)
        assetManager.load<TextureAtlas>(GROUND_ATLAS, TextureAtlas::class.java)
        assetManager.load<TextureAtlas>(JUMP_ATLAS, TextureAtlas::class.java)
        assetManager.load<TextureAtlas>(MAIN_SCENE_ATLAS, TextureAtlas::class.java)
        assetManager.finishLoading()
        val atlas: TextureAtlas = assetManager.get(WALK_ATLAS)
        val walk1 = atlas.findRegion(Constants.WALK1)
        val walk2 = atlas.findRegion(Constants.WALK2)
        val walk3 = atlas.findRegion(Constants.WALK3)
        val walk4 = atlas.findRegion(Constants.WALK4)

        val walkFrames: Array<AtlasRegion> = Array<AtlasRegion>()
        walkFrames.add(walk1)
        walkFrames.add(walk2)
        walkFrames.add(walk3)
        walkFrames.add(walk4)

        walk = Animation(.15f, walkFrames, PlayMode.LOOP)

//        val groundAtlas: TextureAtlas = assetManager.get(GROUND_ATLAS)
//        ground = groundAtlas.findRegion(Constants.GROUND)

        val groundAtlas: TextureAtlas = assetManager.get(MAIN_SCENE_ATLAS)
        ground = groundAtlas.findRegion(Constants.LAND)
        background = groundAtlas.findRegion(Constants.BACKGROUND)

        val jumpAtlas: TextureAtlas = assetManager.get(JUMP_ATLAS)
        jump = jumpAtlas.findRegion(Constants.JUMP)
        fall = jumpAtlas.findRegion(Constants.FALL)

    }


    override fun error(asset: AssetDescriptor<*>?, throwable: Throwable?) {
        Gdx.app.log(TAG, "error: ${throwable?.localizedMessage}")
    }
}
