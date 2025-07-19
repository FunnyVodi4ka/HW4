package myappuserservice.springmyapp;

import myappuserservice.springmyapp.model.User;
import myappuserservice.springmyapp.repository.UserDTO;
import myappuserservice.springmyapp.repository.UserRepository;
import myappuserservice.springmyapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    public void setUp() {
        testUser = new User();
        testUser.setName("John Doe");
        testUser.setEmail("john.doe@example.com");
        testUser.setAge(30);
        userRepository.save(testUser);
    }

    @Test
    public void testFindUserById() {
        UserDTO foundUser = userService.findUserById(testUser.getUser_id());
        //User foundUser = userService.findUserById(testUser.getUser_id());
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getName()).isEqualTo(testUser.getName());
    }

    @Test
    public void testFindAllUsers() {
        List<UserDTO> users = userService.findAllUsers();
        //List<User> users = userService.findAllUsers();
        assertThat(users).isNotEmpty();
        assertThat(users.size()).isGreaterThan(0);
    }

    @Test
    public void testSaveUser() {
        UserDTO newUser = new UserDTO();
        //User newUser = new User();
        newUser.setName("Jane Doe");
        newUser.setEmail("jane.doe@example.com");
        newUser.setAge(25);

        UserDTO savedUser = userService.saveUser(newUser);
        //User savedUser = userService.saveUser(newUser);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getName()).isEqualTo(newUser.getName());
        assertThat(savedUser.getEmail()).isEqualTo(newUser.getEmail());
    }

    @Test
    public void testDeleteById() {
        Long id = testUser.getUser_id();
        userService.deleteById(id);
        assertThrows(JpaObjectRetrievalFailureException.class, () -> userService.findUserById(id));
    }
}

