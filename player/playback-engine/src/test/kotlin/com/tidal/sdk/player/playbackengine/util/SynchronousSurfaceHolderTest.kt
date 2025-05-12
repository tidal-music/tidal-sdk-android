package com.tidal.sdk.player.playbackengine.util

import android.graphics.Canvas
import android.graphics.Rect
import android.view.Surface
import android.view.SurfaceHolder
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isSameAs
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

internal class SynchronousSurfaceHolderTest {

    private val delegate = mock<SurfaceHolder>()
    private val synchronousSurfaceHolderCallbackFactory =
        mock<SynchronousSurfaceHolderCallback.Factory>()
    private val synchronousSurfaceHolder =
        SynchronousSurfaceHolder(delegate, synchronousSurfaceHolderCallbackFactory)

    @AfterEach
    fun afterEach() = verifyNoMoreInteractions(delegate, synchronousSurfaceHolderCallbackFactory)

    @Test
    fun addNullCallback() {
        synchronousSurfaceHolder.addCallback(null)

        assertThat(synchronousSurfaceHolder.reflectionCallbackMappings).isEmpty()
    }

    @Test
    fun addNonNullCallback() {
        val callback = mock<SurfaceHolder.Callback>()
        val mapping = mock<SynchronousSurfaceHolderCallback>()
        whenever(synchronousSurfaceHolderCallbackFactory.create(callback)) doReturn mapping

        synchronousSurfaceHolder.addCallback(callback)

        verify(synchronousSurfaceHolderCallbackFactory).create(callback)
        verify(delegate).addCallback(mapping)
        with(synchronousSurfaceHolder.reflectionCallbackMappings.entries.single()) {
            assertThat(key).isSameAs(callback)
            assertThat(value).isSameAs(mapping)
        }
        verifyNoMoreInteractions(callback, mapping)
    }

    @Test
    fun removeNullCallback() {
        synchronousSurfaceHolder.removeCallback(null)

        assertThat(synchronousSurfaceHolder.reflectionCallbackMappings).isEmpty()
    }

    @Test
    fun removeNonNullMissingCallback() {
        val callback = mock<SurfaceHolder.Callback>()
        val mapping = mock<SynchronousSurfaceHolderCallback>()

        synchronousSurfaceHolder.removeCallback(callback)

        assertThat(synchronousSurfaceHolder.reflectionCallbackMappings).isEmpty()
        verifyNoMoreInteractions(callback, mapping)
    }

    @Test
    fun removeNonNullPresentCallback() {
        val callback = mock<SurfaceHolder.Callback>()
        val mapping = mock<SynchronousSurfaceHolderCallback>()
        synchronousSurfaceHolder.reflectionCallbackMappings[callback] = mapping

        synchronousSurfaceHolder.removeCallback(callback)

        assertThat(synchronousSurfaceHolder.reflectionCallbackMappings).isEmpty()
        verify(delegate).removeCallback(mapping)
        verifyNoMoreInteractions(callback, mapping)
    }

    @Test
    fun isCreating() {
        val expected = true
        whenever(delegate.isCreating) doReturn expected

        val actual = synchronousSurfaceHolder.isCreating

        verify(delegate).isCreating
        assertThat(actual).isEqualTo(expected)
        assertThat(synchronousSurfaceHolder.reflectionCallbackMappings).isEmpty()
    }

    @Test
    fun setType() {
        val type = 99

        synchronousSurfaceHolder.setType(type)

        verify(delegate).setType(type)
        assertThat(synchronousSurfaceHolder.reflectionCallbackMappings).isEmpty()
    }

    @Test
    fun setFixedSize() {
        val width = 1
        val height = -56

        synchronousSurfaceHolder.setFixedSize(width, height)

        verify(delegate).setFixedSize(width, height)
        assertThat(synchronousSurfaceHolder.reflectionCallbackMappings).isEmpty()
    }

    @Test
    fun setSizeFromLayout() {
        synchronousSurfaceHolder.setSizeFromLayout()

        verify(delegate).setSizeFromLayout()
        assertThat(synchronousSurfaceHolder.reflectionCallbackMappings).isEmpty()
    }

    @Test
    fun setFormat() {
        val format = -837

        synchronousSurfaceHolder.setFormat(format)

        verify(delegate).setFormat(format)
        assertThat(synchronousSurfaceHolder.reflectionCallbackMappings).isEmpty()
    }

    @Test
    fun setKeepScreenOn() {
        val screenOn = true

        synchronousSurfaceHolder.setKeepScreenOn(screenOn)

        verify(delegate).setKeepScreenOn(screenOn)
        assertThat(synchronousSurfaceHolder.reflectionCallbackMappings).isEmpty()
    }

    @Test
    fun lockCanvas() {
        val expected = mock<Canvas>()
        whenever(delegate.lockCanvas()) doReturn expected

        val actual = synchronousSurfaceHolder.lockCanvas()

        verify(delegate).lockCanvas()
        assertThat(actual).isSameAs(expected)
        verifyNoMoreInteractions(expected)
        assertThat(synchronousSurfaceHolder.reflectionCallbackMappings).isEmpty()
    }

    @Test
    fun lockCanvasWithParameter() {
        val dirty = mock<Rect>()
        val expected = mock<Canvas>()
        whenever(delegate.lockCanvas(dirty)) doReturn expected

        val actual = synchronousSurfaceHolder.lockCanvas(dirty)

        verify(delegate).lockCanvas(dirty)
        assertThat(actual).isSameAs(expected)
        verifyNoMoreInteractions(dirty, expected)
        assertThat(synchronousSurfaceHolder.reflectionCallbackMappings).isEmpty()
    }

    @Test
    fun unlockCanvasAndPost() {
        val canvas = mock<Canvas>()

        synchronousSurfaceHolder.unlockCanvasAndPost(canvas)

        verify(delegate).unlockCanvasAndPost(canvas)
        verifyNoMoreInteractions(canvas)
        assertThat(synchronousSurfaceHolder.reflectionCallbackMappings).isEmpty()
    }

    @Test
    fun getSurfaceFrame() {
        val expected = mock<Rect>()
        whenever(delegate.surfaceFrame) doReturn expected

        val actual = synchronousSurfaceHolder.surfaceFrame

        verify(delegate).surfaceFrame
        assertThat(actual).isSameAs(expected)
        verifyNoMoreInteractions(expected)
        assertThat(synchronousSurfaceHolder.reflectionCallbackMappings).isEmpty()
    }

    @Test
    fun getSurface() {
        val expected = mock<Surface>()
        whenever(delegate.surface) doReturn expected

        val actual = synchronousSurfaceHolder.surface

        verify(delegate).surface
        assertThat(actual).isSameAs(expected)
        verifyNoMoreInteractions(expected)
        assertThat(synchronousSurfaceHolder.reflectionCallbackMappings).isEmpty()
    }
}
