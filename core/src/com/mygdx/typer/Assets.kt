package com.mygdx.typer

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetErrorListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureAtlas.*
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode

class Assets (private val assetManager: AssetManager) : AssetErrorListener {
    companion object {
        const val TAG = "assets"
    }
    lateinit var walk : Animation<AtlasRegion>

    fun init(){
        assetManager.setErrorListener(this)
        assetManager.load<TextureAtlas>("sprite.atlas", TextureAtlas::class.java)
        assetManager.finishLoading()
        val atlas:TextureAtlas = assetManager.get("sprite.atlas")
        val walk1 = atlas.findRegion("walk-1")
        val walk2 = atlas.findRegion("walk-2")
        val walk3 = atlas.findRegion("walk-3")
        val walk4 = atlas.findRegion("walk-4")

        val walkFrames: Array<AtlasRegion> = Array<AtlasRegion>()
        walkFrames.add(walk1)
        walkFrames.add(walk2)
        walkFrames.add(walk3)
        walkFrames.add(walk4)

        walk = Animation(.15f, walkFrames, PlayMode.LOOP)
    }


    override fun error(asset: AssetDescriptor<*>?, throwable: Throwable?) {
        Gdx.app.log(TAG, "error: ${throwable?.localizedMessage}")
    }
}
