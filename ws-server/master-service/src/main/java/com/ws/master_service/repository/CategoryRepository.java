package com.ws.master_service.repository;

import com.ws.master_service.dto.customer.suggest.CategoryDto;
import com.ws.master_service.entity.CategoryEntity;
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

    @Query("select new com.ws.master_service.dto.customer.suggest.CategoryDto(\n" +
            "c.id,\n" +
            "c.name,\n" +
            "c.image) from CategoryEntity c\n" +
            "where c.active = true\n" +
            "order by c.name")
    List<CategoryDto> findSuggestCategories();
}
