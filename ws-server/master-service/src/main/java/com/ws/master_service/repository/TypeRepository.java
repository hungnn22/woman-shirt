package com.ws.master_service.repository;

import com.ws.master_service.entity.TypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author myname
 */
public interface TypeRepository extends JpaRepository<TypeEntity, String> {
    boolean existsByIdAndActive(String typeId, boolean b);
}
