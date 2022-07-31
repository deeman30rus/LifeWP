package com.delizarov.lifecawallpaper.extensions

import com.delizarov.lifecawallpaper.domain.ca.Cell
import com.delizarov.lifecawallpaper.domain.life.LifeModel

val LifeModel.cells: Sequence<Cell>
    get() = sequence {
        for (r in 0 until rowCount)
            for (c in 0 until colCount) {
                yield(get(r, c))
            }
    }