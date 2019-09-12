package com.mygdx.typer

import com.badlogic.gdx.Input.Keys
import com.mygdx.typer.KeyProcessor.Companion.HOMEROW
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*

val ONE_SECOND = 1000000000L

class KeyProcessorTest {

    private val listener: MoveListener = Mockito.mock(MoveListener::class.java)
    private val fakeClock: Clock = Mockito.mock(Clock::class.java)

    @Before
    fun setUp() {

    }

    @Test
    fun hitting4HomeRow_shouldNotTriggerJump_whenTooSlow() {
        val keyProcessor = KeyProcessor(fakeClock)
        keyProcessor.setMoveListener(listener)
        keyProcessor.setKey(HOMEROW)

        `when`(fakeClock.nanoTime()).thenReturn(0)
        keyProcessor.keyDown(Keys.A)
        keyProcessor.keyDown(Keys.S)
        keyProcessor.keyDown(Keys.D)
        `when`(fakeClock.nanoTime()).thenReturn(ONE_SECOND)
        keyProcessor.keyDown(Keys.F)
        verify(listener, times(0)).jump()
    }

    @Test
    fun hittingHomeRow_shouldTriggerJump_whenHomeRowIsTarget() {
        val keyProcessor = KeyProcessor()
        keyProcessor.setMoveListener(listener)
        keyProcessor.setKey(HOMEROW)
        KeyProcessor.HOMEROW_KEYS.forEach { keyProcessor.keyDown(it) }
        Mockito.verify(listener, atLeast(1)).jump()
    }

    @Test
    fun hitting3HomeRowKeys_shouldNotTriggerJump_whenHomeRowIsTarget() {
        val keyProcessor = KeyProcessor()
        keyProcessor.setMoveListener(listener)
        keyProcessor.setKey(HOMEROW)
        keyProcessor.keyDown(Keys.A)
        keyProcessor.keyDown(Keys.S)
        keyProcessor.keyDown(Keys.D)
        Mockito.verify(listener, times(0)).jump()
    }

    @Test
    fun hittingB_shouldTriggerJump_whenBIsTarget() {
        val keyProcessor = KeyProcessor()
        keyProcessor.setMoveListener(listener)
        keyProcessor.setKey(Keys.B)
        keyProcessor.keyDown(Keys.B)
        Mockito.verify(listener, times(1)).jump()
    }

    @Test
    fun hittingB_shouldNotTriggerJump_whenAIsTarget() {
        val keyProcessor = KeyProcessor()
        keyProcessor.setMoveListener(listener)
        keyProcessor.setKey(Keys.A)
        keyProcessor.keyDown(Keys.B)
        Mockito.verify(listener, times(0)).jump()
    }
}