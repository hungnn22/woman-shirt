package com.ws.masterserver.repository;

import com.ws.masterserver.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, String> {
    CategoryEntity findByIdAndActive(String id, Boolean active);

    Boolean existsByIdAndActive(String id, Boolean active);

    Boolean existsByNameIgnoreCaseAndActiveAndIdNot(String trim, Boolean aTrue, String id);

    Boolean existsByNameIgnoreCaseAndActive(String toLowerCase, Boolean aTrue);

    CategoryEntity findByNameIgnoreCaseAndActive(String categoryName, Boolean aTrue);
}
