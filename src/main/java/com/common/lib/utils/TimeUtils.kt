package com.common.lib.utils

import com.common.lib.api.response.PlantillaResponse

object TimeUtils {

    /**
     * Mide el tiempo transcurrido entre el inicio y el fin, y lo devuelve en formato de minutos y segundos.
     *
     * @param block El bloque de código cuya duración se desea medir.
     * @return El resultado del bloque de código (de tipo PlantillaResponse<RES>).
     */
    fun <RES> measureExecutionTime(block: () -> PlantillaResponse<RES>): PlantillaResponse<RES> {
        // Captura el tiempo de inicio
        val startTime = System.nanoTime()

        // Ejecuta el bloque de código pasado como parámetro
        val result = block()

        // Captura el tiempo de fin
        val endTime = System.nanoTime()

        // Calcula la duración en milisegundos
        val durationInMillis = (endTime - startTime) / 1_000_000

        // Convierte la duración a minutos y segundos
        val minutes = durationInMillis / 60_000
        val seconds = (durationInMillis % 60_000) / 1000

        // Loguea el tiempo de ejecución
        println("Tiempo de ejecución: $minutes minutos y $seconds segundos")

        // Retorna el resultado del bloque de código (tipo PlantillaResponse<RES>)
        return result
    }
}
