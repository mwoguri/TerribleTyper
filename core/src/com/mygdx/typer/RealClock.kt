package com.mygdx.typer

import com.badlogic.gdx.utils.TimeUtils

object RealClock : Clock {
    override fun nanoTime(): Long {
        return TimeUtils.nanoTime()
    }
}
