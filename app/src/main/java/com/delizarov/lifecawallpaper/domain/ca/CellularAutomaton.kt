package com.delizarov.lifecawallpaper.domain.ca

internal class CellularAutomaton(
    universe: List<List<CellState>>,
    private val rules: Rule
) {
    val rowCount = universe.size
    val colCount: Int = if (rowCount != 0) {
        universe[0].size
    } else {
        0
    }

    private val cellsPool = CellsPool(20)

    private val state1 = toIntMatr(universe)
    private val state2 = toIntMatr(universe)

    private var currentState = state1

    init {
        if (rowCount != 0) {
            for (row in universe) {
                if (row.size != colCount) {
                    error("universe must have rectangle shape")
                }
            }
        }
    }

    operator fun get(row: Int, col: Int): CellState {
        if (row < 0 || row > rowCount) {
            error("Out of universe access, current size is [$rowCount x $colCount], accessing element r:$row, c:$col")
        }
        if (col < 0 || col > colCount) {
            error("Out of universe access, current size is [$rowCount x $colCount], accessing element r:$row, c:$col")
        }

        return currentState[row][col]
    }

    fun goNextState() {
        val nextState = if (currentState == state1) state2 else state1

        for (row in 0 until rowCount) {
            for (col in 0 until colCount) {
                val currentCell = cellsPool.obtain(row, col, get(row, col))
                val neighbours = neighboursOf(row, col)

                nextState[row][col] = rules.calcState(currentCell, neighbours)

                for (neighbour in neighbours) {
                    cellsPool.release(neighbour)
                }
                cellsPool.release(currentCell)
            }
        }

        currentState = nextState
    }

    private fun toIntMatr(cells: List<List<CellState>>) = (0 until rowCount).map { row ->
        (0 until colCount).map { col -> cells[row][col] }.toMutableList()
    }.toList()

    private fun neighboursOf(row: Int, col: Int): List<Cell> {
        val rowRange = Integer.max(0, row - 1)..Integer.min(row + 1, rowCount - 1)
        val colRange = Integer.max(0, col - 1)..Integer.min(col + 1, colCount - 1)

        return rowRange.flatMap { r ->
            colRange.map { c -> r to c }
        }.asSequence()
            .filter { it.first != row || it.second != col }
            .map {
                cellsPool.obtain(it.first, it.second, get(it.first, it.second))
            }.toList()
    }

}