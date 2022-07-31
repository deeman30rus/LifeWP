package com.delizarov.lifecawallpaper.domain.life

import com.delizarov.lifecawallpaper.domain.ca.CellState
import com.delizarov.lifecawallpaper.domain.ca.CellularAutomaton

class LifeModel(
    universe: List<List<CellState>>
) {

    private val automaton = CellularAutomaton(
        universe = universe,
        rules = LifeRules
    )

    val rowCount: Int get() = automaton.rowCount
    val colCount: Int get() = automaton.colCount

    fun updateState() {
        automaton.goNextState()
    }

    operator fun get(row: Int, col: Int): CellState {
        return automaton[row, col]
    }
}