package com.ws.masterserver.repository;

import com.ws.masterserver.entity.TypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author myname
 */
public interface TypeRepository extends JpaRepository<TypeEntity, String> {
}
