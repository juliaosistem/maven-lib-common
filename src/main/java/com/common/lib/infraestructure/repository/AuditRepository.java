package com.common.lib.infraestructure.repository;

import com.common.lib.infraestructure.entitis.Audit;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AuditRepository extends JpaRepository<Audit, UUID> {

    List<Audit> findByIdBussines(long idBusiness);
}
