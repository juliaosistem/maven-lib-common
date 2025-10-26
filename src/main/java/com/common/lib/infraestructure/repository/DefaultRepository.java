package com.common.lib.infraestructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Repositorio base que implementa operaciones CRUD genéricas.
 * Sigue los principios SOLID y arquitectura hexagonal.
 * 
 * @author Daniel Juliao
 * @param <E> Tipo de la entidad
 * @param <I> Tipo del ID de la entidad
 * @version 3
 */
@NoRepositoryBean
public interface DefaultRepository<E, I> extends JpaRepository<E, I>, JpaSpecificationExecutor<E> {
    
    /**
     * Obtiene todas las entidades con paginación.
     * 
     * @param pageable Información de paginación
     * @return Page con las entidades
     */
    default Page<E> findAllWithPagination(Pageable pageable) {
        return findAll(pageable);
    }
    
    /**
     * Obtiene todas las entidades con paginación por defecto.
     * 
     * @return Page con las entidades
     */
    default Page<E> findAllWithPagination() {
        return findAllWithPagination(PageRequest.of(0, 100));
    }
    
    /**
     * Busca una entidad por ID.
     * 
     * @param id ID de la entidad
     * @return Optional con la entidad encontrada
     */
    default Optional<E> findByIdSafe(I id) {
        return findById(id);
    }
    
    /**
     * Verifica si una entidad existe por ID.
     * 
     * @param id ID de la entidad
     * @return true si existe, false en caso contrario
     */
    default boolean existsByIdSafe(I id) {
        return existsById(id);
    }
    
    /**
     * Elimina una entidad por ID de forma segura.
     * 
     * @param id ID de la entidad a eliminar
     * @return true si se eliminó, false si no existía
     */
    default boolean deleteByIdSafe(I id) {
        if (existsById(id)) {
            deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
