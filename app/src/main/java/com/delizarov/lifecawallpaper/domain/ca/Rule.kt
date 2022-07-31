package com.delizarov.lifecawallpaper.domain.ca

interface Rule {

    fun calcState(current: Cell, neighbours: List<Cell>): CellState
}