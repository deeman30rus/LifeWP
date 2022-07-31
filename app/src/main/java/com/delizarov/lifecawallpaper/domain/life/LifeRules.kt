package com.delizarov.lifecawallpaper.domain.life

import com.delizarov.lifecawallpaper.domain.ca.Cell
import com.delizarov.lifecawallpaper.domain.ca.CellState
import com.delizarov.lifecawallpaper.domain.ca.Rule

object LifeRules: Rule {

    override fun calcState(current: Cell, neighbours: List<Cell>): CellState {
        val aliveNeighbours = neighbours.count { it.isAlive }

        return if (current.isAlive) {
            if (aliveNeighbours == 2 || aliveNeighbours == 3) CellState.Alive
            else CellState.Dead
        } else {
            if (aliveNeighbours == 3) CellState.Alive
            else CellState.Dead
        }
    }

    private val Cell.isAlive: Boolean
        get() = state == CellState.Alive
}