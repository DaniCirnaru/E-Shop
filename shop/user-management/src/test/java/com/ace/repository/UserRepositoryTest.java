package com.ace.repository;

import com.ace.entity.Role;
import com.ace.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private Role testRole;
    private User testUser;

    @BeforeEach
    public void setUp() {

        testUser = new User();
        testRole = new Role();
        testRole.setRoleName("testRole");
        roleRepository.save(testRole);

        testUser.setUsername("testUser");
        testUser.setPassword("password");
        testUser.setEmail("test@email.com");
        testUser.setRole(testRole);
        userRepository.save(testUser);
    }

    @Test
    void testSaveAndFindById() {
        User user = new User();
        user.setUsername("username");
        user.setEmail("test1@example.com");
        user.setPassword("password");
        user.setRole(testRole);

        User savedUser = userRepository.save(user);

        Optional<User> retrievedUser = userRepository.findById(savedUser.getId());
        assertThat(retrievedUser).isPresent();
        assertThat(retrievedUser.get().getUsername()).isEqualTo("username");
    }

    @Test
    void testFindByEmail() {
        User foundUser = userRepository.findByEmail("test@email.com")
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: testuser@example.com"));

        assertThat(foundUser.getEmail()).isEqualTo("test@email.com");
    }

    @AfterEach
    public void tearDown() {
        userRepository.delete(testUser);
    }
}
