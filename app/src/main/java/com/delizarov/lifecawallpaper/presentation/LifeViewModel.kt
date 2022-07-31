package com.delizarov.lifecawallpaper.presentation

import com.delizarov.lifecawallpaper.domain.ca.Cell
import com.delizarov.lifecawallpaper.domain.ca.CellsPool
import com.delizarov.lifecawallpaper.domain.life.LifeModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private const val FRAME_RATE = 60
private const val FRAME_RATE_DELAY = 1000L / (FRAME_RATE + 1)

class LifeViewModel @Inject constructor() {

    private var curState: LifeModel? = null
    private val cellsPool = CellsPool(0)

    fun diffFlow(): Flow<ViewStateDiff> = flow {

        // надо эммитить состояния по количеству кадров в секунду
        // 1. получаем начальное состояние,
        // 2. вычисляем дифф,
        // 3. запускаем процесс перехода из текущего состояния в следующее
        // 4. эммитим вьюстейты от текущего состояния до тех пор пока переход из стостояния в состояние не будет прекращён
        // 5. вычисляем новое состояние и го в п.1

        while (true) {
            val nextState: LifeModel? = null // cur if cur null or LifeModel#nextState otherwise

            // тут вычисляется дифф между моделями, какие клетки появились, а какие должны быть удалены
            val diff = calcDiff(curState, nextState)

            repeat(FRAME_RATE) {
                // тут должна быть просто анимация затуханя умерших клеток
                // и появления родившихся

                val vs = ViewStateDiff.from(diff)

                emit(vs)

                delay(FRAME_RATE_DELAY)
            }

            curState = nextState
        }
    }

    companion object {

        private fun calcDiff(fromState: LifeModel?, toState: LifeModel?): ModelDiff {

            return ModelDiff(
                added = calcAdded(fromState, toState),
                removed = calcRemoved(fromState, toState),
            )
        }

        private fun calcAdded(fromModel: LifeModel?, toModel: LifeModel?): List<Cell> {
            return when {
                toModel == null && fromModel == null -> emptyList()
                toModel == null -> emptyList()
                fromModel == null -> {
                    toModel.aliveCells
                }
                else -> {
//                    require(fromModel.rowCount == toModel.rowCount) { "models row count difference" }
//                    require(fromModel.colCount == toModel.colCount) { "models column count difference" }

                    val bornCells = mutableListOf<Cell>()

                    for (r in 0 until )

                    bornCells
                }
            }

            return emptyList() // todo implement
        }

        private fun calcRemoved(fromState: LifeModel?, toState: LifeModel?): List<Cell> {

            return emptyList() // todo implement
        }
    }
}

private val LifeModel.aliveCells: List<Cell>
    get() = cells.filter { it.isAlive }

private data class ModelDiff(
    val added: List<Cell>,
    val removed: List<Cell>,
)


