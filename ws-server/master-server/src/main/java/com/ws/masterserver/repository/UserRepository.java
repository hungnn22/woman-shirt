package com.ws.masterserver.repository;

import com.ws.masterserver.dto.customer.user.UserDto;
import com.ws.masterserver.entity.UserEntity;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.constants.enums.RoleEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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

    /**
     * @return số người dùng mới trong tuần
     */
    @Query("select count(u) from UserEntity u\n" +
            "where extract('week' from u.createdDate) = extract('week' from current_date)")
    Long countNewUserThisWeek();

    @Query("select u\n" +
            "from UserEntity u\n" +
            "where (:textSearch is null\n" +
            "or unaccent(upper(u.firstName)) like concat('%', unaccent(upper(trim(:textSearch))), '%')\n" +
            "or unaccent(upper(u.lastName)) like concat('%', unaccent(upper(trim(:textSearch))), '%')\n" +
            "or unaccent(upper(u.email)) like concat('%', unaccent(upper(trim(:textSearch))), '%')\n" +
            "or unaccent(upper(u.phone)) like concat('%', unaccent(upper(trim(:textSearch))), '%'))\n" +
            "and (:active is null or u.active = :active)\n" +
            "and (:role is null or u.role = :role)\n" +
            "and (:diffRole is null or u.role <> :diffRole)")
    Page<UserEntity> search(@Param("textSearch") String textSearch,
                            @Param("active") Boolean active,
                            @Param("role") RoleEnum role,
                            @Param("diffRole") RoleEnum diffRole,
                            Pageable pageable);

    @Query("select trim(concat(coalesce(u.firstName, ''), ' ', coalesce(u.lastName, ''))) as name\n" +
            "from UserEntity u\n" +
            "where u.id = ?1")
    String findNameById(String createdBy);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByPhone(String phone);

    boolean existsByIdAndActive(String id, boolean active);

    boolean existsByPhoneAndIdNot(String phone, String id);
}
