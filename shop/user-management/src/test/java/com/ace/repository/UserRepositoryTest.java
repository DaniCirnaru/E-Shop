package com.ace.repository;

import com.ace.entity.Role;
import com.ace.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;
    private Role testRole;
    private User testUser;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

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
    }

    @Test
    public void testSaveUser() {
        User savedUser = userRepository.save(testUser);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("testUser");
    }

    @Test
    public void testFindAll() {
        User user2 = new User();
        user2.setUsername("testUser2");
        user2.setPassword("password2");
        user2.setEmail("test2@email.com");
        user2.setRole(testRole);

        userRepository.save(testUser);
        userRepository.save(user2);

        List<User> foundUserList = userRepository.findAll();

        assertThat(foundUserList).isNotNull();
        assertThat(foundUserList.size()).isEqualTo(2);
    }

    @Test
    public void testFindById() {
        User savedUser = userRepository.save(testUser);

        Optional<User> retrievedUser = userRepository.findById(savedUser.getId());

        assertThat(retrievedUser).isPresent();
        assertThat(retrievedUser.get().getId()).isEqualTo(savedUser.getId());
    }

    @Test
    public void testFindByEmail() {
        User savedUser = userRepository.save(testUser);

        Optional<User> retrievedUser = userRepository.findByEmail(savedUser.getEmail());

        assertThat(retrievedUser).isPresent();
        assertThat(retrievedUser.get().getEmail()).isEqualTo(savedUser.getEmail());
    }

    @Test
    public void testDeleteUser() {
        User savedUser = userRepository.save(testUser);

        assertThat(userRepository.findById(savedUser.getId())).isPresent();

        userRepository.delete(savedUser);
        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        assertThat(foundUser).isEmpty();
    }

}
