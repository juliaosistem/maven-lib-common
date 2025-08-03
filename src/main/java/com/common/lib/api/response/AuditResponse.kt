package com.common.lib.api.response;


import java.util.*;

import java.util.UUID;

data class AuditResponse(
        var id: UUID? = null,
        var proceso: String? = null
) {
    companion object {
        @JvmStatic
        fun builder(): Builder = Builder()
    }

    class Builder {
        private var id: UUID? = null
        private var proceso: String? = null

        fun id(id: UUID?) = apply { this.id = id }
        fun proceso(proceso: String?) = apply { this.proceso = proceso }
        fun build() = AuditResponse(id, proceso)
    }
}
