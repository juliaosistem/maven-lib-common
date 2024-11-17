package com.common.lib.infraestructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.stereotype.Repository

@NoRepositoryBean
interface DefualtRepository<E,I>: JpaRepository<E,I> {
}