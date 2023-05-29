package com.formos.stripe.repository;

import com.formos.stripe.domain.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link User} entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    String USERS_BY_LOGIN_CACHE = "usersByLogin";

    String USERS_BY_EMAIL_CACHE = "usersByEmail";
    Optional<User> findOneByActivationKey(String activationKey);
    List<User> findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(LocalDateTime dateTime);
    Optional<User> findOneByResetKey(String resetKey);
    Optional<User> findOneByEmailIgnoreCase(String email);
    Optional<User> findOneByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    @Cacheable(cacheNames = USERS_BY_LOGIN_CACHE)
    Optional<User> findOneWithAuthoritiesByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    @Cacheable(cacheNames = USERS_BY_EMAIL_CACHE)
    Optional<User> findOneWithAuthoritiesByEmailIgnoreCase(String email);

    Page<User> findAllByIdNotNullAndActivatedIsTrue(Pageable pageable);
    Page<User> findAllByIsDeletedIsFalse(Pageable pageable);

    List<User> findUserByAuthoritiesName(String name);

    User findFirstByEmail(String email);

    Optional<User> findFirstByEmailAndIsDeletedFalse(String email);

    @Query(
        value = " select ju.* from jhi_user ju " +
        " join jhi_user_authority jua ON ju.id = jua.user_id " +
        " where ju.is_deleted = false and jua.authority_name in :roles and ju.activated = true ",
        nativeQuery = true
    )
    Page<User> findAllUserByRoles(List<String> roles, Pageable pageable);

    @Query(
        value = " select ju.* from jhi_user ju " +
        " join jhi_user_authority jua ON ju.id = jua.user_id " +
        " where ju.is_deleted = false and ju.id in :ids ",
        nativeQuery = true
    )
    List<User> findAllUserByIds(List<Long> ids);
}
