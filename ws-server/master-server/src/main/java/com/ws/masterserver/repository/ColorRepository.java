package com.ws.masterserver.repository;

import com.ws.masterserver.entity.ColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author myname
 */
@Repository
public interface ColorRepository extends JpaRepository<ColorEntity, String> {
    ColorEntity findByIdAndActive(String id, Boolean active);

    Boolean existsByIdAndActive(String id, Boolean active);
}
