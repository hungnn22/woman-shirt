package com.ws.masterserver.repository;

import com.ws.masterserver.entity.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<BlogEntity, String> {
    BlogEntity findByTitleAndContent(String title, String content);
    BlogEntity findByid(String id);
}
