package com.common.lib.api.response

import org.springframework.http.HttpStatus



data class PlantillaResponse<RES>(
        var rta: Boolean = false,
        var message: String = "",
        var httpStatus: HttpStatus = HttpStatus.OK,
        var data: RES? = null,
        var dataList: List<RES>? = null
) {
        // Constructor sin argumentos para compatibilidad con Java
        constructor() : this(false, "", HttpStatus.OK, null, null)

        companion object {
                @JvmStatic
                fun <RES> builder(): Builder<RES> = Builder()
        }

        class Builder<RES> {
                private var rta: Boolean = false
                private var message: String = ""
                private var httpStatus: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR
                private var data: RES? = null
                private var dataList: List<RES>? = null

                fun rta(rta: Boolean) = apply { this.rta = rta }
                fun message(message: String) = apply { this.message = message }
                fun httpStatus(httpStatus: HttpStatus) = apply { this.httpStatus = httpStatus }
                fun data(data: RES) = apply { this.data = data }
                fun dataList(dataList: List<RES>) = apply { this.dataList = dataList }

                fun build(): PlantillaResponse<RES> =
                        PlantillaResponse(rta, message, httpStatus, data, dataList)
        }
}
