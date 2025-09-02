package tcg.model.entities;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TestEntityManager entityManager;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = new User();
        user1.setUsername("user1");
        user1.setEmail("user1@test.com");
        user1.setFirstName("User");
        user1.setLastName("One");
        user1.setPassword("pass");
        user1.setRole("USER");
        user1.setStore(false);
        user1.setAddress("Calle 1");
        entityManager.persist(user1);

        user2 = new User();
        user2.setUsername("admin");
        user2.setEmail("admin@test.com");
        user2.setFirstName("Admin");
        user2.setLastName("User");
        user2.setPassword("pass");
        user2.setRole("ADMIN");
        user2.setStore(true);
        user2.setAddress("Calle 2");
        entityManager.persist(user2);

        entityManager.flush();
    }

    @Test
    void testExistsByUsername() {
        boolean exists = userDao.existsByUsername("user1");
        boolean notExists = userDao.existsByUsername("unknown");
        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }

    @Test
    void testExistsByEmail() {
        boolean exists = userDao.existsByEmail("admin@test.com");
        boolean notExists = userDao.existsByEmail("noone@test.com");
        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }

    @Test
    void testFindByUsernameIgnoreCase() {
        Optional<User> found = userDao.findByUsernameIgnoreCase("USER1");
        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("user1@test.com");

        Optional<User> notFound = userDao.findByUsernameIgnoreCase("unknown");
        assertThat(notFound).isEmpty();
    }
}
