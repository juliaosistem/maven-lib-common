package com.common.lib.utils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;

/**
 * Builder generico para convertir query params dinamicos en {@link Specification} de JPA.
 */
public final class GenericSpecificationBuilder {

    private static final Set<String> RESERVED_PARAMS = Set.of("page", "size", "sort", "id", "idBusiness");

    private GenericSpecificationBuilder() {
    }

    /**
     * Construye una specification combinada a partir de filtros clave/valor.
     *
     * @param filters query params de filtros
     * @param <T> tipo de entidad
     * @return specification resultante; conjunction cuando no hay filtros validos
     */
    public static <T> Specification<T> fromFilters(Map<String, String> filters) {
        return (root, query, cb) -> buildAndPredicate(filters, root, cb);
    }

    private static Predicate buildAndPredicate(Map<String, String> filters, Root<?> root, CriteriaBuilder cb) {
        if (filters == null || filters.isEmpty()) {
            return cb.conjunction();
        }

        List<Predicate> predicates = new ArrayList<>();
        for (Map.Entry<String, String> entry : filters.entrySet()) {
            Predicate predicate = resolvePredicate(entry, root, cb);
            if (predicate != null) {
                predicates.add(predicate);
            }
        }
        return predicates.isEmpty() ? cb.conjunction() : cb.and(predicates.toArray(new Predicate[0]));
    }

    private static Predicate resolvePredicate(Map.Entry<String, String> entry, Root<?> root, CriteriaBuilder cb) {
        String field = entry.getKey();
        String rawValue = entry.getValue();
        if (shouldSkip(field, rawValue)) {
            return null;
        }

        try {
            Path<?> path = resolvePath(root, field);
            return buildPredicate(path, rawValue.trim(), cb);
        } catch (RuntimeException ex) {
            return null;
        }
    }

    private static boolean shouldSkip(String field, String value) {
        if (field == null || value == null) {
            return true;
        }
        return field.isBlank() || value.isBlank() || RESERVED_PARAMS.contains(field);
    }

    private static Predicate buildPredicate(Path<?> path, String value, CriteriaBuilder cb) {
        if (value.startsWith("like:")) {
            return cb.like(cb.lower(path.as(String.class)), "%" + value.substring(5).toLowerCase() + "%");
        }

        if (value.startsWith(">=")) {
            return buildComparable(path, value.substring(2).trim(), cb, ComparisonType.GTE);
        }
        if (value.startsWith("<=")) {
            return buildComparable(path, value.substring(2).trim(), cb, ComparisonType.LTE);
        }
        if (value.startsWith(">")) {
            return buildComparable(path, value.substring(1).trim(), cb, ComparisonType.GT);
        }
        if (value.startsWith("<")) {
            return buildComparable(path, value.substring(1).trim(), cb, ComparisonType.LT);
        }

        if (value.contains(",")) {
            return buildInPredicate(path, value);
        }

        Object converted = convertValue(path.getJavaType(), value.trim());
        return cb.equal(path, converted);
    }

    private static Predicate buildInPredicate(Path<?> path, String rawValue) {
        Class<?> javaType = path.getJavaType();
        List<Object> values = Arrays.stream(rawValue.split(","))
                .map(String::trim)
                .filter(part -> !part.isBlank())
                .map(part -> convertValue(javaType, part))
                .toList();
        if (values.isEmpty()) {
            return null;
        }
        return path.in(values);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static Predicate buildComparable(Path<?> path, String rawValue, CriteriaBuilder cb, ComparisonType type) {
        Class<?> javaType = wrapPrimitive(path.getJavaType());
        Object converted = convertValue(javaType, rawValue);
        if (!(converted instanceof Comparable comparable)) {
            return null;
        }

        Expression comparablePath = path.as((Class) javaType);
        return switch (type) {
            case GTE -> cb.greaterThanOrEqualTo(comparablePath, comparable);
            case LTE -> cb.lessThanOrEqualTo(comparablePath, comparable);
            case GT -> cb.greaterThan(comparablePath, comparable);
            case LT -> cb.lessThan(comparablePath, comparable);
        };
    }

    private static Path<?> resolvePath(Path<?> root, String field) {
        Path<?> current = root;
        for (String segment : field.split("\\.")) {
            current = current.get(segment);
        }
        return current;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static Object convertValue(Class<?> targetType, String value) {
        Class<?> type = wrapPrimitive(targetType);
        if (String.class.equals(type)) return value;
        if (Integer.class.equals(type)) return Integer.parseInt(value);
        if (Long.class.equals(type)) return Long.parseLong(value);
        if (Double.class.equals(type)) return Double.parseDouble(value);
        if (Float.class.equals(type)) return Float.parseFloat(value);
        if (Boolean.class.equals(type)) return parseBoolean(value);
        if (BigDecimal.class.equals(type)) return new BigDecimal(value);
        if (BigInteger.class.equals(type)) return new BigInteger(value);
        if (UUID.class.equals(type)) return UUID.fromString(value);
        if (LocalDate.class.equals(type)) return LocalDate.parse(value);
        if (LocalDateTime.class.equals(type)) return parseDateTime(value);
        if (Enum.class.isAssignableFrom(type)) return Enum.valueOf((Class<? extends Enum>) type, value.toUpperCase());
        return value;
    }

    private static Boolean parseBoolean(String value) {
        String normalized = value.trim().toLowerCase();
        if ("1".equals(normalized) || "true".equals(normalized) || "yes".equals(normalized)) {
            return Boolean.TRUE;
        }
        if ("0".equals(normalized) || "false".equals(normalized) || "no".equals(normalized)) {
            return Boolean.FALSE;
        }
        return Boolean.parseBoolean(normalized);
    }

    private static LocalDateTime parseDateTime(String value) {
        try {
            return LocalDateTime.parse(value);
        } catch (DateTimeParseException ex) {
            return LocalDate.parse(value).atStartOfDay();
        }
    }

    private static Class<?> wrapPrimitive(Class<?> type) {
        if (!type.isPrimitive()) return type;
        if (type == int.class) return Integer.class;
        if (type == long.class) return Long.class;
        if (type == double.class) return Double.class;
        if (type == float.class) return Float.class;
        if (type == boolean.class) return Boolean.class;
        return type;
    }

    private enum ComparisonType {
        GTE, LTE, GT, LT
    }
}

