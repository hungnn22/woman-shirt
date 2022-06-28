package com.ws.masterserver.repository;

import com.ws.masterserver.dto.admin.user.UserReq;
import com.ws.masterserver.dto.admin.user.search.UserRes;
import com.ws.masterserver.dto.customer.user.UserDto;
import com.ws.masterserver.entity.UserEntity;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByEmailAndActive(String value, Boolean active);

    boolean existsByEmail(String email);

    UserEntity findByIdAndActive(String id, Boolean aTrue);

    @Query("select new com.ws.masterserver.utils.base.rest.CurrentUser(\n" +
            "u.id,\n" +
            "trim(concat(coalesce(u.firstName, ''), ' ', coalesce(u.lastName, ''))),\n" +
            "u.role,\n" +
            "u.email)\n" +
            "from UserEntity u\n" +
            "where u.active = true and u.id = :id")
    CurrentUser findCurrentUserByIdAndActive(@Param("id") String id);

    @Query("select new com.ws.masterserver.utils.base.rest.CurrentUser(\n" +
            "u.id,\n" +
            "trim(concat(coalesce(u.firstName, ''), ' ', coalesce(u.lastName, ''))),\n" +
            "u.role,\n" +
            "u.email)\n" +
            "from UserEntity u\n" +
            "where u.active = true and u.email = :email")
    CurrentUser findCurrentUserByEmailAndActive(@Param("email") String email);

    @Query("select new com.ws.masterserver.dto.customer.user.UserDto(" +
            "u.email,\n" +
            "u.password,\n" +
            "u.role)\n" +
            "from UserEntity u\n" +
            "where u.email = :email and u.active = :active")
    UserDto findUserDtoByEmail(@Param("email") String email, @Param("active") Boolean active);

    UserEntity findByEmailIgnoreCaseAndActive(String email, Boolean aTrue);

    @Query("select count(u) from UserEntity u\n" +
            "where cast(u.createdDate as date) = cast(current_date as date)")
    Long countNewUserToday();
}
