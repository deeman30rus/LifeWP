package com.delizarov.lifecawallpaper.presentation

class CellViewState(
    val row: Int,
    val col: Int,
    val alpha: Float,
)

class ViewStateDiff(
    val cellsViewState: List<CellViewState>,
    val redrawAll: Boolean = false,
)