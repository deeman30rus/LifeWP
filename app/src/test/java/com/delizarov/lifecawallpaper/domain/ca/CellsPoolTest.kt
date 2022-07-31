package com.delizarov.lifecawallpaper.domain.ca

import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

private val DEFAULT_CAPACITY = 10
private val EXTENDED_CAPACITY = 20

// Заюзать Strikt

class CellsPoolTest {

    private val underTest = CellsPool(DEFAULT_CAPACITY)

    @Test
    fun `obtain returns new object`() {
        val cell = underTest.obtain()

        expectThat(underTest.size)
            .isEqualTo(1)

        expectThat(underTest.capacity)
            .isEqualTo(DEFAULT_CAPACITY)
    }

    @Test
    fun `obtain with parameters returns specified object`() {

    }

    @Test
    fun `release obtained object`() {

    }

    @Test
    fun `you cant exceed given capacity`() {

    }

    @Test
    fun `reallocate capacity`() {

    }

    @Test
    fun `you cant reallocated while some objects are used`() {

    }
}