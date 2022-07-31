package com.delizarov.lifecawallpaper.presentation

import com.delizarov.lifecawallpaper.domain.ca.Cell

/**
 * Дифф модели который содержит список клеток, которые были рождены на данной итерации
 * и те которые умерли
 */
data class ModelDiff(
    val born: List<Cell>,
    val died: List<Cell>,
)