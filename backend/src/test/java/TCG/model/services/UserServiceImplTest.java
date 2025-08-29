package tcg.model.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import tcg.model.entities.Location;
import tcg.model.entities.LocationDao;
import tcg.model.entities.User;
import tcg.model.entities.UserDao;
import tcg.model.exceptions.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock private UserDao userDao;
    @Mock private LocationDao locationDao;
    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private User admin;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("user");
        user.setEmail("user@test.com");
        user.setPassword("encoded");

        admin = new User();
        admin.setId(10L);
        admin.setRole("ADMIN");
    }

    @Test
    void register_success() throws AlreadyExistsException {
        when(userDao.existsByUsername("user")).thenReturn(false);
        when(userDao.existsByEmail("user@test.com")).thenReturn(false);
        when(passwordEncoder.encode("encoded")).thenReturn("hashed");

        userService.register(user);

        assertEquals("USER", user.getRole());
        assertFalse(user.isStore());
        assertEquals("hashed", user.getPassword());
        verify(userDao).save(user);
    }

    @Test
    void register_usernameExists() {
        when(userDao.existsByUsername("user")).thenReturn(true);
        assertThrows(AlreadyExistsException.class, () -> userService.register(user));
    }

    @Test
    void login_success() throws UserNotFoundException {
        when(userDao.findByUsernameIgnoreCase("user")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("encoded", "encoded")).thenReturn(true);

        User result = userService.login("user", "encoded");
        assertEquals(user, result);
    }

    @Test
    void login_userNotFound() {
        when(userDao.findByUsernameIgnoreCase("user")).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.login("user", "encoded"));
    }

    @Test
    void login_wrongPassword() {
        when(userDao.findByUsernameIgnoreCase("user")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "encoded")).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.login("user", "wrong"));
    }

    @Test
    void userBecomesStore_success() throws Exception {
        when(userDao.findById(1L)).thenReturn(Optional.of(user));
        Location location = new Location();
        when(locationDao.save(any(Location.class))).thenReturn(location);

        userService.userBecomesStore(1L, 10.0, 20.0);

        assertTrue(user.isStore());
        assertEquals(location, user.getLocation());
        verify(userDao).save(user);
    }

    @Test
    void deleteUser_success() throws Exception {
        User normalUser = new User();
        normalUser.setId(2L);
        normalUser.setRole("USER");
        when(userDao.findById(2L)).thenReturn(Optional.of(normalUser));
        when(userDao.findById(10L)).thenReturn(Optional.of(admin));

        userService.deleteUser(10L, 2L);

        verify(userDao).delete(normalUser);
    }

    @Test
    void getUsers_success() throws Exception {
        when(userDao.findById(10L)).thenReturn(Optional.of(admin));
        Page<User> page = new PageImpl<>(List.of(user));
        when(userDao.findByRole("USER", Pageable.unpaged())).thenReturn(page);

        Page<User> result = userService.getUsers(10L, Pageable.unpaged());

        assertEquals(1, result.getContent().size());
    }
}
