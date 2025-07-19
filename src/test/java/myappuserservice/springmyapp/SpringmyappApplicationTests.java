package myappuserservice.springmyapp;

import myappuserservice.springmyapp.controller.UserController;
import myappuserservice.springmyapp.model.User;
import myappuserservice.springmyapp.repository.UserDTO;
import myappuserservice.springmyapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class SpringmyappApplicationTests {

	@InjectMocks
	private UserController userController;

	@Mock
	private UserService userService;

	@Mock
	private Model model;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}

	@Test
	public void testFindAllUsers() throws Exception {
		UserDTO user1 = new UserDTO("John Doe", "testmail@gmail.com", 27);
		UserDTO user2 = new UserDTO("Jane T", "janemail@gmail.com", 24);

//		User user1 = new User("John Doe", "testmail@gmail.com", 27);
//		User user2 = new User("Jane T", "janemail@gmail.com", 24);
		List<UserDTO> users = Arrays.asList(user1, user2);
//		List<User> users = Arrays.asList(user1, user2);

		when(userService.findAllUsers()).thenReturn(users);

		mockMvc.perform(get("/users"))
				.andExpect(status().isOk())
				.andExpect(model().attribute("users", users))
				.andExpect(view().name("user-list"));
	}

	@Test
	public void testCreateUser() throws Exception {
		User user = new User();
		mockMvc.perform(post("/user-create")
						.param("name", "New User"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/users"));

		verify(userService).saveUser(any(UserDTO.class));
		//verify(userService).saveUser(any(User.class));
	}

	@Test
	public void testDeleteUser() throws Exception {
		Long userId = 1L;
		mockMvc.perform(get("/user-delete/{user_id}", userId))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/users"));

		verify(userService).deleteById(userId);
	}

	@Test
	public void testUpdateUserForm() throws Exception {
		Long userId = 1L;
//		User user = new User("John Doe", "testmail@gmail.com", 27);
		UserDTO user = new UserDTO("John Doe", "testmail@gmail.com", 27);

		when(userService.findUserById(userId)).thenReturn(user);

		mockMvc.perform(get("/user-update/{user_id}", userId))
				.andExpect(status().isOk())
				.andExpect(model().attribute("user", user))
				.andExpect(view().name("/user-update"));
	}

	@Test
	public void testUpdateUser() throws Exception {
		User user = new User();
		user.setUser_id(1L);

		mockMvc.perform(post("/user-update")
						.param("name", "Updated User"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/users"));

		verify(userService).saveUser(any(UserDTO.class));
		//verify(userService).saveUser(any(User.class));
	}
}
