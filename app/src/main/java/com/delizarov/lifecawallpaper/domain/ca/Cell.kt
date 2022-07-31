package com.delizarov.lifecawallpaper.domain.ca

data class Cell(
    var row: Int = 0,
    var col: Int = 0,
    var state: CellState = CellState.Dead
)