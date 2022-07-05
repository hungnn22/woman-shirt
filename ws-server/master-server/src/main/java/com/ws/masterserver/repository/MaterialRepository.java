package com.ws.masterserver.repository;

import com.ws.masterserver.entity.MaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author myname
 */
@Repository
public interface MaterialRepository extends JpaRepository<MaterialEntity, String> {
    MaterialEntity findByIdAndActive(String id, Boolean active);

    Boolean existsByNameIgnoreCaseAndActive(String nameMaterial, Boolean active);
}
