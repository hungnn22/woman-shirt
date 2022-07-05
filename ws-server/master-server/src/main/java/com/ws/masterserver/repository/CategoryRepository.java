package com.ws.masterserver.repository;

import com.ws.masterserver.dto.customer.suggest.CategoryDto;
import com.ws.masterserver.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, String> {
    CategoryEntity findByIdAndActive(String id, Boolean active);

    Boolean existsByIdAndActive(String id, Boolean active);

    Boolean existsByNameIgnoreCaseAndActiveAndIdNot(String trim, Boolean aTrue, String id);

    Boolean existsByNameIgnoreCaseAndActive(String toLowerCase, Boolean aTrue);

    CategoryEntity findByNameIgnoreCaseAndActive(String categoryName, Boolean aTrue);

    @Query("select new com.ws.masterserver.dto.customer.suggest.CategoryDto(\n" +
            "c.id,\n" +
            "c.name,\n" +
            "c.image) from CategoryEntity c\n" +
            "order by c.name")
    List<CategoryDto> findSuggestCategories();
}
