package com.common.lib.utils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenericSpecificationBuilderTest {

    @Mock
    private Root<Object> root;
    @Mock
    private CriteriaQuery<?> query;
    @Mock
    private CriteriaBuilder cb;
    @Mock
    private Path<Object> path;
    @Mock
    private Predicate predicate;
    @Mock
    private Predicate andPredicate;
    @Mock
    private Predicate conjunction;

    @Test
    @DisplayName("Construye operador mayor o igual con tipo Integer")
    void shouldBuildGreaterOrEqualPredicate() {
        Map<String, String> filters = Map.of("precio", ">=1000");
        mockComparablePath("precio", Integer.class);
        when(cb.and(any(Predicate[].class))).thenReturn(andPredicate);
        when(cb.greaterThanOrEqualTo(any(Expression.class), eq(1000))).thenReturn(predicate);

        Predicate result = build(filters);

        assertSame(andPredicate, result);
        verify(cb).greaterThanOrEqualTo(any(Expression.class), eq(1000));
    }

    @Test
    @DisplayName("Construye operador menor o igual con tipo LocalDate")
    void shouldBuildLessOrEqualPredicate() {
        Map<String, String> filters = Map.of("fecha", "<=2026-08-01");
        mockComparablePath("fecha", LocalDate.class);
        when(cb.and(any(Predicate[].class))).thenReturn(andPredicate);
        LocalDate expected = LocalDate.parse("2026-08-01");
        when(cb.lessThanOrEqualTo(any(Expression.class), eq(expected))).thenReturn(predicate);

        Predicate result = build(filters);

        assertSame(andPredicate, result);
        verify(cb).lessThanOrEqualTo(any(Expression.class), eq(expected));
    }

    @Test
    @DisplayName("Construye operador mayor estricto con tipo Long")
    void shouldBuildGreaterThanPredicate() {
        Map<String, String> filters = Map.of("edad", ">18");
        mockComparablePath("edad", Long.class);
        when(cb.and(any(Predicate[].class))).thenReturn(andPredicate);
        when(cb.greaterThan(any(Expression.class), eq(18L))).thenReturn(predicate);

        Predicate result = build(filters);

        assertSame(andPredicate, result);
        verify(cb).greaterThan(any(Expression.class), eq(18L));
    }

    @Test
    @DisplayName("Construye operador menor estricto con tipo Double")
    void shouldBuildLessThanPredicate() {
        Map<String, String> filters = Map.of("stock", "<5.5");
        mockComparablePath("stock", Double.class);
        when(cb.and(any(Predicate[].class))).thenReturn(andPredicate);
        when(cb.lessThan(any(Expression.class), eq(5.5d))).thenReturn(predicate);

        Predicate result = build(filters);

        assertSame(andPredicate, result);
        verify(cb).lessThan(any(Expression.class), eq(5.5d));
    }

    @Test
    @DisplayName("Construye operador like insensible a mayusculas")
    void shouldBuildLikePredicate() {
        Map<String, String> filters = Map.of("nombre", "like:Juan");
        when(root.get("nombre")).thenReturn(path);
        when(cb.and(any(Predicate[].class))).thenReturn(andPredicate);
        when(path.as(String.class)).thenReturn((Path) path);

        Expression<String> lowerExpression = org.mockito.Mockito.mock(Expression.class);
        when(cb.lower(any(Expression.class))).thenReturn(lowerExpression);
        when(cb.like(eq(lowerExpression), eq("%juan%"))).thenReturn(predicate);

        Predicate result = build(filters);

        assertSame(andPredicate, result);
        verify(cb).like(eq(lowerExpression), eq("%juan%"));
    }

    @Test
    @DisplayName("Construye operador IN por lista separada por comas")
    void shouldBuildInPredicate() {
        Map<String, String> filters = Map.of("estado", "ACTIVO,PENDIENTE");
        mockPath("estado", String.class);
        when(cb.and(any(Predicate[].class))).thenReturn(andPredicate);
        when(path.in(anyCollection())).thenReturn(predicate);

        Predicate result = build(filters);

        assertSame(andPredicate, result);
        verify(path).in(argThat((Collection<?> values) ->
                values.size() == 2 && values.contains("ACTIVO") && values.contains("PENDIENTE")));
    }

    @Test
    @DisplayName("Construye comparacion exacta para boolean")
    void shouldBuildExactBooleanPredicate() {
        Map<String, String> filters = Map.of("activo", "true");
        mockPath("activo", Boolean.class);
        when(cb.and(any(Predicate[].class))).thenReturn(andPredicate);
        when(cb.equal(path, true)).thenReturn(predicate);

        Predicate result = build(filters);

        assertSame(andPredicate, result);
        verify(cb).equal(path, true);
    }

    @Test
    @DisplayName("Resuelve propiedades anidadas con notacion de punto")
    void shouldResolveNestedPath() {
        Map<String, String> filters = Map.of("datesUser.firstName", "Juan");
        when(cb.and(any(Predicate[].class))).thenReturn(andPredicate);
        @SuppressWarnings("unchecked")
        Path<Object> nested = (Path<Object>) org.mockito.Mockito.mock(Path.class);
        @SuppressWarnings("unchecked")
        Path<Object> leaf = (Path<Object>) org.mockito.Mockito.mock(Path.class);

        when(root.get("datesUser")).thenReturn(nested);
        when(nested.get("firstName")).thenReturn(leaf);
        when(leaf.getJavaType()).thenReturn((Class) String.class);
        when(cb.equal(leaf, "Juan")).thenReturn(predicate);

        Predicate result = build(filters);

        assertSame(andPredicate, result);
        verify(cb).equal(leaf, "Juan");
    }

    @Test
    @DisplayName("Ignora parametros reservados")
    void shouldIgnoreReservedParams() {
        Map<String, String> filters = Map.of("page", "1", "size", "20", "idBusiness", "10");
        when(cb.conjunction()).thenReturn(conjunction);

        Predicate result = build(filters);

        assertSame(conjunction, result);
        verify(cb).conjunction();
    }

    @Test
    @DisplayName("Ignora campos inexistentes sin romper la consulta")
    void shouldIgnoreUnknownFields() {
        Map<String, String> filters = Map.of("campoInvalido", "x");
        when(cb.conjunction()).thenReturn(conjunction);
        when(root.get("campoInvalido")).thenThrow(new IllegalArgumentException("missing field"));

        Predicate result = build(filters);

        assertSame(conjunction, result);
        verify(cb).conjunction();
    }

    @Test
    @DisplayName("Convierte enums y UUID en comparaciones exactas")
    void shouldConvertEnumAndUuid() {
        Map<String, String> filters = new LinkedHashMap<>();
        UUID uuid = UUID.randomUUID();
        filters.put("estado", "activo");
        filters.put("externalId", uuid.toString());
        when(cb.and(any(Predicate[].class))).thenReturn(andPredicate);

        @SuppressWarnings("unchecked")
        Path<Object> enumPath = (Path<Object>) org.mockito.Mockito.mock(Path.class);
        @SuppressWarnings("unchecked")
        Path<Object> uuidPath = (Path<Object>) org.mockito.Mockito.mock(Path.class);

        when(root.get("estado")).thenReturn(enumPath);
        when(root.get("externalId")).thenReturn(uuidPath);
        when(enumPath.getJavaType()).thenReturn((Class) TestStatus.class);
        when(uuidPath.getJavaType()).thenReturn((Class) UUID.class);
        when(cb.equal(enumPath, TestStatus.ACTIVO)).thenReturn(predicate);
        when(cb.equal(uuidPath, uuid)).thenReturn(predicate);

        Predicate result = build(filters);

        assertSame(andPredicate, result);
        verify(cb).equal(enumPath, TestStatus.ACTIVO);
        verify(cb).equal(uuidPath, uuid);
    }

    private Predicate build(Map<String, String> filters) {
        Specification<Object> specification = GenericSpecificationBuilder.fromFilters(filters);
        return specification.toPredicate(root, query, cb);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void mockPath(String field, Class<?> javaType) {
        when(root.get(field)).thenReturn(path);
        when(path.getJavaType()).thenReturn((Class) javaType);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void mockComparablePath(String field, Class<?> javaType) {
        mockPath(field, javaType);
        when(path.as(any(Class.class))).thenReturn(path);
    }

    private enum TestStatus {
        ACTIVO,
        PENDIENTE
    }
}


