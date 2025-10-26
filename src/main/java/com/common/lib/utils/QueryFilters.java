package com.common.lib.utils;

import java.util.Collections;
import java.util.Map;

/**
 * Representa filtros de consulta tipados.
 */
public class QueryFilters {
    private final Map<String, String> filters;

    public QueryFilters(Map<String, String> filters) {
        this.filters = filters == null ? Collections.emptyMap() : filters;
    }

    public Map<String, String> asMap() {
        return filters;
    }
}


