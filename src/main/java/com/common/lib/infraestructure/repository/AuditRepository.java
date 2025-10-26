package com.common.lib.infraestructure.repository;

import com.common.lib.infraestructure.entitis.Audit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AuditRepository extends JpaRepository<Audit, UUID> {

    List<Audit> findByIdBusiness(Long idBusiness);
    
    @Query("SELECT a FROM Audit a WHERE a.idBusiness = :idBusiness")
    Page<Audit> findByIdBusiness(@Param("idBusiness") Long idBusiness, Pageable pageable);
}
