package com.ws.master_service.repository;

import com.ws.master_service.entity.MaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author myname
 */
@Repository
public interface MaterialRepository extends JpaRepository<MaterialEntity, String> {
    MaterialEntity findByIdAndActive(String id, Boolean active);

    Boolean existsByIdAndActive(String id, Boolean active);
}
