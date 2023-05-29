package com.formos.stripe.service;

import com.formos.stripe.domain.User;
import com.formos.stripe.service.dto.AdminUserDTO;
import com.formos.stripe.service.dto.UserDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Optional<User> activateRegistration(String key);

    Optional<User> completePasswordReset(String newPassword, String key);

    Optional<User> requestPasswordReset(String mail);

    User registerUser(AdminUserDTO userDTO, String password);

    User createUser(AdminUserDTO userDTO);

    Optional<AdminUserDTO> updateUser(AdminUserDTO userDTO);

    void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl);

    void deleteUser(String login);

    void changePassword(String currentClearTextPassword, String newPassword);

    Page<AdminUserDTO> getAllManagedUsers(Pageable pageable);

    Page<UserDTO> getAllPublicUsers(Pageable pageable);

    Optional<User> getUserWithAuthoritiesByLogin(String login);

    Optional<User> getUserWithAuthorities();

    void removeNotActivatedUsers();

    List<String> getAuthorities();

    Optional<User> findOneByEmail(String email);

    Optional<User> findOneByLogin(String login);

    Page<AdminUserDTO> getAllManagedUsersAssign(Pageable pageable);
}
