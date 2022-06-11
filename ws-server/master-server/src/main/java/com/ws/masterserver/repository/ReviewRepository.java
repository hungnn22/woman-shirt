package com.ws.masterserver.repository;

import com.ws.masterserver.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


public interface ReviewRepository extends JpaRepository<ReviewEntity, String> {
    List<ReviewEntity> findByProductIdAndActive(String productId,Boolean active);

    @Query(value = "select count(id) from review where active = true",nativeQuery = true)
    Integer countRatingActive();

    @Query(value = "select trim(concat(coalesce(u.first_name, ''), ' ', coalesce(u.last_name, ''))) " +
            "from review r\n" +
            "join users u on r.user_id = u.id\n" +
            "where r.user_id= ?1",nativeQuery = true)
    String getUserNameReview(String userId);

    @Query(value = "select avg(rating) from review where active = true",nativeQuery = true)
    Float avgRating();
}
