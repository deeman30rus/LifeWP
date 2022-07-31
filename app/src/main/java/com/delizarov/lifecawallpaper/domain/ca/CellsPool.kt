package com.delizarov.lifecawallpaper.domain.ca

class CellsPool(
    initialCapacity: Int
) {

    private var pool = ArrayDeque<Cell>(initialCapacity)

    var capacity = initialCapacity
        private set

    val size: Int
        get() = pool.size

    init {
        allocateCells()
    }

    fun extendCapacity(newCapacity: Int) {
        require(pool.isEmpty()) { "to reallocate pool must be empty" }

        pool = ArrayDeque(newCapacity)
    }

    fun obtain(): Cell {
        if (pool.isEmpty()) error("Initial capacity of $capacity is exceeded")

        return pool.removeFirst()
    }

    fun obtain(row: Int, col: Int, state: CellState) = obtain().apply {
        this.row = row
        this.col = col
        this.state = state
    }

    fun release(cell: Cell) {
        pool.addLast(cell)
    }

    private fun allocateCells() {
        repeat(capacity) {
            pool.add(Cell())
        }
    }

}