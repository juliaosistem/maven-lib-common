package com.common.lib.infraestructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefaultRepository<E,I> extends JpaRepository<E,I> {
}
